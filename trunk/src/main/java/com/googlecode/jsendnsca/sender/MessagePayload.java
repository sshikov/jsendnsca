package com.googlecode.jsendnsca.sender;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MessagePayload {

    public static final int LEVEL_OK = 0;
    public static final int LEVEL_WARNING = 1;
    public static final int LEVEL_CRITICAL = 2;
    public static final int LEVEL_UNKNOWN = 3;

    private String hostname = "localhost";
    private int level;
    private String serviceName;
    private String message;
    
    public String getHostname() {
        return hostname;
    }
    
    public void useLocalHostname() throws UnknownHostException {
        setHostname(false);
    }

    public void setHostname(boolean useCanonical) throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        if (useCanonical) {
            this.hostname = ipAddress.getCanonicalHostName();
        } else {
            this.hostname = ipAddress.getHostName();
        }
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLevel(String level) {
        if (level.equals("OK")) {
            this.level = 0;
        } else if (level.equals("WARNING")) {
            this.level = 1;
        } else if (level.equals("CRITICAL")) {
            this.level = 2;
        } else {
            this.level = 3;
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
