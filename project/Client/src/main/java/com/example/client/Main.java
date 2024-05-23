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

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {

        Properties serverProps=new Properties();

        try {
            serverProps.load(Main.class.getResourceAsStream("/chatserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }

        BugRepository bugRepository = new BugDBRepository(serverProps);
        TesterRepository testerRepository = new TesterDBRepository(serverProps);
        ProgrammerRepository programmerRepository = new ProgrammerDBRepository(serverProps);
        IService service = new Service(testerRepository, programmerRepository, bugRepository);


        FXMLLoader loader = new FXMLLoader(
                Main.class.getClassLoader().getResource("hello-view.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.getController();
        ctrl.setService(service);




        FXMLLoader tloader = new FXMLLoader(
                getClass().getClassLoader().getResource("tester.fxml"));
        Parent troot=tloader.load();


        TesterController tcontroller =
                tloader.<TesterController>getController();
        tcontroller.setServices(service);
        //controller.loadData();

        ctrl.setTesterController(tcontroller);
        ctrl.setTParent(troot);



        FXMLLoader ploader = new FXMLLoader(
                getClass().getClassLoader().getResource("programmer.fxml"));
        Parent proot=ploader.load();


        ProgrammerController pcontroller =
                ploader.<ProgrammerController>getController();
        pcontroller.setServices(service);
        //controller.loadData();


        ctrl.setProgrammerController(pcontroller);
        ctrl.setPParent(proot);



        primaryStage.setTitle("Bug management system");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();


    }
}
