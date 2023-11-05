package com.java.parawisata.javaparawisata.Utils.Dialog;

import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable {
    @FXML
    public DialogPane dialogPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControlMessage<String> response = new ControlMessage<>();
        response.data = "Masuk response data";
        for (int i = 0; i<=5; i++) {
            response.messages.add(new AdditionalMessage(MessageType.ERROR, "Error" + i));
        }

        response.messages.forEach(x -> {
            Label label = new Label();
            label.setText(x.getMessage());
            dialogPane.setContent(label);
        });
    }
}
