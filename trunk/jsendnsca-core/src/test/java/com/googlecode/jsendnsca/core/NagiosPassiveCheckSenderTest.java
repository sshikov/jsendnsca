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

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.jsendnsca.core.mocks.MockNscaDaemon;

@SuppressWarnings("static-access")
public class NagiosPassiveCheckSenderTest {

	private static MockNscaDaemon mockNscaDaemon;
	private static Thread daemonThread;

	@BeforeClass
	public static void startMockDaemon() throws IOException {
		mockNscaDaemon = new MockNscaDaemon();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgExceptionOnConstructingSenderWithNullNagiosSettings() throws Exception {
		new NagiosPassiveCheckSender(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgExceptionOnSendingWithNullMessagePayload() throws Exception {
		final NagiosPassiveCheckSender sender = new NagiosPassiveCheckSender(new NagiosSettings());

		sender.send(null);
	}

	@Test(expected = UnknownHostException.class)
	public void shouldThrowUnknownHostExceptionOnUnknownHost() throws Exception {
		NagiosSettings nagiosSettings = new NagiosSettings();
		nagiosSettings.setNagiosHost("foobar");
		final NagiosPassiveCheckSender sender = new NagiosPassiveCheckSender(nagiosSettings);

		sender.send(new MessagePayload());
	}

	@Test
	public void shouldSendPassiveCheck() throws Exception {
		final NagiosSettings nagiosSettings = NagiosSettingsBuilder
			.withNagiosHost("localhost")
			.withPassword("hasturrocks")
			.create();

		final NagiosPassiveCheckSender passiveAlerter = new NagiosPassiveCheckSender(nagiosSettings);

		final MessagePayload payload = new MessagePayload();
		payload.setHostname("localhost");
		payload.setLevel(MessagePayload.LEVEL_CRITICAL);
		payload.setServiceName("Test Service Name");
		payload.setMessage("Test Message");

		daemonThread = new Thread(mockNscaDaemon);
		daemonThread.start();
		Thread.sleep(1000);
		passiveAlerter.send(payload);
	}
	
	@Test(expected=NagiosException.class)
	public void shouldThrowNagiosExceptionIfNoInitVectorSentOnConnection() throws Exception {
		final NagiosSettings nagiosSettings = new NagiosSettings();
		nagiosSettings.setNagiosHost("localhost");
		nagiosSettings.setPassword("hasturrocks");

		final NagiosPassiveCheckSender passiveAlerter = new NagiosPassiveCheckSender(nagiosSettings);

		final MessagePayload payload = new MessagePayload();
		payload.setHostname("localhost");
		payload.setLevel(MessagePayload.LEVEL_CRITICAL);
		payload.setServiceName("Test Service Name");
		payload.setMessage("Test Message");

		mockNscaDaemon.setFailToSendInitVector(true);
		daemonThread = new Thread(mockNscaDaemon);
		daemonThread.start();
		passiveAlerter.send(payload);
	}

	@Test(expected = SocketTimeoutException.class)
	public void shouldTimeoutWhenSendingPassiveCheck() throws Exception {
		final NagiosSettings nagiosSettings = new NagiosSettings();
		nagiosSettings.setTimeout(1000);

		final NagiosPassiveCheckSender passiveAlerter = new NagiosPassiveCheckSender(nagiosSettings);

		final MessagePayload payload = new MessagePayload();
		payload.setHostname("localhost");
		payload.setLevel(MessagePayload.LEVEL_CRITICAL);
		payload.setServiceName("Test Service Name");
		payload.setMessage("Test Message");

		mockNscaDaemon.setSimulateTimeout(true);
		daemonThread = new Thread(mockNscaDaemon);
		daemonThread.start();
		Thread.sleep(1000);
		passiveAlerter.send(payload);
	}
	
	@After
	public void resetMockAfterEachTest() {
		mockNscaDaemon.setSimulateTimeout(false);
		mockNscaDaemon.setFailToSendInitVector(false);
	}

	@AfterClass
	public static void stopDaemonThreadIfRunning() {
		try {
			if (daemonThread != null) {
				while (daemonThread.isAlive()) { 
					Thread.sleep(10);
				}
			}
			if (mockNscaDaemon != null) {
				mockNscaDaemon.shutDown();
			}
		} catch (InterruptedException ignore) {
		}
	}

}
