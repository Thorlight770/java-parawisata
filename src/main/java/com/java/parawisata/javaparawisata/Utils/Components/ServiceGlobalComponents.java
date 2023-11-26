package com.java.parawisata.javaparawisata.Utils.Components;

import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class ServiceGlobalComponents {
    private static MFXGenericDialog dialogContent;
    private static MFXStageDialog dialog;

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

    public static FXMLLoader generateFXMLLoader(String resource) {
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(JavaParawisataApp.class.getResource(resource));
        try {
            loader.load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return loader;
    }

    public static void showPopUpDialog(FXMLLoader loader, String title) {
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.initModality(Modality.WINDOW_MODAL);
        // stage.getIcons().add(new Image("/image/icon.png"));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void showAlertDialog(ControlMessage response) {
        if (response.getMaxMessageType().getName().equals("DEFAULT"))
            showAlertDialog("Success", response);
        else if (response.getMaxMessageType().getValue() == 4)
            showAlertDialog("Error", response);
        else showAlertDialog(response.getMaxMessageType().getName(), response);
    }
    public static void showAlertDialog(String headerText, ControlMessage response) {
        Platform.runLater(() -> {
            dialogContent = MFXGenericDialogBuilder.build()
                    .setContentText(generateMessage(response.getMessages()))
                    .makeScrollable(true)
                    .get();

            dialog = MFXGenericDialogBuilder.build(dialogContent)
                    .toStageDialogBuilder()
                    .initModality(Modality.APPLICATION_MODAL)
                    .setDraggable(true)
                    .setTitle("Dialogs Preview")
                    .setScrimPriority(ScrimPriority.WINDOW)
                    .setScrimOwner(true)
                    .get();

            MFXButton okBtn = new MFXButton("Ok");
            okBtn.setCursor(Cursor.HAND);
            dialogContent.addActions(
                    Map.entry(okBtn,event -> dialog.close())
            );

            dialogContent.setMaxSize(400, 200);

            MFXFontIcon icon = new MFXFontIcon();
            if (headerText.equals("Error")) {
                icon.setDescription("mfx-x-circle");
                icon.setSize(18);
                icon.setColor(Color.web("RED"));
                dialogContent.setHeaderIcon(icon);
            } else if (headerText.equals("Success")) {
                icon.setDescription("mfx-check-circle");
                icon.setSize(18);
                icon.setColor(Color.web("GREEN"));
                dialogContent.setHeaderIcon(icon);
            } else {
                icon.setDescription("mfx-info-circle");
                icon.setSize(18);
                icon.setColor(Color.web("YELLOW"));
                dialogContent.setHeaderIcon(icon);
            }

            dialogContent.setHeaderText(headerText);

            dialog.showDialog();
        });
    }

    public static String generateMessage(List<AdditionalMessage> listMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        if (!listMessage.isEmpty()) {
            listMessage.forEach(x -> {
                sb.append("\t" + x.getType().getName() + " - " + x.getMessage() + "\n");
            });
        } else sb.append("\t" + "Success");
        return sb.toString();
    }
}
