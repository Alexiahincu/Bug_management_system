package com.example.client.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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


public class LoginController implements IObserver {

//    private ChatServicesJsonProxy service;
    private IService service;

    @FXML
    TextField userTxt, passwordTxt;
    Parent tParent, pParent;
    @FXML
    ComboBox<String> userOptions;
    private TesterController testerController;
    private ProgrammerController programmerController;

    @FXML
    public void initialize() {
        // Add options to the ComboBox
        userOptions.setItems(FXCollections.observableArrayList("Tester", "Programmer"));
    }

    public void setTesterController(TesterController testerController) {
        this.testerController = testerController;
    }
    public void setProgrammerController(ProgrammerController programmerController) {
        this.programmerController = programmerController;
    }

//    public void setServer(ChatServicesJsonProxy s){
//        service = s;
//    }


    public void setService(IService service) {
        this.service = service;
    }

    public void setTParent(Parent p){
        tParent=p;
    }

    public void setPParent(Parent pParent) {
        this.pParent = pParent;
    }

    public void pressLogin(ActionEvent actionEvent) {
        //Parent root;
        String username = userTxt.getText();
        String password = passwordTxt.getText();
        String selected = userOptions.getValue();

        try{
            if(selected.equals("Tester")){
                Tester tester = service.tLogin(username, password, testerController);
                if (tester == null)
                    throw new Exception("Wrong username or password");

                Stage stage=new Stage();
                stage.setTitle("Welcome, " + tester.getName());
                stage.setScene(new Scene(tParent, 800, 700));

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        //ProgrammerController.btnLogOutPressed();
                        System.exit(0);
                    }
                });

                //ProgrammerController.loadData();
                stage.show();
                testerController.setTester(tester);
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }

            else if(selected.equals("Programmer")) {

                Programmer programmer = service.pLogin(username, password, programmerController);
                if (programmer == null)
                    throw new Exception("Wrong username or password");

                Stage stage=new Stage();
                stage.setTitle("Welcome, " + programmer.getName());
                stage.setScene(new Scene(pParent, 800, 700));

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        //ProgrammerController.btnLogOutPressed();
                        System.exit(0);
                    }
                });

                //ProgrammerController.loadData();
                stage.show();
                programmerController.setProgrammer(programmer);
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }

           // Util.writeLog("User succesfully Programmer in "+crtUser.getId());

        }   catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MPP ProgrammerIn");
                alert.setHeaderText("Authentication failure");
//                alert.setContentText("Wrong username or password");
            System.out.println(e.getMessage());
            alert.setContentText(e.getMessage());
                alert.showAndWait();
            }


    }

    public void pressCancel(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void update() {}

}
