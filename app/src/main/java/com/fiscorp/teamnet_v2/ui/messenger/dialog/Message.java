package com.fiscorp.teamnet_v2.ui.messenger.dialog;

import java.sql.Time;
import java.sql.Timestamp;

public class Message {

    public String getMessageText() {
        return messageText;
    }

    public String getMessageSenderName() {
        return messageSenderName;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    private String messageText;

    public Timestamp getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }

    private Timestamp messageTime;

    public void setMessageSenderName(String messageSenderName) {
        this.messageSenderName = messageSenderName;
    }

    private String messageSenderName;

//    public Message(String messageText, Timestamp messageTime, String messageSenderName) {
//        this.messageText = messageText;
//        this.messageTime = messageTime;
//        this.messageSenderName = messageSenderName;
//    }

}
