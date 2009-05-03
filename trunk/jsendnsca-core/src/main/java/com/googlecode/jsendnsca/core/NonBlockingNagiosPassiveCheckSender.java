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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This sender does not block unlike the {@link NagiosPassiveCheckSender}. Instead
 * it internally queues the passive check in an unbounded queue and has a single worker
 * thread sending from the queue.<p>
 * 
 * Any exceptions resulting from sending the passive check are output to standard error with 
 * a stack trace.<p>
 * 
 * This sender is useful where you don't want to wait for the passive check to be sent and
 * don't care if the sending fails<p>
 * 
 * @author Raj Patel
 * @since 1.2
 */
public class NonBlockingNagiosPassiveCheckSender implements INagiosPassiveCheckSender {

	private final INagiosPassiveCheckSender sender;
	private final ExecutorService executor;

	/**
	 * Construct a new {@link NonBlockingNagiosPassiveCheckSender} with the provided
	 * {@link NagiosSettings}
	 * 
	 * @param nagiosSettings
	 *            the {@link NagiosSettings} to use to send the Passive Check
	 */
	public NonBlockingNagiosPassiveCheckSender(NagiosSettings settings) {
		this(new NagiosPassiveCheckSender(settings));
	}
	
	NonBlockingNagiosPassiveCheckSender(INagiosPassiveCheckSender sender) {
		this.sender = sender;
		this.executor = Executors.newSingleThreadExecutor();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.jsendnsca.sender.INagiosPassiveCheckSender#send(com.googlecode
	 * .jsendnsca.sender.MessagePayload)
	 */
	public void send(MessagePayload payload) throws NagiosException, IOException {
		executor.execute(new NonBlockingSender(payload));
	}
	
	private class NonBlockingSender implements Runnable {

		private MessagePayload payload;
		
		public NonBlockingSender(MessagePayload payload) {
			this.payload = payload;
		}

		public void run() {
			try {
				sender.send(payload);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
