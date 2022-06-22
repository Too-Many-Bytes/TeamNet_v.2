package com.fiscorp.teamnet_v2.core.serverHandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiscorp.teamnet_v2.core.Storage;
import com.fiscorp.teamnet_v2.ui.messenger.dialog.Message;

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

public class Communicator {

    private final String host = "195.2.74.98";
    private final int port = 8189;

    private SocketChannel channel;
    private final ObjectMapper objectMapper;
    private final Storage storage;

    public Communicator(Storage storage) {
        this.storage = storage;
        this.objectMapper = new ObjectMapper();
    }


    public void connectToServer(Storage storage) {
        new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        channel = socketChannel;
                        socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new MainHandler(storage));
                    }
                });
                ChannelFuture future = b.connect(host, port).sync();
                sendIdToServer();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }

        }).start();
    }


    private void sendIdToServer() throws JsonProcessingException {
        ArrayList<Object> addInfo = new ArrayList<>(); // перенести в communicator
        addInfo.add(storage.getUserId());
        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalInfo.setInfo(addInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        ClientResponse clientResponse = new ClientResponse("connect", objectMapper.writeValueAsString(additionalInfo));
        sendClientResponse(clientResponse);
    }


    public void sendDialogsUpdateRequest() throws JsonProcessingException {
        AdditionalInfo additionalInfo = new AdditionalInfo();
        ArrayList<Object> addInfo = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        addInfo.add(storage.getUserId());
        additionalInfo.setInfo(addInfo);
        ClientResponse clientResponse = new ClientResponse("chats", objectMapper.writeValueAsString(additionalInfo));
        storage.getCommunicator().sendClientResponse(clientResponse);
    }


    public void sendClientResponse(ClientResponse clientResponse) throws JsonProcessingException {
        channel.writeAndFlush(objectMapper.writeValueAsString(clientResponse));
    }


    public void sendMessageRequest(String msg, int dialogId) throws JsonProcessingException {
        AdditionalInfo additionalInfo = new AdditionalInfo();
        ArrayList<Object> info = new ArrayList<>();
        info.add(storage.getUserId());
        info.add(storage.getUserName());
        info.add(dialogId);
        info.add(msg);
        additionalInfo.setInfo(info);
        ClientResponse clientResponse = new ClientResponse("sendMsg", objectMapper
                .writeValueAsString(additionalInfo));

        sendClientResponse(clientResponse);
    }


    public void sendLoadMessagesRequest(int dialogId) throws JsonProcessingException {

        AdditionalInfo additionalInfo = new AdditionalInfo();
        ArrayList<Object> info = new ArrayList<>();
        info.add(dialogId);
        additionalInfo.setInfo(info);
        ClientResponse clientResponse = new ClientResponse("allMsg", objectMapper
                .writeValueAsString(additionalInfo));

        sendClientResponse(clientResponse);
    }


    public void sendAuthRequest(String login, String password) throws JsonProcessingException {
        AdditionalInfo additionalInfo = new AdditionalInfo();
        ArrayList<Object> addInfo = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        addInfo.add(login);
        addInfo.add(password);
        additionalInfo.setInfo(addInfo);
        ClientResponse clientResponse = new ClientResponse("auth", objectMapper.writeValueAsString(additionalInfo));
        storage.getCommunicator().sendClientResponse(clientResponse);
    }

}
