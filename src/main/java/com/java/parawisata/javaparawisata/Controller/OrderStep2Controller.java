package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.ParamRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IParamService;
import com.java.parawisata.javaparawisata.Service.Impl.ParamServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.LoaderComponents;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Helper.Helper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class OrderStep2Controller implements Initializable {
    @FXML
    public VBox vboxBus;

    private OrderController orderController;
    private OrderStep3Controller step3Controller;

    private IParamRepository paramRepository;
    private IParamService paramService;
    private Order orderData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // this.getAllListBus();
    }

    public void getAllListBus(String destination) {
        this.paramRepository = new ParamRepositoryImpl();
        this.paramService = new ParamServiceImpl(this.paramRepository);
        GlobalParameter paramReq = new GlobalParameter();
        paramReq.setGroup("Bus");
        paramReq.setCriteria(destination);
        ControlMessage<List<GlobalParameter>> response =  this.paramService.InquiryGlobalParam(paramReq);
        if (response.isSuccess) {
            ControlMessage<List<AnchorPane>> anchors = this.generateListBusPane(response.getData());
            if (anchors.isSuccess) {
                this.vboxBus.getChildren().addAll(anchors.getData());
            } else {
                ServiceGlobalComponents.showAlertDialog(anchors);
            }
        } else {
            ServiceGlobalComponents.showAlertDialog(response);
        }
    }

    public ControlMessage<List<AnchorPane>> generateListBusPane(List<GlobalParameter> listBus) {
        ControlMessage<List<AnchorPane>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        response.isSuccess = true;
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
                    vBoxFasilitas.setMinSize(150, 70);
                    vBoxFasilitas.setPadding(new Insets(10, 0, 0, 0));
                    vBoxFasilitas.setMaxSize(150, 70);

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

                    String textPrice = "Rp. ".concat(Helper.thousandsSeparator(x.getInfo01()));
                    Label labelPrice = new Label(textPrice);
                    hboxHarga.getChildren().add(labelPrice);
                    vBoxHarga.getChildren().add(hboxHarga);

                    MFXButton btnPesan = new MFXButton("Pesan");
                    btnPesan.setMinSize(85, 46);
                    btnPesan.setStyle("-fx-background-color: #7a0ed9;-fx-background-radius:10");
                    btnPesan.setTextFill(Color.web("WHITE"));
                    btnPesan.setButtonType(ButtonType.RAISED);
                    btnPesan.setDepthLevel(DepthLevel.LEVEL2);
                    btnPesan.setCursor(Cursor.HAND);
                    btnPesan.setOnAction(event -> {
                        try {
                            this.onBtnChooseBusAction(x.getValue());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    // Set Child HBox
                    hBox.getChildren().addAll(List.of(fsBus, labelBus, line, vBoxFasilitas, vBoxHarga, btnPesan));
                    ServiceGlobalComponents.setAnchorConstraints(hBox, 0.0);
                    // Set Child AnchorPane
                    root.getChildren().add(hBox);

                    response.getData().add(root);
                });
            }
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    public void setParentController(OrderController controller) {
        this.orderController = controller;
    }

    public void setParentOrderData(Order data) {
        this.orderData = data;
    }

    public void onBtnChooseBusAction(String busIDPrice) throws IOException {
        this.orderData.setBusID(busIDPrice);
        LoaderComponents<OrderStep3Controller> loaderStep3 = ServiceGlobalComponents.generateLoaderFXMLPage("fxml/order-step3-view.fxml");
        this.orderController.icnBus.setFill(Color.web("#01e419"));
        this.orderController.pLv3.setStroke(Color.web("#01e419"));
        this.orderController.orderContent.getChildren().setAll(loaderStep3.getAnchorPane());
        this.step3Controller = loaderStep3.getController();
        this.step3Controller.setParentController(this.orderController);
        this.step3Controller.onSetData(this.orderData);
        this.step3Controller.lblKet.setText(this.orderData.getBusID().substring(0, 4).concat(String.valueOf(Date.valueOf(LocalDate.now().toString()))));
    }
}
