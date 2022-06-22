package com.fiscorp.teamnet_v2.ui.messenger.dialog;

import java.util.ArrayList;

public class DialogMessagesAdapter {
    private final int dialogId;

    public int getDialogId() {
        return dialogId;
    }

    private MessagesAdapter messagesAdapter;

    public MessagesAdapter getMessagesAdapter() {
        return messagesAdapter;
    }

    public void setMessagesAdapter(MessagesAdapter messagesAdapter) {
        this.messagesAdapter = messagesAdapter;
    }

    public ArrayList<Message> getMessages() {
        return messagesAdapter.getAllMessages();
    }

    public DialogMessagesAdapter(int dialogId, MessagesAdapter messagesAdapter) {
        this.dialogId = dialogId;
        this.messagesAdapter = messagesAdapter;
    }

}
