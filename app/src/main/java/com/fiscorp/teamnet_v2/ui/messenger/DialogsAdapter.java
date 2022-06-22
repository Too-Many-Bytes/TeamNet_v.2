package com.fiscorp.teamnet_v2.ui.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fiscorp.teamnet_v2.R;
import com.fiscorp.teamnet_v2.ui.messenger.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class DialogsAdapter extends RecyclerView.Adapter<DialogsAdapter.DialogViewHolder> {

    public void setAllDialogs(ArrayList<Dialog> allDialogs) {
        this.allDialogs = allDialogs;
    }

    public ArrayList<Dialog> getAllDialogs() {
        return allDialogs;
    }

    private ArrayList<Dialog> allDialogs;
    private final OnItemClickListener onItemClickListener;


    public DialogsAdapter(ArrayList<Dialog> allDialogs, OnItemClickListener onItemClickListener) {
        if (allDialogs == null) {
            this.allDialogs = new ArrayList<>();
        } else {
            this.allDialogs = allDialogs;
        }

        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.dialogs_recycler_view_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);

        return new DialogViewHolder(view, onItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder holder, int position) {
        Dialog necessaryDialog = allDialogs.get(position);
        holder.bind(necessaryDialog, onItemClickListener);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.dialogs_recycler_view_item;
    }

    @Override
    public int getItemCount() {
        return allDialogs.size();
    }

    protected class DialogViewHolder extends RecyclerView.ViewHolder {
        int idOfDialog;
        TextView userName;
        TextView beginningOfMessageSenderName;
        TextView beginningOfMessageText;

        public DialogViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            beginningOfMessageSenderName = itemView.findViewById(R.id.beginningOfMessageSenderName);
            beginningOfMessageText = itemView.findViewById(R.id.beginningOfMessageText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getBindingAdapterPosition());
                }
            });
        }


        void bind(Dialog dialog, OnItemClickListener onItemClickListener) {
            idOfDialog = dialog.getDialogId();
            userName.setText(dialog.getUserName());

            if (dialog.getLastMessageText().trim().length() == 0) {
                beginningOfMessageText.setText("--Вы ещё не общались--");
            } else {
                String lastMessageSender = dialog.getLastMessageSender() + ": ";
                beginningOfMessageSenderName.setText(lastMessageSender);
                beginningOfMessageText.setText(dialog.getLastMessageText());
            }
        }



//        @Override
//        public void onClick(View v) {
//            onItemClickListener.onItemClick(getBindingAdapterPosition());
//        }
    }

}
