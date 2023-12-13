package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.ParamRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IParamService;
import com.java.parawisata.javaparawisata.Service.Impl.ParamServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.LoaderComponents;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
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

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

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

    private IParamRepository paramRepository;
    private IParamService paramService;
    private GlobalParameter menuRequest = new GlobalParameter();
    private Auth globalUser = new Auth();

    public JavaParawisataController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setMenus(String role, String username) {
        lblUsername.setText(username);
        menuRequest.setGroup("Menus");
        menuRequest.setCriteria(role);
        ControlMessage<List<GlobalParameter>> menus = this.getMenus(menuRequest);
        if (menus.isSuccess) {
            this.rootMenus.getChildren().addAll(generateMenus(menus.data));

            if (menuRequest.getCriteria().equals("Administrator")) {
                this.lblHeader.setText("Driver Maintenance");
                this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/driver-maint-view.fxml"));
                this.contentPane.autosize();
            } else if (menuRequest.getCriteria().equals("Customer")) {
                this.lblHeader.setText("Dashboard");
                this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/dashboard-view.fxml"));
                this.contentPane.autosize();
            }
        } else ServiceGlobalComponents.showAlertDialog(menus);
    }
    public ControlMessage<List<GlobalParameter>> getMenus(GlobalParameter data) {
        paramRepository = new ParamRepositoryImpl();
        paramService = new ParamServiceImpl(paramRepository);
        return paramService.InquiryGlobalParam(data);
    }

    //<editor-fold desc="Customer Menus FXML">
    public void onBtnDashboardClick(MouseEvent event) throws IOException {
        this.lblHeader.setText("Dashboard");
        LoaderComponents<DashboardController> dashboard = ServiceGlobalComponents.generateLoaderFXMLPage("fxml/dashboard-view.fxml");
        dashboard.getController().onSetAuth(this.globalUser);
        this.contentPane.getChildren().setAll(dashboard.getAnchorPane());
        this.contentPane.autosize();
    }

    public void onBtnHistoryOrderClick(MouseEvent event) throws IOException {
        this.lblHeader.setText("History Order");
        LoaderComponents<HistoryOrderController> historyOrder = ServiceGlobalComponents.generateLoaderFXMLPage("fxml/history-order-view.fxml");
        historyOrder.getController().onSetAuth(this.globalUser);
        this.contentPane.getChildren().setAll(historyOrder.getAnchorPane());
        this.contentPane.autosize();
    }

    public void onBtnOrderBusClick(MouseEvent event) throws IOException {
        this.lblHeader.setText("Order Bus Schedule");
        LoaderComponents<OrderController> order = ServiceGlobalComponents.generateLoaderFXMLPage("fxml/order-view.fxml");
        order.getController().onSetAuth(this.globalUser);
        order.getController().onStartStepperOrder();
        this.contentPane.getChildren().setAll(order.getAnchorPane());
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
    public void onBtnSignOutClick(MouseEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(JavaParawisataApp.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
    //</editor-fold>

    //<editor-fold desc="Administrator Menus FXML">
    public void onBtnDriverMaintenanceClick(MouseEvent event) {
        this.lblHeader.setText("Driver Maintenance");
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/driver-maint-view.fxml"));
        this.contentPane.autosize();
    }
    public void onBtnBusMaintenanceClick(MouseEvent event) {
        this.lblHeader.setText("Bus Maintenance");
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/bus-maint-view.fxml"));
        this.contentPane.autosize();
    }
    public void onBtnOrderApprovalClick(MouseEvent event) {
        this.lblHeader.setText("Order Approval");
        this.contentPane.getChildren().setAll(this.generateFXMLPage("fxml/order-approval-view.fxml"));
        this.contentPane.autosize();
    }
    //</editor-fold>

    //<editor-fold desc="Generate Menu & FXML">
    private List<HBox> generateMenus(List<GlobalParameter> listMenu) {
        List<HBox> response = new ArrayList<HBox>();
        try {
            if (!listMenu.isEmpty()) {
                IntStream.range(0, listMenu.size()).forEach(index -> {
                    GlobalParameter x = listMenu.get(index);
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
                            try {
                                this.onBtnDashboardClick(e);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else if (x.getText().equalsIgnoreCase("ORDER BUS")) {
                            try {
                                this.onBtnOrderBusClick(e);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else if (x.getText().equalsIgnoreCase("HISTORY ORDER")) {
                            try {
                                this.onBtnHistoryOrderClick(e);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else if (x.getText().equalsIgnoreCase("DRIVER MAINTENANCE")) {
                            this.onBtnDriverMaintenanceClick(e);
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

    public void onSetUser(Auth user) {
        this.globalUser = user;
    }
}
