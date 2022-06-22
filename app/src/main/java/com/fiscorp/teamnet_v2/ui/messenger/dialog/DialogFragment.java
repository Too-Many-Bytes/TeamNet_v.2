package com.fiscorp.teamnet_v2.ui.messenger.dialog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiscorp.teamnet_v2.ui.MainActivity;
import com.fiscorp.teamnet_v2.R;
import com.fiscorp.teamnet_v2.core.Storage;
import com.fiscorp.teamnet_v2.ui.messenger.DialogsAdapter;


public class DialogFragment extends Fragment {

    private EditText editTextMessage;
    private TextView textViewUserName;
    private ImageButton imageButtonBack;
    private ImageButton imageButtonSendMessage;
    private RecyclerView recyclerViewMessages;
    private Storage mainStorage;
    private MessagesAdapter messagesAdapter;
    private int dialogId;
    private String dialogIdParam = "dialogId";

    public DialogFragment() {
        // Required empty public constructor
    }

//    // TODO: Rename and change types and number of parameters
//    public static BlankFragment newInstance(String param1, String param2) {
//        BlankFragment fragment = new BlankFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return fragment;
//    }
//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dialogId = getArguments().getInt(dialogIdParam);
        }

        mainStorage = ((MainActivity) getActivity()).getStorage();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        textViewUserName = view.findViewById(R.id.textViewUserName);
        imageButtonBack = view.findViewById(R.id.imageButtonBack);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        imageButtonSendMessage = view.findViewById(R.id.imageButtonSendMessage);
        recyclerViewMessages = view.findViewById(R.id.recyclerViewMessages);


        createMessagesAdapter();

        textViewUserName.setText(mainStorage.getUserNameByDialogId(dialogId));


        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //MessagesAdapter messagesAdapter = new MessagesAdapter(mainStorage.getDialogMessagesById(0));
        recyclerViewMessages.setAdapter(messagesAdapter);
        messagesAdapter.setRecyclerView(recyclerViewMessages);
        recyclerViewMessages.scrollToPosition(messagesAdapter.getItemCount() - 1);


        loadMessages();

        editTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewMessages.scrollToPosition(messagesAdapter.getItemCount() - 1);
            }
        });


        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainStorage.getMainActivity().showBottomNavBar();
                mainStorage.getMainActivity().setMessengerFragment();
            }
        });


        imageButtonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextMessage.getText().toString().trim().equals("")) {
                    return;
                }
                try {
                    mainStorage.getCommunicator().sendMessageRequest(editTextMessage.getText().toString(), dialogId);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                editTextMessage.setText("");
                //recyclerViewMessages.scrollToPosition(messagesAdapter.getItemCount() - 1);
                //mainStorage.getMainActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainStorage.getMainActivity().getMessengerFragment()).commit();
            }
        });


        return view;
    }


    private void loadMessages() {
        try {
            mainStorage.getCommunicator().sendLoadMessagesRequest(dialogId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    private void createMessagesAdapter() {
        messagesAdapter = new MessagesAdapter(mainStorage.getDialogMessagesById(dialogId));
        mainStorage.addMessagesAdapter(dialogId, messagesAdapter);
    }
}
