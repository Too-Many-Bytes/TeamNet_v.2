package com.fiscorp.teamnet_v2.ui.messenger.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fiscorp.teamnet_v2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    public void setAllMessages(ArrayList<Message> allMessages) {
        this.allMessages = allMessages;
    }

    public ArrayList<Message> getAllMessages() {
        return allMessages;
    }

    private ArrayList<Message> allMessages;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    private RecyclerView recyclerView;

    public MessagesAdapter(ArrayList<Message> messages) {
        if (messages == null) {
            allMessages = new ArrayList<>();
            //allMessages.add(new Message("Hello", "Karen"));
        } else {
            allMessages = messages;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.messages_recycler_view_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);

        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message necessaryMessage = allMessages.get(position);
        holder.bind(necessaryMessage);
    }

    @Override
    public int getItemCount() {
        return allMessages.size();
    }


    public void addMessage(Message msg) {
        this.allMessages.add(msg);
    }

    public void insertItemsRange(ArrayList<Message> messages) {
        allMessages.addAll(0, messages);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessageText;
        TextView textViewMessageDateTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessageText = itemView.findViewById(R.id.textViewMessageText);
            textViewMessageDateTime = itemView.findViewById(R.id.textViewMessageDateTime);
        }


        void bind(Message message) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            String messageText = message.getMessageSenderName() + ": " + message.getMessageText();
            String dateTimeText = dateFormat.format(message.getMessageTime());
            textViewMessageDateTime.setText(dateTimeText);
            textViewMessageText.setText(messageText);
        }
    }
}


