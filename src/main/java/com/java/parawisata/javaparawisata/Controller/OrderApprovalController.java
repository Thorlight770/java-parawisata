package com.java.parawisata.javaparawisata.Controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrderApprovalController implements Initializable {
    @FXML
    public TableColumn<?, ?> colAction;

    @FXML
    public TableColumn<?, ?> colBusName;

    @FXML
    public TableColumn<?, ?> colCreatedDate;

    @FXML
    public TableColumn<?, ?> colCustomerName;

    @FXML
    public TableColumn<?, ?> colDestination;

    @FXML
    public TableColumn<?, ?> colDriverName;

    @FXML
    public TableColumn<?, ?> colDuration;

    @FXML
    public TableColumn<?, ?> colOrderID;

    @FXML
    public TableColumn<?, ?> colPickUpPoint;

    @FXML
    public TableColumn<?, ?> colStatusPayment;

    @FXML
    public Label lblCustomerName;

    @FXML
    public Label lblDestination;

    @FXML
    public Label lblOrderID;

    @FXML
    public Label lblPickUpPoint;

    @FXML
    public Label lblStatusPayment;

    @FXML
    public TableView<?> tableOrderApv;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
