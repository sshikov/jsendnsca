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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Thrown if invalid or missing command line arguments are provided
 * 
 * @author Raj.Patel
 * @version 1.0
 */
public class UsageException extends Exception {

    private static final long serialVersionUID = 6391805034995467681L;
    private static final String COMMAND_LINE_SYNTAX = "jsend-nsca [OPTIONS] OK|WARNING|CRITICAL servicename message";

    private String message;

    /**
     * Construct a new {@link UsageException} with the parsed {@link Options}
     * 
     * @param options
     *            the parsed {@link Options}
     */
    public UsageException(Options options) {
        StringWriter stringWriter = new StringWriter();

        new HelpFormatter().printHelp(new PrintWriter(stringWriter), 100, COMMAND_LINE_SYNTAX, "[OPTIONS]", options, 2, 10, "", false);

        message = stringWriter.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        return message;
    }

    /**
     * Print formatted usage information on the command line syntax
     */
    public void printUsageInfo() {
        System.out.print(message);
    }
}
