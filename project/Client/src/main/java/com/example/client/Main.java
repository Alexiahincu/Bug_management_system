package com.example.client;

import com.example.client.gui.LoginController;
import com.example.client.gui.ProgrammerController;
import com.example.client.gui.TesterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Domain.Programmer;
import org.example.Repository.*;
import org.example.Service.IService;
import org.example.Service.Service;
import org.example.jsonprotocol.ChatServicesJsonProxy;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";

    public void start(Stage primaryStage) throws Exception {

        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(Main.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        ChatServicesJsonProxy server = new ChatServicesJsonProxy(serverIP, serverPort);



        // hello view


        FXMLLoader loader = new FXMLLoader(
                Main.class.getClassLoader().getResource("hello-view.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.getController();
        ctrl.setService(server);

            // tester

        FXMLLoader tloader = new FXMLLoader(
                getClass().getClassLoader().getResource("tester.fxml"));
        Parent troot=tloader.load();


        TesterController tcontroller =
                tloader.<TesterController>getController();
        tcontroller.setServices(server);
        //controller.loadData();

        ctrl.setTesterController(tcontroller);
        ctrl.setTParent(troot);

            // programmer

        FXMLLoader ploader = new FXMLLoader(
                getClass().getClassLoader().getResource("programmer.fxml"));
        Parent proot=ploader.load();


        ProgrammerController pcontroller =
                ploader.<ProgrammerController>getController();
        pcontroller.setServices(server);
        //controller.loadData();


        ctrl.setProgrammerController(pcontroller);
        ctrl.setPParent(proot);

            // show primary stage

        primaryStage.setTitle("Bug management system");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();

    }
}
