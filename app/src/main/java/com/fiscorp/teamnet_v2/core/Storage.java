package com.fiscorp.teamnet_v2.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiscorp.teamnet_v2.core.serverHandling.ServerParser;
import com.fiscorp.teamnet_v2.ui.MainActivity;
import com.fiscorp.teamnet_v2.core.serverHandling.Communicator;
import com.fiscorp.teamnet_v2.ui.messenger.Dialog;
import com.fiscorp.teamnet_v2.ui.messenger.dialog.DialogMessagesAdapter;
import com.fiscorp.teamnet_v2.ui.messenger.dialog.Message;
import com.fiscorp.teamnet_v2.ui.messenger.dialog.MessagesAdapter;

import org.eclipse.jetty.server.Server;

import java.util.ArrayList;

public class Storage {

    public int getUserId() {
        return userId;
    }

    private final int userId = 1;

    public String getUserName() {
        return userName;
    }

    private final String userName = "karen";

    private ArrayList<Dialog> allDialogs;

    public ArrayList<Dialog> getAllDialogs() {
        return allDialogs;
    }

    private String[] allMsgStrings;

    private Communicator communicator;

    public Communicator getCommunicator() {
        return communicator;
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    private MainActivity mainActivity;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private final ArrayList<DialogMessagesAdapter> dialogMessagesAdapters;


    public Storage() {
        this.allDialogs = new ArrayList<>();
        this.dialogMessagesAdapters = new ArrayList<>();
        //this.allMsgStrings = null;

    }


    public void addAllMsgString(int dialogId, String numOfPart, String partOfResponse) throws JsonProcessingException {
        String[] nums = numOfPart.split("/");

        if (allMsgStrings == null) {
            allMsgStrings = new String[Integer.parseInt(nums[1])];
        }

        allMsgStrings[Integer.parseInt(nums[0]) - 1] = partOfResponse;



        if (notNull(allMsgStrings)) {
            ServerParser serverParser = new ServerParser();
            updateDialogMessagesAdapter(dialogId, serverParser.parseDialogMessages(concatStrings(allMsgStrings)));
            allMsgStrings = null;
        }
    }


    public void updateDialogs(ArrayList<Dialog> newDialogs) throws JsonProcessingException {
        allDialogs = newDialogs;
    }



    private boolean notNull(String[] array) {
        for (String s : array) {
            if (s == null || s.equals("")) {
                return false;
            }
        }
        return true;
    }


    private String concatStrings(String[] strings) {
        StringBuilder result = new StringBuilder();
        for (String string : strings) {
            result.append(string);
        }
        return result.toString();
    }


    public ArrayList<Message> getDialogMessagesById(int dialogId) {
        for (int i = 0; i < dialogMessagesAdapters.size(); i++) {
            DialogMessagesAdapter dialogMessagesAdapter = dialogMessagesAdapters.get(i);
            if (dialogMessagesAdapter.getDialogId() == dialogId) {
                return dialogMessagesAdapter.getMessages();
            }
        }

        return null;
    }


    public void updateDialogMessagesAdapter(int dialogId, ArrayList<Message> messages) {
        for (int i = 0; i < dialogMessagesAdapters.size(); i++) {
            if (dialogMessagesAdapters.get(i).getDialogId() == dialogId) {
                MessagesAdapter messagesAdapter = dialogMessagesAdapters.get(i).getMessagesAdapter();
                messagesAdapter.setAllMessages(messages);
                //messagesAdapter.insertItemsRange(messages);
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //messagesAdapter.notifyItemRangeInserted(0, messages.size());
                        messagesAdapter.notifyDataSetChanged();
                        messagesAdapter.getRecyclerView().scrollToPosition(messagesAdapter.getItemCount() - 1);
                    }
                });

                break;
            }
        }
    }


    public void addMessagesAdapter(int dialogId, MessagesAdapter messagesAdapter) {
        boolean wasSet = false;
        for (int i = 0; i < dialogMessagesAdapters.size(); i++) {
            if (dialogMessagesAdapters.get(i).getDialogId() == dialogId) {
                dialogMessagesAdapters.get(i).setMessagesAdapter(messagesAdapter);
                wasSet = true;
                break;
            }
        }

        if (!wasSet) {
            dialogMessagesAdapters.add(new DialogMessagesAdapter(dialogId, messagesAdapter));
        }
    }


    public void insertMessageIntoDialog(int dialogId, Message message) {
        for (int i = 0; i < dialogMessagesAdapters.size(); i++) {

            if (dialogMessagesAdapters.get(i).getDialogId() == dialogId) {
                MessagesAdapter messagesAdapter = dialogMessagesAdapters.get(i).getMessagesAdapter();
                messagesAdapter.addMessage(message);

                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messagesAdapter.notifyItemInserted(messagesAdapter.getItemCount() - 1);
                        messagesAdapter.getRecyclerView().scrollToPosition(messagesAdapter.getItemCount() - 1);                    }
                });

                break;
            }
        }

        updateDialogLastMessage(dialogId, message.getMessageText());
    }


    private void updateDialogLastMessage(int dialogId, String message) {
        if (message.length() > 16) {
            message = message.substring(0, 17) + "...";
        }

        for (int i = 0; i < allDialogs.size(); i++) {
            if (allDialogs.get(i).getDialogId() == dialogId) {
                allDialogs.get(i).setLastMessageText(message);
                mainActivity.getMessengerFragment().getRecyclerView().getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }


    public String getUserNameByDialogId(int dialogId) {
        for (int i = 0; i < allDialogs.size(); i++) {
            if (allDialogs.get(i).getDialogId() == dialogId) {
                return allDialogs.get(i).getUserName();
            }
        }

        return "Unknown hamster";
    }
}
