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
package com.googlecode.jsendnsca.cli;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.jsendnsca.cli.OptionsParser;
import com.googlecode.jsendnsca.cli.UsageException;
import com.googlecode.jsendnsca.sender.MessagePayload;
import com.googlecode.jsendnsca.sender.NagiosSettings;

public class OptionsParserTest {

    @Test
    public void shouldReturnDefaultNagiosSettingsIfNotProvidedOnCommandLine() throws Exception {
        final String[] args = new String[] { "OK", "test service", "test message",};

        NagiosSettings nagiosSettings = new OptionsParser(args).getNagiosSettings();

        assertEquals("localhost", nagiosSettings.getNagiosHost());
        assertEquals(5667, nagiosSettings.getPort());
        assertEquals("hasturrocks", nagiosSettings.getPassword());
        assertEquals(10000, nagiosSettings.getTimeout());
    }

    @Test
    public void shouldReturnNagiosSettingsAsPerTheCommandLineProvided() throws Exception {
        final String[] args = new String[] { "--nagioshost", "nagios-vhost", "--alertinghost", "me"
                , "--port", "6667", "--password", "secret", "--timeout","30000",  "OK", "test service", "test message",};

        NagiosSettings nagiosSettings = new OptionsParser(args).getNagiosSettings();

        assertEquals("nagios-vhost", nagiosSettings.getNagiosHost());
        assertEquals(6667, nagiosSettings.getPort());
        assertEquals("secret", nagiosSettings.getPassword());
        assertEquals(30000, nagiosSettings.getTimeout());
    }
    
    @Test
    public void shouldThrowUsageExceptionForInvalidNagiosArguments() throws Exception {
        final String[] args = new String[] { "--nagioshost"};

        try {
            new OptionsParser(args).getNagiosSettings();
            fail("Should throw UsageException");
        } catch (UsageException e) {
            System.out.print(e.getMessage());
            assertTrue(e.getMessage().startsWith("usage"));
        }
    }
    
    @Test
    public void shouldReturnMessagePayloadForMessageArgumentsProvided() throws Exception {
        final String[] args = new String[] { "WARNING", "test service", "test message",};

        MessagePayload messagePayload = new OptionsParser(args).getMessagePayload();
        
        assertEquals(1, messagePayload.getLevel());
        assertEquals("test service", messagePayload.getServiceName());
        assertEquals("test message", messagePayload.getMessage());
    }
    
    @Test
    public void shouldThrowUsageExceptionOnMissingMessageArguments() throws Exception {
        final String[] args = new String[] {};

        try {
            new OptionsParser(args).getMessagePayload();
            fail("Should throw UsageException");
        } catch (UsageException ue) {
            ue.printUsageInfo();
            assertTrue(ue.getMessage().startsWith("usage"));
        }
    }
    
    @Test
    public void shouldThrowUsageExceptionOnInvalidLevel() throws Exception {
        final String[] args = new String[] { "FOO", "test service", "test message",};

        try {
            new OptionsParser(args).getMessagePayload();
            fail("Should throw UsageException");
        } catch (UsageException ue) {
            ue.printUsageInfo();
            assertTrue(ue.getMessage().startsWith("usage"));
        }
    }
}
