package com.fiscorp.teamnet_v2.ui.messenger;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiscorp.teamnet_v2.ui.MainActivity;
import com.fiscorp.teamnet_v2.R;
import com.fiscorp.teamnet_v2.core.Storage;
import com.fiscorp.teamnet_v2.core.serverHandling.AdditionalInfo;
import com.fiscorp.teamnet_v2.core.serverHandling.ClientResponse;
import com.fiscorp.teamnet_v2.ui.messenger.dialog.DialogFragment;
import com.fiscorp.teamnet_v2.ui.messenger.interfaces.OnItemClickListener;

import java.util.ArrayList;


public class MessengerFragment extends Fragment implements OnItemClickListener {


    private RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private Storage mainStorage;

    private DialogsAdapter dialogsAdapter;

    public MessengerFragment() {
        // Required empty public constructor
    }

//    public static MessengerFragment newInstance(String param1, String param2) {
//        MessengerFragment fragment = new MessengerFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainStorage = ((MainActivity) getActivity()).getStorage();
        dialogsAdapter = new DialogsAdapter(mainStorage.getAllDialogs(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_messenger, container, false);
        recyclerView = view.findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //DialogsAdapter dialogsAdapter = new DialogsAdapter(mainStorage.getAllDialogs(), this);
        recyclerView.setAdapter(dialogsAdapter);

        try {
            mainStorage.getCommunicator().sendDialogsUpdateRequest();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return view;
    }


    @Override
    public void onItemClick(int position) {
        DialogsAdapter dialogsAdapter = (DialogsAdapter) recyclerView.getAdapter();
        Dialog dialog = dialogsAdapter.getAllDialogs().get(position);
        MainActivity mainActivity = mainStorage.getMainActivity();
        DialogFragment dialogFragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putInt("dialogId", dialog.getDialogId());
        dialogFragment.setArguments(args);
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, dialogFragment).commit();

        mainActivity.hideBottomNavBar();

    }


    public void refreshRecyclerView() {
        mainStorage.getMainActivity().runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                DialogsAdapter dialogsAdapter = (DialogsAdapter) recyclerView.getAdapter();
                if (dialogsAdapter != null) {
                    dialogsAdapter.setAllDialogs(mainStorage.getAllDialogs());
                }
                if (dialogsAdapter != null) {
                    dialogsAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}