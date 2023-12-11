package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Schedule;
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
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public PieChart diagReview;

    @FXML
    public TableColumn<Schedule, Date> colBookedDate;

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

    private Auth globalUser = new Auth();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> datas = FXCollections.observableArrayList(
                new PieChart.Data("*", 100),
                new PieChart.Data("**", 100),
                new PieChart.Data("***", 100),
                new PieChart.Data("****", 100),
                new PieChart.Data("*****", 100)
        );
        this.diagReview.setData(datas);

        this.setUpTableSchedule();
    }

    private void setUpTableSchedule() {
        colBookedDate.setCellValueFactory(new PropertyValueFactory<Schedule, Date>("scheduleDate"));
        colPickUpPoint.setCellValueFactory(new PropertyValueFactory<Schedule, String>("pickUpPoint"));
        colDestination.setCellValueFactory(new PropertyValueFactory<Schedule, String>("destinationTour"));
    }

    public void onSetAuth(Auth user) {
        this.globalUser = user;
    }
}
