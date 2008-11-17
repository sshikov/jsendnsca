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
package com.googlecode.jsendnsca;

import com.googlecode.jsendnsca.cli.OptionsParser;
import com.googlecode.jsendnsca.cli.UsageException;
import com.googlecode.jsendnsca.sender.NagiosException;
import com.googlecode.jsendnsca.sender.NagiosPassiveCheckSender;

/**
 * Main class for the command line tool
 * 
 * @author Raj.Patel
 * @version 1.0
 */
public class Main {
    
    /**
     * Main method
     * @param args
     */
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
