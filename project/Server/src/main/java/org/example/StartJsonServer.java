package org.example;

import org.example.Repository.*;
import org.example.Service.IService;
import org.example.Service.Service;
import org.example.utils.AbstractServer;
import org.example.utils.ChatJsonConcurrentServer;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartJsonServer {
    private static int defaultPort=55555;

    public static void main(String[] args) {

        Properties serverProps=new Properties();
        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }

        BugRepository bugRepository = new BugHibernateRepository();
        TesterRepository testerRepository = new TesterHibernateRepository();
        ProgrammerRepository programmerRepository = new ProgrammerHibernateRepository();
        IService service = new Service(testerRepository, programmerRepository, bugRepository);


        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }

        System.out.println("Starting server on port: "+serverPort);
        AbstractServer server = new ChatJsonConcurrentServer(serverPort, service);

        try {
            server.start();
        } catch (org.example.utils.ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }

    }
}
