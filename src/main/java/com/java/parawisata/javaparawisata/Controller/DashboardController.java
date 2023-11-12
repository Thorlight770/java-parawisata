package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Schedule;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import io.github.palexdev.materialfx.controls.MFXTableView;

public class DashboardController implements Initializable {
    @FXML
    public PieChart diagReview;

    @FXML
    public MFXTableView<Schedule> tableNextSchedule;

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
        MFXTableColumn<Schedule> dateColumn =new MFXTableColumn<>("Date", true);
        MFXTableColumn<Schedule> pickUpPointColumn =new MFXTableColumn<>("Pick Up Point", true);
        MFXTableColumn<Schedule> destinationColumn =new MFXTableColumn<>("Destination Point", true);

        dateColumn.setRowCellFactory(schedule -> new MFXTableRowCell<>(Schedule::getScheduleDate));
        pickUpPointColumn.setRowCellFactory(schedule -> new MFXTableRowCell<>(Schedule::getPickUpPoint));
        destinationColumn.setRowCellFactory(schedule -> new MFXTableRowCell<>(Schedule::getDestinationTour));

        this.tableNextSchedule.getTableColumns().addAll(dateColumn, pickUpPointColumn, destinationColumn);
    }
}
