package com.fiscorp.teamnet_v2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiscorp.teamnet_v2.R;
import com.fiscorp.teamnet_v2.core.Storage;
import com.fiscorp.teamnet_v2.core.serverHandling.AdditionalInfo;
import com.fiscorp.teamnet_v2.core.serverHandling.ClientResponse;
import com.fiscorp.teamnet_v2.core.serverHandling.Communicator;
import com.fiscorp.teamnet_v2.core.serverHandling.MainHandler;
import com.fiscorp.teamnet_v2.ui.events.EventsFragment;
import com.fiscorp.teamnet_v2.ui.messenger.MessengerFragment;
import com.fiscorp.teamnet_v2.ui.profile.ProfileFragment;
import com.fiscorp.teamnet_v2.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MainActivity extends AppCompatActivity {

    public Storage getStorage() {
        return storage;
    }

    private Storage storage;

    private Communicator communicator;


    private BottomNavigationView bottomNavigationView;

    private SearchFragment searchFragment = new SearchFragment();

    private MessengerFragment messengerFragment = new MessengerFragment();

    public MessengerFragment getMessengerFragment() {
        return messengerFragment;
    }


    private EventsFragment eventsFragment = new EventsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = new Storage();
        storage.setMainActivity(this);
        communicator = new Communicator(storage);
        storage.setCommunicator(communicator);
        communicator.connectToServer(storage);

        setSearchFragment();

        bottomNavigationView = findViewById(R.id.navigation_bar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_messenger:
                        setMessengerFragment();
                        return true;
                    case R.id.nav_events:
                        setEventsFragment();
                        return true;
                    case R.id.nav_profile:
                        setProfileFragment();
                        return true;
                    case R.id.nav_search:
                        setSearchFragment();
                        return true;
                }

                return false;
            }
        });

    }

    public void hideBottomNavBar() {
        bottomNavigationView.setVisibility(View.GONE);
    }
    public void showBottomNavBar() {bottomNavigationView.setVisibility(View.VISIBLE);}

    public void setSearchFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
    }

    public void setEventsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, eventsFragment).commit();
    }

    public void setMessengerFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, messengerFragment).commit();
    }

    public void setProfileFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
    }
}