package com.java.parawisata.javaparawisata.Controller;

import io.github.palexdev.materialfx.beans.Alignment;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;

public class OrderStep1Controller implements Initializable {
    @FXML
    public MFXComboBox<String> comboDestination;

    @FXML
    public MFXComboBox<String> comboPoint;

    @FXML
    public MFXDatePicker dateFrom;

    @FXML
    public MFXDatePicker dateTo;

    @FXML
    public MFXCheckbox isOneDay;

    @FXML
    public MFXButton submit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dateFrom.setPopupAlignment(new Alignment(HPos.CENTER, VPos.TOP));
        this.dateFrom.setClosePopupOnChange(true);
        this.dateTo.setPopupAlignment(new Alignment(HPos.CENTER, VPos.TOP));
    }
}
