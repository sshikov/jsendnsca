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

public class NonBlockingNagiosPassiveCheckSender implements INagiosPassiveCheckSender {

	private final INagiosPassiveCheckSender sender;
	private final ExecutorService executor;

	public NonBlockingNagiosPassiveCheckSender(NagiosSettings settings) {
		this(new NagiosPassiveCheckSender(settings));
	}
	
	NonBlockingNagiosPassiveCheckSender(INagiosPassiveCheckSender sender) {
		this.sender = sender;
		this.executor = Executors.newSingleThreadExecutor();
	}
	
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
