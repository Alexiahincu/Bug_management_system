package com.example.client.gui;

import com.example.client.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.Domain.Programmer;
import org.example.Domain.Tester;
import org.example.Service.IObserver;
import org.example.Service.IService;
import org.example.Service.Service;

import java.io.IOException;


public class TNewAcc implements IObserver{

    private IService service;

    @FXML
    TextField nameTxt, userTxt, passwordTxt, emailTxt, codeTxt;
    Parent parent;

    private LoginController loginController;

    @FXML
    public void initialize() {
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setServices(IService service) {
        this.service = service;
    }

    public void setParent(Parent p){
        parent=p;
    }

    public void pressCreate(ActionEvent actionEvent) {
        String name = nameTxt.getText();
        String username = userTxt.getText();
        String password = passwordTxt.getText();
        String email = emailTxt.getText();
        String code = codeTxt.getText();

        try{
            if(name.isEmpty()||username.isEmpty()||password.isEmpty()||email.isEmpty()||code.isEmpty())
                throw new Exception("Completeaza toate casutele!!!");

            if(!code.equals("01234")) // validare prin cod
                throw new Exception("Cod incorect!!!");

            service.AddTester(name, username, password, email);

            Stage secondaryStage = new Stage();

            // hello view

            FXMLLoader loader = new FXMLLoader(
                    Main.class.getClassLoader().getResource("hello-view.fxml"));
            Parent root=loader.load();


            LoginController ctrl =
                    loader.getController();
            ctrl.setService(service);

            // tester

            FXMLLoader tloader = new FXMLLoader(
                    getClass().getClassLoader().getResource("tester.fxml"));
            Parent troot=tloader.load();


            TesterController tcontroller =
                    tloader.<TesterController>getController();
            tcontroller.setServices(service);

            ctrl.setTesterController(tcontroller);
            ctrl.setTParent(troot);

            // programmer

            FXMLLoader ploader = new FXMLLoader(
                    getClass().getClassLoader().getResource("programmer.fxml"));
            Parent proot=ploader.load();


            ProgrammerController pcontroller =
                    ploader.<ProgrammerController>getController();
            pcontroller.setServices(service);


            ctrl.setProgrammerController(pcontroller);
            ctrl.setPParent(proot);

            // new tester

            FXMLLoader ntloader = new FXMLLoader(
                    getClass().getClassLoader().getResource("newTester.fxml"));
            Parent ntroot=ntloader.load();


            TNewAcc tncontroller =
                    ntloader.<TNewAcc>getController();
            tncontroller.setServices(service);


            ctrl.setTNewAcc(tncontroller);
            ctrl.setNtParent(ntroot);

            // new programmer

            FXMLLoader nploader = new FXMLLoader(
                    getClass().getClassLoader().getResource("newProgrammer.fxml"));
            Parent nproot=nploader.load();


            PNewAcc pncontroller =
                    nploader.<PNewAcc>getController();
            pncontroller.setServices(service);


            ctrl.setPNewAcc(pncontroller);
            ctrl.setNpParent(nproot);


            // show primary stage

            secondaryStage.setTitle("Bug management system");
            secondaryStage.setScene(new Scene(root, 450, 150));
            secondaryStage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();


        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP ProgrammerIn");
            alert.setHeaderText("Authentication failure");
            System.out.println(e.getMessage());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    public void pressCancel(ActionEvent actionEvent) throws IOException {
        Stage secondaryStage = new Stage();

        // hello view

        FXMLLoader loader = new FXMLLoader(
                Main.class.getClassLoader().getResource("hello-view.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.getController();
        ctrl.setService(service);

        // tester

        FXMLLoader tloader = new FXMLLoader(
                getClass().getClassLoader().getResource("tester.fxml"));
        Parent troot=tloader.load();


        TesterController tcontroller =
                tloader.<TesterController>getController();
        tcontroller.setServices(service);

        ctrl.setTesterController(tcontroller);
        ctrl.setTParent(troot);

        // programmer

        FXMLLoader ploader = new FXMLLoader(
                getClass().getClassLoader().getResource("programmer.fxml"));
        Parent proot=ploader.load();


        ProgrammerController pcontroller =
                ploader.<ProgrammerController>getController();
        pcontroller.setServices(service);


        ctrl.setProgrammerController(pcontroller);
        ctrl.setPParent(proot);

        // new tester

        FXMLLoader ntloader = new FXMLLoader(
                getClass().getClassLoader().getResource("newTester.fxml"));
        Parent ntroot=ntloader.load();


        TNewAcc tncontroller =
                ntloader.<TNewAcc>getController();
        tncontroller.setServices(service);


        ctrl.setTNewAcc(tncontroller);
        ctrl.setNtParent(ntroot);

        // new programmer

        FXMLLoader nploader = new FXMLLoader(
                getClass().getClassLoader().getResource("newProgrammer.fxml"));
        Parent nproot=nploader.load();


        PNewAcc pncontroller =
                nploader.<PNewAcc>getController();
        pncontroller.setServices(service);


        ctrl.setPNewAcc(pncontroller);
        ctrl.setNpParent(nproot);


        // show primary stage

        secondaryStage.setTitle("Bug management system");
        secondaryStage.setScene(new Scene(root, 450, 150));
        secondaryStage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void update() {
    }
}
