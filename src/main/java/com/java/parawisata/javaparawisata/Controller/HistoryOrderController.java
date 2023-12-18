package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.HistoryOrder;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.OrderRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IOrderService;
import com.java.parawisata.javaparawisata.Service.Impl.OrderServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class HistoryOrderController implements Initializable {
    @FXML
    public TableColumn<HistoryOrder, String> colReason;

    @FXML
    public TableColumn<HistoryOrder, String> colBusType;

    @FXML
    public TableColumn<HistoryOrder, Date> colDateOrder;

    @FXML
    public TableColumn<HistoryOrder, String> colDestination;

    @FXML
    public TableColumn<HistoryOrder, String> colDriverName;

    @FXML
    public TableColumn<HistoryOrder, Long> colID;

    @FXML
    public TableColumn<HistoryOrder, String> colOrderID;

    @FXML
    public TableColumn<HistoryOrder, String> colPickUpPoint;

    @FXML
    public TableColumn<HistoryOrder, String> colStatus;

    @FXML
    public TableView<HistoryOrder> tableHistoryOrder;

    private IOrderRepository orderRepository;
    private HistoryOrder historyOrderData;
    private Auth globalUser = new Auth();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onSetTable() {
        ControlMessage<List<HistoryOrder>> historyOrders = this.getAllHistoryOrderCustomer();
        if (historyOrders.isSuccess) {
            this.setUpTableHistoryOrder(historyOrders.data);
        } else ServiceGlobalComponents.showAlertDialog(historyOrders);
    }
    private void setUpTableHistoryOrder(List<HistoryOrder> historyOrders) {
        ObservableList<HistoryOrder> list = FXCollections.observableArrayList(historyOrders);

        colID.setCellValueFactory(new PropertyValueFactory<HistoryOrder, Long>("idHist"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("orderID"));
        colBusType.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("busName"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("driverName"));
        colPickUpPoint.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("pickUpPoint"));
        colDestination.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("destination"));
        colStatus.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("status"));
        colDateOrder.setCellValueFactory(new PropertyValueFactory<HistoryOrder, Date>("orderDate"));
        colReason.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("reason"));

        tableHistoryOrder.setItems(list);
    }

    private void updateHistoryOrder() {
        System.out.println("Masuk Method");
    }

    private ControlMessage<List<HistoryOrder>> getAllHistoryOrderCustomer() {
        this.orderRepository = new OrderRepositoryImpl();
        IOrderService orderService = new OrderServiceImpl(this.orderRepository);
        return orderService.getAllHistoryOrderByUserID(this.globalUser.getUserID());
    }
    public void onSetAuth(Auth user) {
        this.globalUser = user;
    }
}
