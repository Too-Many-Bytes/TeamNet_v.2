package com.fiscorp.teamnet_v2.core.serverHandling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiscorp.teamnet_v2.core.Storage;

import java.util.ArrayList;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MainHandler extends SimpleChannelInboundHandler<String> {

    private final Storage storage;
    private final ServerParser serverParser = new ServerParser();

    public MainHandler(Storage storage) {
        this.storage = storage;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        ServerResponse serverResponse = serverParser.parseServerResponse(msg);

        if (serverResponse != null) {
            if (serverResponse.getCommand().equals("chats")) {
                storage.updateDialogs(serverParser.parseDialogs(serverResponse.getAnswer()));
                storage.getMainActivity().getMessengerFragment().refreshRecyclerView();
            } else if (serverResponse.getCommand().equals("allMsg")) {
                ArrayList<Object> allMsgServerAnswer = serverParser.parseArrayListResponse(serverResponse.getAnswer());
                storage.updateDialogMessagesAdapter(serverParser.parseDialogId(allMsgServerAnswer.get(0)),
                        serverParser.parseDialogMessages(allMsgServerAnswer.get(1)));
            } else if (serverResponse.getCommand().equals("allMsgDiv")) {
                ArrayList<Object> info = serverParser.parseArrayListResponse(serverResponse.getAnswer());
                storage.addAllMsgString(serverParser.parseDialogId(info.get(0)), info.get(1).toString(), info.get(2).toString());
            } else if (serverResponse.getCommand().equals("sendMsg")) {
                ArrayList<Object> sendMsgServerAnswer = serverParser.parseArrayListResponse(serverResponse.getAnswer());
                storage.insertMessageIntoDialog(serverParser.parseDialogId(sendMsgServerAnswer.get(0)),
                        serverParser.parseDialogMessage(sendMsgServerAnswer.get(1)));
            } else if (serverResponse.getCommand().equals("auth")) {
                //ArrayList<Object> sendMsgServerAnswer = serverParser.parseArrayListResponse(serverResponse.getAnswer());
            }
        }
    }
}

