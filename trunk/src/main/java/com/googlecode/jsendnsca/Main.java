package com.googlecode.jsendnsca;

import com.googlecode.jsendnsca.cli.OptionsParser;
import com.googlecode.jsendnsca.cli.UsageException;
import com.googlecode.jsendnsca.sender.NagiosException;
import com.googlecode.jsendnsca.sender.NagiosPassiveCheckSender;

public class Main {
    
    public static void main(String[] args) {
        try {
            sendPassiveCheck(args);
            System.out.println("Sent Passive Check Successfully");
        } catch (UsageException ue) {
            ue.printUsageInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendPassiveCheck(String[] args) throws UsageException, NagiosException {
        final OptionsParser optionsParser = new OptionsParser(args);
        
        NagiosPassiveCheckSender nagiosPassiveAlerter = new NagiosPassiveCheckSender(optionsParser.getNagiosSettings());
        nagiosPassiveAlerter.send(optionsParser.getMessagePayload());
    }
}
