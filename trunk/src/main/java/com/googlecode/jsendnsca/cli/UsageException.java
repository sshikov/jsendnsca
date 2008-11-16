package com.googlecode.jsendnsca.cli;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class UsageException extends Exception {

    private static final long serialVersionUID = 6391805034995467681L;
    private static final String COMMAND_LINE_SYNTAX = "jsend-nsca [OPTIONS] OK|WARNING|CRITICAL servicename message";

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public UsageException(Options options) {
        StringWriter stringWriter = new StringWriter();

        new HelpFormatter().printHelp(new PrintWriter(stringWriter), 100, COMMAND_LINE_SYNTAX, "[OPTIONS]", options, 2, 10, "", false);

        message = stringWriter.toString();
    }

    public void printUsageInfo() {
        System.out.print(message);
    }
}
