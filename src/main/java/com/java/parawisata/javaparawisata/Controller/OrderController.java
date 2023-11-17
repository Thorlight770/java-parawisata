package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import io.github.palexdev.materialfx.controls.MFXStepper;
import io.github.palexdev.materialfx.controls.MFXStepperToggle;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    @FXML
    public MFXStepper stepperPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<MFXStepperToggle> stepperToggleList = createSteps();
        this.stepperPane.getStepperToggles().addAll(stepperToggleList);
    }

    private List<MFXStepperToggle> createSteps() {
        MFXStepperToggle step1 = new MFXStepperToggle("Step 1", new MFXFontIcon("mfx-map", 16, Color.web("#f1c40f")));
        step1.setContent(ServiceGlobalComponents.generateFXMLPage("fxml/order-step1-view.fxml"));
        ServiceGlobalComponents.setAnchorConstraints(step1, 0.0);

        MFXStepperToggle step2 = new MFXStepperToggle("Step 2", new MFXFontIcon("mfx-calendars", 16, Color.web("#49a6d7")));
        step2.setContent(ServiceGlobalComponents.generateFXMLPage("fxml/order-step2-view.fxml"));
        ServiceGlobalComponents.setAnchorConstraints(step2, 0.0);

        MFXStepperToggle step3 = new MFXStepperToggle("Step 3", new MFXFontIcon("mfx-variant5-mark", 16, Color.web("#85CB33")));
        step3.setContent(ServiceGlobalComponents.generateFXMLPage("fxml/order-step3-view.fxml"));
        ServiceGlobalComponents.setAnchorConstraints(step3, 0.0);

        return List.of(step1, step2, step3);
    }

    public void regenerateStepper() {
        this.stepperPane.getStepperToggles().removeAll();
        this.stepperPane.getStepperToggles().get(1).setContent(ServiceGlobalComponents.generateFXMLPage("fxml/order-step2-view.fxml"));
    }
}
