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

import java.net.SocketTimeoutException;

import org.junit.Test;

import com.googlecode.jsendnsca.core.mocks.MockNscaDaemon;

public class NagiosPassiveCheckSenderTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgExceptionOnConstructingSenderWithNullNagiosSettings() throws Exception {
		new NagiosPassiveCheckSender(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgExceptionOnSendingWithNullMessagePayload() throws Exception {
		final NagiosPassiveCheckSender sender = new NagiosPassiveCheckSender(new NagiosSettings());

		sender.send(null);
	}

	@Test
	public void sendPassiveAlert() throws Exception {
		final NagiosSettings nagiosSettings = new NagiosSettings();
		nagiosSettings.setNagiosHost("localhost");
		nagiosSettings.setPassword("hasturrocks");
		
		final NagiosPassiveCheckSender passiveAlerter = new NagiosPassiveCheckSender(nagiosSettings);

		final MessagePayload payload = new MessagePayload();
		payload.setHostname("localhost");
		payload.setLevel(MessagePayload.LEVEL_CRITICAL);
		payload.setServiceName("Test Service Name");
		payload.setMessage("Test Message");

		final Thread daemonThread = new Thread(new MockNscaDaemon());
		daemonThread.start();
		Thread.sleep(1000);
		passiveAlerter.send(payload);
	}
	
	@Test(expected=SocketTimeoutException.class)
	public void shouldTimeoutWhenSendingPassiveCheck() throws Exception {
		final NagiosSettings nagiosSettings = new NagiosSettings();
		nagiosSettings.setTimeout(1000);

		final NagiosPassiveCheckSender passiveAlerter = new NagiosPassiveCheckSender(nagiosSettings);

		final MessagePayload payload = new MessagePayload();
		payload.setHostname("localhost");
		payload.setLevel(MessagePayload.LEVEL_CRITICAL);
		payload.setServiceName("Test Service Name");
		payload.setMessage("Test Message");

		final MockNscaDaemon mockNscaDaemon = new MockNscaDaemon();
		mockNscaDaemon.setSimulateTimeout(true);
		final Thread daemonThread = new Thread(mockNscaDaemon);
		daemonThread.start();
		Thread.sleep(1000);
		passiveAlerter.send(payload);
	}
}
