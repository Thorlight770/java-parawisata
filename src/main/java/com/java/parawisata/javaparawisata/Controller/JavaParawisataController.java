package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    public VBox rootMenus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        this.rootMenus.getChildren().addAll(
//                generateMenus(List.of(
//                        new GlobalParameter("Menus", "Customer", "DASHBOARD", "Dashboard", "", "", 1L),
//                        new GlobalParameter("Menus", "Customer", "MAP", "Order Bus", "", "", 1L),
//                        new GlobalParameter("Menus", "Customer", "HISTORY", "History Order", "", "", 1L)
//                        ))
//        );
        this.rootMenus.getChildren().addAll(
                generateMenus(List.of(
                        new GlobalParameter("Menus", "Administrator", "ADDRESS_BOOK", "Customer Maintenance", "", "", 1L),
                        new GlobalParameter("Menus", "Administrator", "BUS", "Bus Maintenance", "", "", 1L),
                        new GlobalParameter("Menus", "Administrator", "HOURGLASS_1", "Order Approval", "", "", 1L)
                ))
        );
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/dashboard-view.fxml"));
        this.contentPane.autosize();
    }

    //<editor-fold desc="Customer Menus FXML">
    public void onBtnDashboardClick(MouseEvent event) {
        this.lblHeader.setText("Dashboard");
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/dashboard-view.fxml"));
        this.contentPane.autosize();
    }

    public void onBtnHistoryOrderClick(MouseEvent event) {
        this.lblHeader.setText("History Order");
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/history-order-view.fxml"));
        this.contentPane.autosize();
    }

    public void onBtnOrderBusClick(MouseEvent event) {
        this.lblHeader.setText("Order Bus Schedule");
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/order-view.fxml"));
        this.contentPane.autosize();
    }
    //</editor-fold>

    //<editor-folding desc="Global Button">
    @FXML
    public void onBtnNotificationClick(MouseEvent event) {

    }

    @FXML
    public void onBtnSettingClick(MouseEvent event) {
        this.lblHeader.setText("Setting Profile");
    }

    @FXML
    public void onBtnSignOutClick(MouseEvent event) {

    }
    //</editor-fold>

    //<editor-fold desc="Administrator Menus FXML">
    public void onBtnCustomerMaintenanceClick(MouseEvent event) {
        this.lblHeader.setText("Customer Maintenance");
    }
    public void onBtnBusMaintenanceClick(MouseEvent event) {
        this.lblHeader.setText("Bus Maintenance");
    }
    public void onBtnOrderApprovalClick(MouseEvent event) {
        this.lblHeader.setText("Order Approval");
    }
    //</editor-fold>

    //<editor-fold desc="Generate Menu & FXML">
    private List<HBox> generateMenus(List<GlobalParameter> listMenu) {
        List<HBox> response = new ArrayList<HBox>();
        try {
            if (!listMenu.isEmpty()) {
                listMenu.forEach(x -> {
                    HBox menu = new HBox();
                    menu.setSpacing(10);
                    menu.setPrefSize(200, 50);
                    menu.setMinSize(170, 50);
                    menu.setAlignment(Pos.CENTER_LEFT);
                    menu.setPadding(new Insets(0, 0, 0, 20));
                    menu.setCursor(Cursor.HAND);
                    // SET ACTION MOUSE CLICK
                    menu.setOnMouseClicked(e -> {
                        if (x.getText().equalsIgnoreCase("DASHBOARD")) {
                            this.onBtnDashboardClick(e);
                        } else if (x.getText().equalsIgnoreCase("ORDER BUS")) {
                            this.onBtnOrderBusClick(e);
                        } else if (x.getText().equalsIgnoreCase("HISTORY ORDER")) {
                            this.onBtnHistoryOrderClick(e);
                        } else if (x.getText().equalsIgnoreCase("CUSTOMER MAINTENANCE")) {
                            this.onBtnCustomerMaintenanceClick(e);
                        } else if (x.getText().equalsIgnoreCase("BUS MAINTENANCE")) {
                            this.onBtnBusMaintenanceClick(e);
                        } else if (x.getText().equalsIgnoreCase("ORDER APPROVAL")) {
                            this.onBtnOrderApprovalClick(e);
                        }
                    });

                    Label lblMenu = new Label(x.getText());
                    ServiceGlobalComponents.setAnchorConstraints(lblMenu, 0D);
                    lblMenu.setTextFill(Color.web("#454559"));
                    lblMenu.setWrapText(true);

                    FontAwesomeIconView fsMenu = new FontAwesomeIconView();
                    fsMenu.setGlyphName(x.getValue());
                    fsMenu.setSize("20");
                    fsMenu.setFill(Color.web("#454559"));

                    menu.setOnMouseEntered(e -> {
                        menu.setStyle("-fx-background-color:#272549;-fx-background-radius:10");
                        lblMenu.setTextFill(Color.web("WHITE"));
                        fsMenu.setFill(Color.web("WHITE"));
                    });
                    menu.setOnMouseExited(e -> {
                        menu.setStyle("-fx-background-color:transparent");
                        lblMenu.setTextFill(Color.web("#454559"));
                        fsMenu.setFill(Color.web("#454559"));
                    });

                    menu.getChildren().addAll(List.of(fsMenu, lblMenu));

                    response.add(menu);
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
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
    //</editor-fold>
}
