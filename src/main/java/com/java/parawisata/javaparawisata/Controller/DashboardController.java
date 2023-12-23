package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Dashboard;
import com.java.parawisata.javaparawisata.Entity.Schedule;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.OrderRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IOrderService;
import com.java.parawisata.javaparawisata.Service.Impl.OrderServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public PieChart diagReview;

    @FXML
    public TableColumn<Schedule, Date> colDateFrom;

    @FXML
    public TableColumn<Schedule, Date> colDateTo;

    @FXML
    public TableColumn<Schedule, String> colDestination;

    @FXML
    public TableColumn<Schedule, String> colPickUpPoint;

    @FXML
    public TableView<Schedule> tableSchedule;

    @FXML
    public Label lblTotalPendingTrip;

    @FXML
    public Label lblTotalTrip;

    @FXML
    public MFXProgressSpinner progressTotalTrip;

    @FXML
    public MFXProgressSpinner ProgressTotalPendingTrip;

    private IOrderRepository orderRepository;
    private Auth globalUser = new Auth();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setUpTableSchedule(List<Schedule> schedule) {
        ObservableList<Schedule> list =FXCollections.observableArrayList(schedule);
        colDateFrom.setCellValueFactory(new PropertyValueFactory<Schedule, Date>("dateFrom"));
        colDateTo.setCellValueFactory(new PropertyValueFactory<Schedule, Date>("dateTo"));
        colPickUpPoint.setCellValueFactory(new PropertyValueFactory<Schedule, String>("pickUpPoint"));
        colDestination.setCellValueFactory(new PropertyValueFactory<Schedule, String>("destinationTour"));
        tableSchedule.setItems(list);
    }

    public void onSetDashboard() {
        ControlMessage<Dashboard> response = getCompleteDashboardContent(this.globalUser.getUserID());
        if (response.isSuccess && !response.data.getSchedules().isEmpty()) {
            this.setUpTableSchedule(response.data.getSchedules());
            this.lblTotalTrip.setText(String.valueOf(response.data.getTotalTrip()));
            this.lblTotalPendingTrip.setText(String.valueOf(response.data.getTotalPendingTrip()));
            this.ProgressTotalPendingTrip.setProgress(response.data.getPercentPendingTrip());
            this.progressTotalTrip.setProgress(response.data.getPercentTrip());
            ObservableList<PieChart.Data> datas = FXCollections.observableArrayList(
                    new PieChart.Data("Total Trip Done", response.data.getTotalTrip()),
                    new PieChart.Data("Total OnSchedule Trip", response.data.getTotalPendingTrip())
            );
            this.diagReview.setData(datas);
        } else if (!response.isSuccess) ServiceGlobalComponents.showAlertDialog(response);
    }
    private ControlMessage<Dashboard> getCompleteDashboardContent(String userID) {
        this.orderRepository = new OrderRepositoryImpl();
        IOrderService orderService = new OrderServiceImpl(this.orderRepository);
        return orderService.getAllDataDashboard(userID);
    }

    public void onSetAuth(Auth user) {
        this.globalUser = user;
    }
}
