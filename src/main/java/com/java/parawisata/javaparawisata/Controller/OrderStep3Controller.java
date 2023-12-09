package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.OrderRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IOrderService;
import com.java.parawisata.javaparawisata.Service.Impl.OrderServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.LoaderComponents;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class OrderStep3Controller implements Initializable {
    @FXML
    public MFXButton cancelBtn;

    @FXML
    public MFXButton saveBtn;

    @FXML
    public MFXButton uploadBtn;
    @FXML
    public Label lblKet;

    private OrderController orderController;
    private OrderStep1Controller step1Controller;
    private Order orderData;
    private boolean isUpload;

    private IOrderRepository orderRepository;
    private IOrderService orderService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.saveBtn.setDisable(true);
    }

    @FXML
    public void onCancelOrder(ActionEvent event) throws IOException {
        AtomicBoolean res = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Apakah anda yakin ingin membatalkan Order ?");
        if (res.get()) {
            LoaderComponents<OrderStep1Controller> loaderStep1 = ServiceGlobalComponents.generateLoaderFXMLPage("fxml/order-step1-view.fxml");
            this.orderController.orderContent.getChildren().setAll(loaderStep1.getAnchorPane());
            this.step1Controller = loaderStep1.getController();
            this.step1Controller.setParentController(this.orderController);
            this.orderController.pLv3.setStroke(Color.web("#D4CBD7"));
            this.orderController.pLv2.setStroke(Color.web("#D4CBD7"));
            this.orderController.icnCalendar.setFill(Color.web("#D4CBD7"));
            this.orderController.icnBus.setFill(Color.web("#D4CBD7"));
        }
    }

    @FXML
    public void onUploadOrder(ActionEvent event) throws IOException, URISyntaxException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Stage stage = new Stage();
        File sourceFile = fileChooser.showOpenDialog(stage);

        ControlMessage<Boolean> uploadFile = ServiceGlobalComponents.uploadFileChooser(sourceFile, lblKet.getText().concat(".jpg"));
        if (!uploadFile.isSuccess)
            ServiceGlobalComponents.showAlertDialog(uploadFile);
        else {
            uploadFile.messages.add(new AdditionalMessage(MessageType.SUCCESS, "Upload File Order !"));
            ServiceGlobalComponents.showAlertDialog(uploadFile);
            this.saveBtn.setDisable(false);
        }
    }

    @FXML
    public void onSaveOrder(ActionEvent event) throws IOException {
        if (isUpload) {
            this.orderRepository = new OrderRepositoryImpl();
            this.orderService = new OrderServiceImpl(this.orderRepository);
            ControlMessage<Order> response = this.orderService.onAddOrder(this.orderData);
            ServiceGlobalComponents.showAlertDialog(response);
            if (response.isSuccess) {
                LoaderComponents<OrderStep1Controller> loaderStep1 = ServiceGlobalComponents.generateLoaderFXMLPage("fxml/order-step1-view.fxml");
                this.orderController.orderContent.getChildren().setAll(loaderStep1.getAnchorPane());
                this.step1Controller = loaderStep1.getController();
                this.step1Controller.setParentController(this.orderController);
                this.orderController.pLv3.setStroke(Color.web("#D4CBD7"));
                this.orderController.pLv2.setStroke(Color.web("#D4CBD7"));
                this.orderController.icnCalendar.setFill(Color.web("#D4CBD7"));
                this.orderController.icnBus.setFill(Color.web("#D4CBD7"));
            }
        } else {
            ControlMessage message = new ControlMessage<>();
            message.messages.add(new AdditionalMessage(MessageType.ERROR, "File Transfer Belum Di Upload !"));
            ServiceGlobalComponents.showAlertDialog("Error", message);
        }
    }

    public void setParentController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void onSetData(Order order) {
        this.orderData = order;
    }
}
