package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class OrderStep2Controller implements Initializable {
    private OrderController orderController;
    @FXML
    public VBox vboxBus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vboxBus.getChildren().addAll(this.generateListBusPane(List.of(
                new GlobalParameter("", "", "Bus Besar", "Bus Besar Text", "Rp.1,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus VIP Class", "Rp.3,500,000,-", "Wifi|Speaker|Eat", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|Eat|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|Eat|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L),
                new GlobalParameter("", "", "Bus Besar", "Bus PLATINUM Class", "Rp.4,500,000,-", "Wifi|SPEAKER|CHARGE|Ac", 1L)
        )));
    }

    public List<AnchorPane> generateListBusPane(List<GlobalParameter> listBus) {
        List<AnchorPane> response = new ArrayList<AnchorPane>();
        try {
            if (!listBus.isEmpty()) {
                listBus.forEach(x -> {
                    AnchorPane root = new AnchorPane();
                    root.setMinSize(200, 70);
                    root.getStyleClass().add("contentBus");

                    // Create HBox for List
                    HBox hBox = new HBox();
                    hBox.setSpacing(10);
                    hBox.setAlignment(Pos.CENTER_RIGHT);

                    // Create Icon
                    FontAwesomeIconView fsBus = new FontAwesomeIconView();
                    fsBus.setGlyphName("BUS");
                    fsBus.setSize("40");

                    // Create Label
                    Label labelBus = new Label(x.getText());
                    labelBus.setMinSize(100, 60);
                    labelBus.setMaxSize(100, 60);
                    labelBus.setWrapText(true);

                    // Create Line
                    Line line = new Line(0,0,0,50);
                    line.setStrokeWidth(3);

                    // Create VBox Fasilitas
                    VBox vBoxFasilitas = new VBox();
                    vBoxFasilitas.setMinSize(98, 70);
                    vBoxFasilitas.setPadding(new Insets(10, 0, 0, 0));
                    vBoxFasilitas.setMaxSize(98, 70);

                    Label labelFasilitas = new Label("Fasilitas:");
                    vBoxFasilitas.getChildren().add(labelFasilitas);

                    HBox hBoxFasilitas = new HBox();
                    hBoxFasilitas.setSpacing(5);
                    hBoxFasilitas.setAlignment(Pos.CENTER_LEFT);
                    hBoxFasilitas.setPadding(new Insets(10, 0, 0, 0));
                    if (!x.getInfo02().isEmpty()) {
                        String[] fasilitas = x.getInfo02().split("\\|");
                        for (String data : fasilitas) {
                            FontAwesomeIconView fs = new FontAwesomeIconView();
                            if (data.equalsIgnoreCase("WIFI")) {
                                fs.setSize("20");
                                fs.setGlyphName("RSS");
                            } else if (data.equalsIgnoreCase("CHARGE")) {
                                fs.setSize("20");
                                fs.setGlyphName("PLUG");
                            } else if (data.equalsIgnoreCase("MICROPHONE")) {
                                fs.setSize("20");
                                fs.setGlyphName("MICROPHONE");
                            } else if (data.equalsIgnoreCase("AC")) {
                                fs.setSize("20");
                                fs.setGlyphName("THERMOMETER_QUARTER");
                            } else if (data.equalsIgnoreCase("EAT")) {
                                fs.setSize("20");
                                fs.setGlyphName("CUTLERY");
                            } else if (data.equalsIgnoreCase("LUGGAGE")) {
                                fs.setSize("20");
                                fs.setGlyphName("SHOPPING_BAG");
                            } else continue;
                            hBoxFasilitas.getChildren().add(fs);
                        }
                    }
                    vBoxFasilitas.getChildren().add(hBoxFasilitas);

                    // Create VBox Fasilitas
                    VBox vBoxHarga = new VBox();
                    vBoxHarga.setMinSize(119, 70);
                    vBoxHarga.setPadding(new Insets(10, 0, 0, 0));

                    Label labelHarga = new Label("Harga:");
                    vBoxHarga.getChildren().add(labelHarga);

                    HBox hboxHarga = new HBox();
                    hboxHarga.setSpacing(5);
                    hboxHarga.setAlignment(Pos.CENTER_LEFT);
                    hboxHarga.setPadding(new Insets(10, 0, 0, 0));

                    Label labelPrice = new Label(x.getInfo01());
                    hboxHarga.getChildren().add(labelPrice);
                    vBoxHarga.getChildren().add(hboxHarga);

                    MFXButton btnPesan = new MFXButton("Pesan");
                    btnPesan.setMinSize(85, 46);
                    btnPesan.setStyle("-fx-background-color: #7a0ed9;-fx-background-radius:10");
                    btnPesan.setTextFill(Color.web("WHITE"));
                    btnPesan.setButtonType(ButtonType.RAISED);
                    btnPesan.setDepthLevel(DepthLevel.LEVEL2);

                    // Set Child HBox
                    hBox.getChildren().addAll(List.of(fsBus, labelBus, line, vBoxFasilitas, vBoxHarga, btnPesan));
                    ServiceGlobalComponents.setAnchorConstraints(hBox, 0.0);
                    // Set Child AnchorPane
                    root.getChildren().add(hBox);

                    response.add(root);
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
