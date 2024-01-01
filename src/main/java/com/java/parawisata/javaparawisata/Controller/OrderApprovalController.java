package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.OrderApproval;
import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.OrderRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IOrderService;
import com.java.parawisata.javaparawisata.Service.Impl.OrderServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Helper.Helper;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @FXML
    public ImageView imgView;

    @FXML
    public MenuItem downloadImage;

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

        if (!list.isEmpty()) {
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
                            approve.setStyle("-fx-background-radius: 5px;-fx-background-color:  #7a0ed9");
                            approve.setCursor(Cursor.HAND);
                            approve.setTextFill(Color.web("WHITE"));
                            approve.setMinSize(70, 30);
                            approve.setMaxSize(70, 30);
                            approve.setOnAction(e -> {
                                onApproveOrder(tableOrderApv.getSelectionModel().getSelectedItem());
                            });

                            Button reject = new Button("Reject");
                            reject.setStyle("-fx-background-radius: 5px;-fx-background-color:  #7a0ed9");
                            reject.setCursor(Cursor.HAND);
                            reject.setTextFill(Color.web("WHITE"));
                            reject.setMinSize(50, 30);
                            reject.setMaxSize(50, 30);
                            reject.setOnAction(e -> {
                                onRejectOrder(tableOrderApv.getSelectionModel().getSelectedItem());
                            });

                            HBox managebtn = new HBox(approve, reject);
                            managebtn.setAlignment(Pos.CENTER_RIGHT);
                            HBox.setMargin(approve, new Insets(2, 3, 0, 5));
                            HBox.setMargin(reject, new Insets(2, 3, 0, 5));

                            setGraphic(managebtn);

                            setText(null);
                        }
                    }

                    //<editor-fold desc="Anon Method">
                    private void onApproveOrder(OrderApproval data) {
                        AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Ingin Approve Order ?");
                        if (result.get()) {
                            data.setAdministratorID(globalUser.getUserID());
                            orderRepository = new OrderRepositoryImpl();
                            IOrderService orderService = new OrderServiceImpl(orderRepository);
                            ControlMessage<OrderApproval> response = orderService.processOrderApproval(data, "A");
                            ServiceGlobalComponents.showAlertDialog(response);
                            if (response.isSuccess) {
                                onResetDetails();
                                setTableOrderApv();
                            }
                        }
                    }

                    private void onRejectOrder(OrderApproval data) {
                        AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Ingin Reject Order ?");
                        if (result.get()) {
                            data.setAdministratorID(globalUser.getUserID());
                            data.setReason("AUTO REJECT SYSTEM !");
                            orderRepository = new OrderRepositoryImpl();
                            IOrderService orderService = new OrderServiceImpl(orderRepository);
                            ControlMessage<OrderApproval> response = orderService.processOrderApproval(data, "R");
                            ServiceGlobalComponents.showAlertDialog(response);
                            if (response.isSuccess) {
                                onResetDetails();
                                setTableOrderApv();
                            }
                        }
                    }
                    //</editor-fold>
                };
                return cell;
            };
            colAction.setCellFactory(cellAction);
        }

        tableOrderApv.setFixedCellSize(50.0);
        tableOrderApv.setItems(list);
    }

    public ControlMessage<List<OrderApproval>> getAllDataApproval() {
        this.orderRepository = new OrderRepositoryImpl();
        IOrderService orderService = new OrderServiceImpl(this.orderRepository);
        return orderService.getAllOrderPendingApproval();
    }

    @FXML
    public void onMouseClickTable(MouseEvent event) {
        this.orderApprovalData = this.tableOrderApv.getSelectionModel().getSelectedItem();
        this.lblCustomerName.setText(this.orderApprovalData.getCustomerName());
        this.lblOrderID.setText(this.orderApprovalData.getIdHist().toString());
        this.lblPickUpPoint.setText(this.orderApprovalData.getPickUpPoint());
        this.lblDestination.setText(this.orderApprovalData.getDestination());
        this.lblStatusPayment.setText(this.orderApprovalData.getStatusPayment());
        Path path = Paths.get("fileTransfer/".concat(this.orderApprovalData.getFileName())).toAbsolutePath();
        Image file = new Image(String.valueOf(path));
        if (!file.isError()) {
            this.imgView.setImage(file);
            this.downloadImage.setOnAction(e -> {
                try {
                    this.onSaveAsImage(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    public void setAuth(Auth user) {
        this.globalUser = user;
    }

    public void onSaveAsImage(Image image) throws IOException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image File","*.png");
        fc.getExtensionFilters().add(extFilter);

        Stage stage = new Stage();
        File file = fc.showSaveDialog(stage);

        if (file != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }

        ServiceGlobalComponents.showAlertDialog("Success", MessageType.SUCCESS, List.of("Success Download Image - " + file.getName()));
    }

    @FXML
    public void onBtnPrintOrderApprove(ActionEvent event) throws JRException, URISyntaxException {
        Helper.printReport("order-approve-report.jrxml");
    }

    @FXML
    public void onBtnPrintOrderOnSchedule(ActionEvent event) throws JRException, URISyntaxException {
        Helper.printReport("order-onschedule-report.jrxml");
    }

    @FXML
    public void onBtnPrintOrderReject(ActionEvent event) throws JRException, URISyntaxException {
        Helper.printReport("order-reject-report.jrxml");
    }

    public void onResetDetails() {
        this.lblDestination.setText("");
        this.lblStatusPayment.setText("");
        this.lblOrderID.setText("");
        this.lblCustomerName.setText("");
        this.lblPickUpPoint.setText("");
        this.imgView.setImage(null);
    }
}
