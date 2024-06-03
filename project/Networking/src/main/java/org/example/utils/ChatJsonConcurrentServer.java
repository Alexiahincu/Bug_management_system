package org.example.utils;

import org.example.Service.IService;
import org.example.jsonprotocol.ChatClientJsonWorker;

import java.net.Socket;

public class ChatJsonConcurrentServer extends AbsConcurrentServer{
    private IService chatServer;
    public ChatJsonConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatJsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ChatClientJsonWorker worker=new ChatClientJsonWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }
}
