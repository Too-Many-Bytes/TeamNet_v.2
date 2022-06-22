package com.fiscorp.teamnet_v2.core.serverHandling;

public class ClientResponse {
    private final String command;
    private String additionalInfo;

    public String getCommand() {
        return command;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }


    public ClientResponse(String command) {
        this.command = command;
    }

    public ClientResponse(String command, String additionalInfo) {
        this.command = command;
        this.additionalInfo = additionalInfo;
    }
}
