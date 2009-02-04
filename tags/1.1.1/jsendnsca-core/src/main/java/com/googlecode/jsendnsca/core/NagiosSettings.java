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

import com.googlecode.jsendnsca.core.utils.EncryptionUtils;
import com.googlecode.jsendnsca.core.utils.StringUtils;

/**
 * The settings to use for sending the Passive Check
 * 
 * @author Raj.Patel
 * @version 1.0
 */
public class NagiosSettings {

	/**
	 * No encryption
	 */
	public static final int NO_ENCRYPTION = 0;
	
	/**
	 * XOR encryption
	 */
	public static final int XOR_ENCRYPTION = 1;

	private String nagiosHost = "localhost";
	private int port = 5667;
	private String password = "hasturrocks";
	private int timeout = 10000;
	private int connectTimeout = 5000;
	private int encryptionMethod = XOR_ENCRYPTION;

	/**
	 * The host or IP of the Nagios host running the NSCA add-on
	 * 
	 * @return the host or IP, defaults to localhost
	 */
	public String getNagiosHost() {
		return nagiosHost;
	}

	/**
	 * The host or IP of the Nagios host running the NSCA add-on
	 * 
	 * @param nagiosHost
	 *            the host or IP, defaults to localhost
	 */
	public void setNagiosHost(String nagiosHost) {
		if (StringUtils.isBlank(nagiosHost)) {
			throw new IllegalArgumentException(
					"nagiosHost cannot be null or empty");
		}
		this.nagiosHost = nagiosHost;
	}

	/**
	 * The port on which NSCA is listening
	 * 
	 * @return the port, defaults to 5667
	 */
	public int getPort() {
		return port;
	}

	/**
	 * The port on which NSCA is listening
	 * 
	 * @param port
	 *            the port, defaults to 5667
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * The password configured in the ncsa.cfg file used by NSCA
	 * 
	 * @return the password, defaults to "hasturrocks"
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * The password configured in the ncsa.cfg file used by NSCA
	 * 
	 * @param password
	 *            the password, defaults to "hasturrocks"
	 */
	public void setPassword(String password) {
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException(
					"password cannot be null or empty");
		}
		this.password = password;
	}

	/**
	 * The socket timeout to use when sending the passive check
	 * 
	 * @return the timeout in ms, defaults to 10000 ms
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * The socket timeout to use when sending the passive check
	 * 
	 * @param timeout
	 *            the timeout in ms, defaults to 10000 ms
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * The encryption method used by the NSCA Daemon
	 * 
	 * @return the method
	 */
	public int getEncryptionMethod() {
		return encryptionMethod;
	}

	/**
	 * The encryption method used by the NSCA Daemon, currently only
	 * NO_ENCRYPTION or XOR_ENCRYPTION
	 * 
	 * @param encryptionMethod
	 *            the method
	 */
	public void setEncryptionMethod(int encryptionMethod) {
		if (!EncryptionUtils.isEncryptionMethodSupported(encryptionMethod)) {
			throw new UnsupportedOperationException(
					"Currently only NO_ENCRYPTION or XOR_ENCRYPTION Supported");
		}
		this.encryptionMethod = encryptionMethod;
	}

	/**
	 * The connection timeout
	 * 
	 * @return timeout in ms
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * Set the connection timeout, default is 5000 ms
	 * 
	 * @param connectTimeout timeout in ms
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
}
