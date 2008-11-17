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
package com.googlecode.jsendnsca.sender;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.jsendnsca.sender.MessagePayload;
import com.googlecode.jsendnsca.sender.NagiosException;
import com.googlecode.jsendnsca.sender.NagiosPassiveCheckSender;
import com.googlecode.jsendnsca.sender.NagiosSettings;

public class NagiosPassiveCheckSenderTest {

    @Test
    @Ignore("Only to be used for manual verification as relies on NSCA")
    public void sendPassiveAlert() {
        try {
            final NagiosSettings nagiosSettings = new NagiosSettings();
            nagiosSettings.setNagiosHost("localhost");
            nagiosSettings.setPassword("hasturrocks");
            
            final NagiosPassiveCheckSender passiveAlerter = new NagiosPassiveCheckSender(nagiosSettings);
            
            final MessagePayload payload = new MessagePayload();
            payload.setHostname("localhost");
            payload.setLevel(MessagePayload.LEVEL_CRITICAL);
            payload.setServiceName("Test Service Name");
            payload.setMessage("Test Message");
            
            passiveAlerter.send(payload);
        } catch (NagiosException ex) {
            fail(ex.toString());
        }
    }
}
