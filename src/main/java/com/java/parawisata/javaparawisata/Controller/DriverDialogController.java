package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Entity.Driver;
import com.java.parawisata.javaparawisata.Repository.ICustomerRepository;
import com.java.parawisata.javaparawisata.Repository.IDriverRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.DriverRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IDriverService;
import com.java.parawisata.javaparawisata.Service.Impl.CustomerServiceImpl;
import com.java.parawisata.javaparawisata.Service.Impl.DriverServiceImpl;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DriverDialogController implements Initializable {
    @FXML
    public Label labelCustomerID;

    @FXML
    public Label labelCustomerName;
    private IDriverRepository driverRepository;
    private Driver _driver;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public ControlMessage<Driver> onBtnProcessUpdate() {
        this.driverRepository = new DriverRepositoryImpl();
        IDriverService driverService = new DriverServiceImpl(this.driverRepository);
        return driverService.updateDriver(_driver);
    }

    public void setDataDriver(Driver data) {
        this._driver = data;
    }
}
