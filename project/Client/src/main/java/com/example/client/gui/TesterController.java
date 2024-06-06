package com.example.client.gui;

import com.example.client.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Domain.Bug;
import org.example.Domain.Tester;
import org.example.Service.IObserver;
import org.example.Service.IService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TesterController implements IObserver {
    public ListView<String> listBug = new ListView<>();
    public TextField txtName, txtDesc;
    public Button btnAdd, btnLogOut, btnShowAll, btnLink;
    private Tester tester;
    private IService service;
    ObservableList<String> obsList = FXCollections.observableList(new ArrayList<>());

    public void loadData() throws Exception {
        obsList.clear();
        listBug.setItems(obsList);
        listBug.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Bug> bugs = service.getAllBugs();
        for (Bug elem : bugs) {
            obsList.add(elem.toString());
        }

    }

    public void setServices(IService service) {
        this.service = service;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    public void getInvLink(ActionEvent actionEvent){
        Alert link = new Alert(Alert.AlertType.INFORMATION, "Invitation link: 01234");
        link.show();
    }

    public void btnAddBugPressed(ActionEvent actionEvent){
        try {
            var name = txtName.getText();
            var desc = txtDesc.getText();

            if(name.isEmpty()||desc.isEmpty())
                throw new Exception("Completeaza toate casutele!!!");

            service.AddBug(name, desc);

            obsList.clear();
            loadData();

        } catch (Exception e) {
            Alert hopa = new Alert(Alert.AlertType.ERROR, e.getMessage());
            hopa.show();
            System.out.println(e.getMessage());
        }
    }

    public void btnLogOutPressed(ActionEvent actionEvent) throws IOException {
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
        Platform.runLater(() -> {
            try {
                loadData();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
