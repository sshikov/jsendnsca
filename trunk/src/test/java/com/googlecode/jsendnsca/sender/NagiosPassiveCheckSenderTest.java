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
