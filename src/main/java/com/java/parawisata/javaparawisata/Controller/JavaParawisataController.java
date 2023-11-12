package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.JavaParawisataApp;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class JavaParawisataController implements Initializable {
    @FXML
    public HBox btnDashboard;

    @FXML
    public HBox btnHistoryOrder;

    @FXML
    public FontAwesomeIconView btnNotification;

    @FXML
    public HBox btnOrderBus;

    @FXML
    public FontAwesomeIconView btnSetting;

    @FXML
    public HBox btnSignOut;

    @FXML
    public AnchorPane contentPane;

    @FXML
    public Label lblHeader;

    @FXML
    public Label lblUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/dashboard-view.fxml"));
        this.contentPane.autosize();
    }

    @FXML
    public void onBtnDashboardClick(MouseEvent event) {
        this.lblHeader.setText("Dashboard");
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/dashboard-view.fxml"));
        this.contentPane.autosize();
    }

    @FXML
    public void onBtnHistoryOrderClick(MouseEvent event) {
        this.lblHeader.setText("History Order");
    }

    @FXML
    public void onBtnNotificationClick(MouseEvent event) {

    }

    @FXML
    public void onBtnOrderBusClick(MouseEvent event) {
        this.lblHeader.setText("Order Bus Schedule");
    }

    @FXML
    public void onBtnSettingClick(MouseEvent event) {
        this.lblHeader.setText("Setting Profile");
    }

    @FXML
    public void onBtnSignOutClick(MouseEvent event) {

    }

    private AnchorPane generateFXMLPage(String resource) {
        AnchorPane response = new AnchorPane();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(JavaParawisataApp.class.getResource(resource));
            response = (AnchorPane) fxmlLoader.load();
            AnchorPane.setTopAnchor(response, 0.0);
            AnchorPane.setBottomAnchor(response, 0.0);
            AnchorPane.setLeftAnchor(response, 0.0);
            AnchorPane.setRightAnchor(response, 0.0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
