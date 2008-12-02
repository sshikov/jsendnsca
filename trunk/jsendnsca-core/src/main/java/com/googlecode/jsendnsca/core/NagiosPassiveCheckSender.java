/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.jsendnsca.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.zip.CRC32;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jsendnsca.core.utils.ByteArrayUtils;
import com.googlecode.jsendnsca.core.utils.IOUtils;

/**
 * This class is used to send a Passive Check to the Nagios NSCA add-on
 * 
 * @author Raj.Patel
 * @version 1.0
 */
public class NagiosPassiveCheckSender implements INagiosPassiveCheckSender {

	private static final int INITIALISATION_VECTOR_SIZE = 128;
	private static final int PLUGIN_OUTPUT_SIZE = 512;
	private static final int HOST_NAME_SIZE = 64;
	private static final int SERVICE_NAME_SIZE = 128;
	private static final short NSCA_VERSION = 3;

	private NagiosSettings nagiosSettings;

	/**
	 * Construct a new {@link NagiosPassiveCheckSender} with the provided
	 * {@link NagiosSettings}
	 * 
	 * @param nagiosSettings
	 *            the {@link NagiosSettings} to use to send the Passive Check
	 */
	public NagiosPassiveCheckSender(NagiosSettings nagiosSettings) {
		if (nagiosSettings == null) {
			throw new IllegalArgumentException("nagiosSettings cannot be null");
		}
		this.nagiosSettings = nagiosSettings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.jsendnsca.sender.INagiosPassiveCheckSender#send(com.googlecode
	 * .jsendnsca.sender.MessagePayload)
	 */
	public void send(MessagePayload payload) throws NagiosException, IOException {
		if (payload == null) {
			throw new IllegalArgumentException("payload cannot be null");
		}

		final Socket socket = new Socket();
		final InetSocketAddress nagiosEndpoint = new InetSocketAddress(nagiosSettings.getNagiosHost(), nagiosSettings.getPort());

		OutputStream outputStream = null;
		DataInputStream inputStream = null;
		try {
			socket.connect(nagiosEndpoint, nagiosSettings.getConnectTimeout());
			socket.setSoTimeout(nagiosSettings.getTimeout());
			outputStream = socket.getOutputStream();
			inputStream = new DataInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			throw ioe;
		}

		// read init vector and timestamp on connection
		final byte[] initVector = readInitializationVector(inputStream);
		int timeStamp = inputStream.readInt();

		// prepare passive check and send
		final byte[] passiveCheckBytes = new byte[16 + HOST_NAME_SIZE + SERVICE_NAME_SIZE + PLUGIN_OUTPUT_SIZE];
		try {
			// 1st part, the NSCA version
			ByteArrayUtils.writeShort(passiveCheckBytes, NSCA_VERSION, 0);

			// 3rd part, echo back the time
			ByteArrayUtils.writeInteger(passiveCheckBytes, timeStamp, 8);

			// 4th part, the return code
			ByteArrayUtils.writeShort(passiveCheckBytes, (short) payload.getLevel(), 12);

			// set up main payload
			int myOffset = 14;
			ByteArrayUtils.writeFixedString(passiveCheckBytes, payload.getHostname(), myOffset, HOST_NAME_SIZE);
			ByteArrayUtils.writeFixedString(passiveCheckBytes, payload.getServiceName(), myOffset += HOST_NAME_SIZE, SERVICE_NAME_SIZE);
			ByteArrayUtils.writeFixedString(passiveCheckBytes, payload.getMessage(), myOffset += SERVICE_NAME_SIZE, PLUGIN_OUTPUT_SIZE);

			// 2nd part, CRC
			writeCRC(passiveCheckBytes);

			encryptPayload(passiveCheckBytes, initVector, nagiosSettings.getEncryptionMethod());

			outputStream.write(passiveCheckBytes, 0, passiveCheckBytes.length);
			outputStream.flush();
		} catch (SocketTimeoutException ste) {
			throw ste;
		} catch (Exception e) {
			throw new NagiosException("Error occured while sending passive alert", e);
		} finally {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(inputStream);
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException ignore) {
			}
		}
	}

	private byte[] readInitializationVector(DataInputStream inputStream) throws NagiosException, SocketTimeoutException {
		final byte[] initVector = new byte[INITIALISATION_VECTOR_SIZE];
		try {
			inputStream.readFully(initVector, 0, INITIALISATION_VECTOR_SIZE);
			return initVector;
		} catch (SocketTimeoutException ste) {
			throw ste;
		} catch (Exception e) {
			throw new NagiosException("Can't read initialisation vector", e);
		}
	}

	private void writeCRC(byte[] passiveCheckBytes) {
		final CRC32 crc = new CRC32();
		crc.update(passiveCheckBytes);
		ByteArrayUtils.writeInteger(passiveCheckBytes, (int) crc.getValue(), 4);
	}

	private void encryptPayload(byte[] sendBuffer, byte[] initVector, int encryptionMethod) {

		switch (encryptionMethod) {
		case NagiosSettings.XOR_ENCRYPTION:
			for (int y = 0, x = 0; y < sendBuffer.length; y++, x++) {
				if (x >= INITIALISATION_VECTOR_SIZE) {
					x = 0;
				}
				sendBuffer[y] ^= initVector[x];
			}

			if (StringUtils.isNotBlank(nagiosSettings.getPassword())) {
				final byte[] passwordBytes = nagiosSettings.getPassword().getBytes();

				for (int y = 0, x = 0; y < sendBuffer.length; y++, x++) {
					if (x >= passwordBytes.length) {
						x = 0;
					}
					sendBuffer[y] ^= passwordBytes[x];
				}
			}
			break;
		default:
			break;
		}
	}
}