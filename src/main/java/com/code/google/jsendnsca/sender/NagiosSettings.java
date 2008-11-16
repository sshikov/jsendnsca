package com.code.google.jsendnsca.sender;

public class NagiosSettings {

    private String nagiosHost = "localhost";
    private int port = 5667;
    private String password = "hasturrocks";
    private int timeout = 10000;

    public String getNagiosHost() {
        return nagiosHost;
    }

    public void setNagiosHost(String nagiosHost) {
        this.nagiosHost = nagiosHost;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
