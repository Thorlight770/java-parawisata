package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Utils.Components.LoaderComponents;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    @FXML
    public FontAwesomeIconView icnBus;

    @FXML
    public FontAwesomeIconView icnCalendar;

    @FXML
    public FontAwesomeIconView icnGlass;

    @FXML
    public AnchorPane orderContent;

    @FXML
    public Line pLv1;

    @FXML
    public Line pLv2;

    @FXML
    public Line pLv3;

    @FXML
    public Line pLv4;

    private OrderStep1Controller step1Controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            LoaderComponents<OrderStep1Controller> loaderStep1 = ServiceGlobalComponents.generateLoaderFXMLPage("fxml/order-step1-view.fxml");
            this.orderContent.getChildren().addAll(loaderStep1.getAnchorPane());
            this.step1Controller = loaderStep1.getController();
            this.step1Controller.setParentController(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void print() {
        System.out.println("PRINT PARENT");
    }
}
