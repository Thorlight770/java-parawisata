package com.java.parawisata.javaparawisata.Utils.Components;

import com.google.common.io.Files;
import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Helper.Helper;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Provider;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServiceGlobalComponents {
    private static MFXGenericDialog dialogContent;
    private static MFXStageDialog dialog;

    private static  MFXGenericDialog dialogContentConfirm;
    private static MFXStageDialog dialogConfirm;

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
    public static <T> LoaderComponents<T> generateLoaderFXMLPage(String resource) throws IOException {
        LoaderComponents<T> response = new LoaderComponents<>();
        FXMLLoader fxmlLoader = new FXMLLoader(JavaParawisataApp.class.getResource(resource));
        response.setAnchorPane(fxmlLoader.load());
        response.setController(fxmlLoader.getController());
        AnchorPane.setTopAnchor(response.getAnchorPane(), 0.0);
        AnchorPane.setBottomAnchor(response.getAnchorPane(), 0.0);
        AnchorPane.setLeftAnchor(response.getAnchorPane(), 0.0);
        AnchorPane.setRightAnchor(response.getAnchorPane(), 0.0);
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

    public static void showPopUpDialog(FXMLLoader loader, String title) throws IOException {
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(JavaParawisataApp.class.getResourceAsStream("icons/bus.png")));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void showAlertDialog(String headerText, MessageType type, List<String> listMessage) {
        ControlMessage responseMessage = new ControlMessage();
        listMessage.forEach(x -> {
            responseMessage.messages.add(new AdditionalMessage(type, x));
        });
        showAlertDialog(headerText, responseMessage);
    }
    public static void showAlertDialog(ControlMessage response) {
        if (response.getMaxMessageType().getName().equals("DEFAULT") || response.getMaxMessageType().getName().equals("SUCCESS"))
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

    public static AtomicBoolean showConfirmationDialog(String headerText, String bodyText) {
        AtomicBoolean response = new AtomicBoolean(false);
        dialogContentConfirm = MFXGenericDialogBuilder.build()
                .setContentText("\n\t".concat(bodyText))
                .makeScrollable(true)
                .get();

        dialogConfirm = MFXGenericDialogBuilder.build(dialogContentConfirm)
                .toStageDialogBuilder()
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Dialog Preview")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        MFXButton okBtn = new MFXButton("Confirm");
        okBtn.setCursor(Cursor.HAND);
        okBtn.setTextFill(Color.WHITE);
        okBtn.setStyle("-fx-background-color: GREEN");

        MFXButton cancelBtn = new MFXButton("Cancel");
        cancelBtn.setCursor(Cursor.HAND);
        cancelBtn.setTextFill(Color.WHITE);
        cancelBtn.setStyle("-fx-background-color: RED");

        dialogContentConfirm.addActions(
                Map.entry(okBtn, mouseEvent -> {
                    response.set(true);
                    dialogConfirm.close();
                }),
                Map.entry(cancelBtn,mouseEvent -> dialogConfirm.close())
        );

        MFXFontIcon warnIcon = new MFXFontIcon("mfx-info-circle", 24);
        warnIcon.setColor(Color.YELLOW);
        dialogContentConfirm.setHeaderIcon(warnIcon);

        dialogContentConfirm.setHeaderText(headerText);

        dialogContentConfirm.setMaxSize(400, 200);

        dialogConfirm.showAndWait();

        return response;
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

    public static ControlMessage<Boolean> uploadFileChooser(File sourceFile, String fileName) {
        ControlMessage<Boolean> response = new ControlMessage<>();
        response.isSuccess = true;
        response.data = true;
        try {
            if (sourceFile != null) {
                if (!sourceFile.exists()) {
                    response.messages.add(new AdditionalMessage(MessageType.ERROR, "File Not Exists !"));
                }

                Path pathFolder = Paths.get("fileTransfer").toAbsolutePath();
                File folder = new File(String.valueOf(pathFolder));

                if (!folder.exists())
                    folder.mkdir();

                Path pathFile = Paths.get("fileTransfer/".concat(fileName)).toAbsolutePath();
                File destFile = new File(String.valueOf(pathFile));

                if (!destFile.exists()) {
                    Files.touch(destFile);
                }

                FileChannel source = null;
                FileChannel destination = null;
                source = new FileInputStream(sourceFile).getChannel();
                destination = new FileOutputStream(destFile).getChannel();
                if (destination != null && source != null) {
                    destination.transferFrom(source, 0, source.size());
                }
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
            }
            else {
                response.isSuccess = false;
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Tidak Ada File Yang Dipilih !"));
            }
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = false;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
