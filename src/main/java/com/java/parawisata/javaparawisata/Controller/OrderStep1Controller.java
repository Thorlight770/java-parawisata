package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.OrderRepositoryImpl;
import com.java.parawisata.javaparawisata.Repository.Impl.ParamRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IOrderService;
import com.java.parawisata.javaparawisata.Service.IParamService;
import com.java.parawisata.javaparawisata.Service.Impl.OrderServiceImpl;
import com.java.parawisata.javaparawisata.Service.Impl.ParamServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.LoaderComponents;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import io.github.palexdev.materialfx.beans.Alignment;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
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

    private IParamRepository paramRepository;

    private IOrderRepository orderRepository;

    private Order orderData;

    private OrderController orderController;

    private OrderStep2Controller step2Controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dateFrom.setPopupAlignment(new Alignment(HPos.CENTER, VPos.TOP));
        this.dateFrom.setClosePopupOnChange(true);
        this.dateTo.setPopupAlignment(new Alignment(HPos.CENTER, VPos.TOP));
        this.getAllParam();
    }

    @FXML
    public void onBtnSubmitClick(ActionEvent event) throws IOException {
//        this.orderRepository = new OrderRepositoryImpl();
//        this.paramRepository = new ParamRepositoryImpl();
//        IOrderService orderService = new OrderServiceImpl(this.orderRepository, this.paramRepository);
//        ControlMessage<Order> response = orderService.onAddOrder(orderData);
//        ServiceGlobalComponents.showAlertDialog(response);
//        if (response.isSuccess) {
        LoaderComponents<OrderStep2Controller> loaderStep2 = ServiceGlobalComponents.generateLoaderFXMLPage("fxml/order-step2-view.fxml");
        this.orderController.orderContent.getChildren().setAll(loaderStep2.getAnchorPane());
        this.step2Controller = loaderStep2.getController();
        this.step2Controller.setParentController(this.orderController);
//        }
    }

    public void getAllParam() {
        ControlMessage<List<GlobalParameter>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        try {
            GlobalParameter reqParam = new GlobalParameter();
            reqParam.setGroup("LocationOrder");

            paramRepository = new ParamRepositoryImpl();
            IParamService paramService = new ParamServiceImpl(paramRepository);

            ControlMessage<List<GlobalParameter>> locationOrderParam = paramService.InquiryGlobalParam(reqParam);
            if (!locationOrderParam.messages.isEmpty()) response.messages.addAll(locationOrderParam.getMessages());
            response.isSuccess = locationOrderParam.isSuccess;
            if (!locationOrderParam.data.isEmpty()) {
                List<String> locations = new ArrayList<>();
                locationOrderParam.data.forEach(x -> {
                    locations.add(x.getValue());
                });
                this.comboPoint.setItems(FXCollections.observableArrayList(locations));
            }

            reqParam.setGroup("Destination");
            ControlMessage<List<GlobalParameter>> destinationParam = paramService.InquiryGlobalParam(reqParam);
            if (!destinationParam.messages.isEmpty()) response.messages.addAll(destinationParam.getMessages());
            response.isSuccess = destinationParam.isSuccess;
            if (!destinationParam.data.isEmpty()) {
                List<String> destinations = new ArrayList<>();
                destinationParam.data.forEach(x -> {
                    destinations.add(x.getValue());
                });
                this.comboDestination.setItems(FXCollections.observableArrayList(destinations));
            }
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
            ServiceGlobalComponents.showAlertDialog(response);
        }
    }

    public void setParentController(OrderController orderController) {
        this.orderController = orderController;
        System.out.println(this.orderController);
    }
}
