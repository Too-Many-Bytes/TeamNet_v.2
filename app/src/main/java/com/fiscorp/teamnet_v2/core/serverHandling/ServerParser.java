package com.fiscorp.teamnet_v2.core.serverHandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiscorp.teamnet_v2.ui.messenger.Dialog;
import com.fiscorp.teamnet_v2.ui.messenger.dialog.Message;


import java.lang.reflect.Type;
import java.util.ArrayList;

public class ServerParser {
    ObjectMapper objectMapper;

    public ServerParser() {
        objectMapper = new ObjectMapper();
    }


    public ArrayList<Object> parseArrayListResponse(String serverAnswer) throws JsonProcessingException {
        return objectMapper.readValue(serverAnswer, new TypeReference<ArrayList<Object>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }


    public ArrayList<Message> parseDialogMessages(Object messages) throws JsonProcessingException {
        String messagesString = (String) messages;
        return objectMapper.readValue(messagesString, new TypeReference<ArrayList<Message>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }


    public ArrayList<Dialog> parseDialogs(String dialogs) throws JsonProcessingException {
        return objectMapper.readValue(dialogs, new TypeReference<ArrayList<Dialog>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }


    public int parseDialogId(Object dialogId) {
        return (Integer) dialogId;
    }


    public Message parseDialogMessage(Object message) throws JsonProcessingException {
        String msg = (String) message;
        return objectMapper.readValue(msg, Message.class);
    }


    public ServerResponse parseServerResponse(String response) throws JsonProcessingException {
        return objectMapper.readValue(response, ServerResponse.class);
    }
}
