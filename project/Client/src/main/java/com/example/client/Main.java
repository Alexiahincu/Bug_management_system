package com.example.client;

import com.example.client.gui.*;
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

        ctrl.setTesterController(tcontroller);
        ctrl.setTParent(troot);

            // programmer

        FXMLLoader ploader = new FXMLLoader(
                getClass().getClassLoader().getResource("programmer.fxml"));
        Parent proot=ploader.load();


        ProgrammerController pcontroller =
                ploader.<ProgrammerController>getController();
        pcontroller.setServices(server);


        ctrl.setProgrammerController(pcontroller);
        ctrl.setPParent(proot);

            // new tester

        FXMLLoader ntloader = new FXMLLoader(
                getClass().getClassLoader().getResource("newTester.fxml"));
        Parent ntroot=ntloader.load();


        TNewAcc tncontroller =
                ntloader.<TNewAcc>getController();
        tncontroller.setServices(server);


        ctrl.setTNewAcc(tncontroller);
        ctrl.setNtParent(ntroot);

        // new programmer

        FXMLLoader nploader = new FXMLLoader(
                getClass().getClassLoader().getResource("newProgrammer.fxml"));
        Parent nproot=nploader.load();


        PNewAcc pncontroller =
                nploader.<PNewAcc>getController();
        pncontroller.setServices(server);


        ctrl.setPNewAcc(pncontroller);
        ctrl.setNpParent(nproot);

            // show primary stage

        primaryStage.setTitle("Bug management system");
        primaryStage.setScene(new Scene(root, 450, 150));
        primaryStage.show();

    }
}
