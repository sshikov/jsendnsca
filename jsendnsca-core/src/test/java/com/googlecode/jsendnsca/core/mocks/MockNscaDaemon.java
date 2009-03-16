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
package com.googlecode.jsendnsca.core.mocks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MockNscaDaemon implements Runnable {
	private static final int PASSIVE_CHECK_SIZE = 720;
	private static final int INITIALISATION_VECTOR_SIZE = 128;

	private int NSCA_PORT = 5667;
	private ServerSocket serverSocket;
	private boolean simulateTimeout = false;
	private boolean failToSendInitVector = false;

	public void setSimulateTimeout(boolean simulateTimeout) {
		this.simulateTimeout = simulateTimeout;
	}
	
	public void setFailToSendInitVector(boolean failToSendInitVector) {
		this.failToSendInitVector = failToSendInitVector;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(NSCA_PORT);
			Thread.sleep(500);
			Socket clientSocket = serverSocket.accept();

			DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
			DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
			
			if(simulateTimeout) {
				try {
					Thread.sleep(1100);
					return;
				} catch (InterruptedException ignore) {
				}
			}

			if (!failToSendInitVector) {
				// write init vector and timestamp
				byte[] initVector = new byte[INITIALISATION_VECTOR_SIZE];
				outputStream.write(initVector);
				outputStream.writeInt((int) new Date().getTime());
				outputStream.flush();
				// read passive check bytes
				byte[] passiveCheckBytes = new byte[PASSIVE_CHECK_SIZE];
				inputStream.readFully(passiveCheckBytes, 0, PASSIVE_CHECK_SIZE);
			}
			
			clientSocket.close();
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
