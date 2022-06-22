package com.fiscorp.teamnet_v2.ui.messenger;

public class Dialog {
    private int dialogId;
    private String lastMessageText;
    private String userName;
    private String lastMessageSender;
    private String lastMessageTime;


    public int getDialogId() {
        return dialogId;
    }

    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessageSender() {
        return lastMessageSender;
    }

    public void setLastMessageSender(String lastMessageSender) {
        this.lastMessageSender = lastMessageSender;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }



//    public Dialog(int id, String userName, String lastMessageText, String lastMessageSender) {
//        this.dialogId = id;
//        this.userName = userName;
//        this.lastMessageText = lastMessageText;
//        this.lastMessageSender = lastMessageSender;
//
//
//    }
}