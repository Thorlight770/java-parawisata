package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Entity.OrderApproval;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.OrderRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IOrderService;
import com.java.parawisata.javaparawisata.Service.Impl.BusServiceImpl;
import com.java.parawisata.javaparawisata.Service.Impl.OrderServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class OrderApprovalController implements Initializable {
    @FXML
    public TableColumn<OrderApproval, String> colAction;

    @FXML
    public TableColumn<OrderApproval, String> colBusName;

    @FXML
    public TableColumn<OrderApproval, Date> colCreatedDate;

    @FXML
    public TableColumn<OrderApproval, String> colCustomerName;

    @FXML
    public TableColumn<OrderApproval, String> colDestination;

    @FXML
    public TableColumn<OrderApproval, String> colDriverName;

    @FXML
    public TableColumn<OrderApproval, Integer> colDuration;

    @FXML
    public TableColumn<OrderApproval, Long> colOrderID;

    @FXML
    public TableColumn<OrderApproval, String> colPickUpPoint;

    @FXML
    public TableColumn<OrderApproval, String> colStatusPayment;

    @FXML
    public Label lblCustomerName;

    @FXML
    public Label lblDestination;

    @FXML
    public Label lblOrderID;

    @FXML
    public Label lblPickUpPoint;

    @FXML
    public Label lblStatusPayment;

    @FXML
    public TableView<OrderApproval> tableOrderApv;

    private IOrderRepository orderRepository;
    private OrderApproval orderApprovalData;
    private Auth globalUser = new Auth();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTableOrderApv();
    }

    public void setTableOrderApv() {
        ControlMessage<List<OrderApproval>> response = this.getAllDataApproval();
        if (response.isSuccess) {
            this.setUpTableOrderApproval(response.data);
        } else ServiceGlobalComponents.showAlertDialog(response);
    }

    public void setUpTableOrderApproval(List<OrderApproval> orderApprovals) {
        ObservableList<OrderApproval> list = FXCollections.observableArrayList(orderApprovals);

        this.colCreatedDate.setCellValueFactory(new PropertyValueFactory<OrderApproval, Date>("orderDate"));
        this.colOrderID.setCellValueFactory(new PropertyValueFactory<OrderApproval, Long>("idHist"));
        this.colCustomerName.setCellValueFactory(new PropertyValueFactory<OrderApproval, String>("customerName"));
        this.colBusName.setCellValueFactory(new PropertyValueFactory<OrderApproval, String>("busName"));
        this.colDriverName.setCellValueFactory(new PropertyValueFactory<OrderApproval, String>("driverName"));
        this.colPickUpPoint.setCellValueFactory(new PropertyValueFactory<OrderApproval, String>("pickUpPoint"));
        this.colDestination.setCellValueFactory(new PropertyValueFactory<OrderApproval, String>("destination"));
        this.colDuration.setCellValueFactory(new PropertyValueFactory<OrderApproval, Integer>("duration"));
        this.colStatusPayment.setCellValueFactory(new PropertyValueFactory<OrderApproval, String>("statusPayment"));

        Callback<TableColumn<OrderApproval, String>, TableCell<OrderApproval, String>> cellAction = (TableColumn<OrderApproval, String> param) -> {
            final TableCell<OrderApproval, String> cell = new TableCell<OrderApproval, String>() {
                @Override
                protected void updateItem(String s, boolean b) {
                    super.updateItem(s, b);
                    if (b) {
                        setGraphic(null);
                        setItem(null);
                    } else {
                        Button approve = new Button("Approve");
                        // approve.setButtonType(ButtonType.RAISED);
                        approve.setStyle("-fx-background-radius: 5px;-fx-background-color:  #7a0ed9");
                        // approve.setRippleColor(Color.web("GREEN"));
                        approve.setCursor(Cursor.HAND);
                        approve.setTextFill(Color.web("WHITE"));
                        approve.setMinSize(70, 30);
                        approve.setMaxSize(70, 30);

                        Button reject = new Button("Reject");
                        // reject.setButtonType(ButtonType.RAISED);
                        reject.setStyle("-fx-background-radius: 5px;-fx-background-color:  #7a0ed9");
                        // reject.setRippleColor(Color.web("RED"));
                        reject.setCursor(Cursor.HAND);
                        reject.setTextFill(Color.web("WHITE"));
                        reject.setMinSize(50, 30);
                        reject.setMaxSize(50, 30);

                        HBox managebtn = new HBox(approve, reject);
                        managebtn.setAlignment(Pos.CENTER_RIGHT);
                        HBox.setMargin(approve, new Insets(2, 3, 0, 5));
                        HBox.setMargin(reject, new Insets(2, 3, 0, 5));

                        setGraphic(managebtn);

                        setText(null);
                    }
                }

                //<editor-fold desc="Anon Method">
                private void onUpdateBus(Bus data) {

                }

                private boolean onDeleteBus(Bus bus) {
                    return true;
                }
                //</editor-fold>
            };
            return cell;
        };
        colAction.setCellFactory(cellAction);
        tableOrderApv.setFixedCellSize(50.0);
        tableOrderApv.setItems(list);
    }

    public ControlMessage<List<OrderApproval>> getAllDataApproval() {
        this.orderRepository = new OrderRepositoryImpl();
        IOrderService orderService = new OrderServiceImpl(this.orderRepository);
        return orderService.getAllOrderPendingApproval();
    }

    public void setAuth(Auth user) {
        this.globalUser = user;
    }
}
