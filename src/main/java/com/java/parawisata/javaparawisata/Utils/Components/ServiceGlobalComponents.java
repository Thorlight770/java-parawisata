package com.java.parawisata.javaparawisata.Utils.Components;

import com.java.parawisata.javaparawisata.JavaParawisataApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class ServiceGlobalComponents {
    public static AnchorPane generateFXMLPage(String resource) {
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

    public static void setAnchorConstraints(Node node, Double aDouble) {
        AnchorPane.setLeftAnchor(node, aDouble);
        AnchorPane.setRightAnchor(node, aDouble);
        AnchorPane.setTopAnchor(node, aDouble);
        AnchorPane.setBottomAnchor(node, aDouble);
    }
}
