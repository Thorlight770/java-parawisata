package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Driver;
import com.java.parawisata.javaparawisata.Repository.IDriverRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.DriverRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IDriverService;
import com.java.parawisata.javaparawisata.Service.Impl.DriverServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class DriverDialogController implements Initializable {
    @FXML
    public MFXTextField txtDriverID;

    @FXML
    public MFXTextField txtDriverName;

    private IDriverRepository driverRepository;
    private Driver _driver = new Driver();
    private Auth globalUser = new Auth();
    private DriverMaintController driverMaintController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onCancelAction(ActionEvent event) {
        ((Stage)(((Button) event.getSource()).getScene().getWindow())).hide();
    }

    @FXML
    public void onSaveAction(ActionEvent event) {
        this.onSetData();

        driverRepository = new DriverRepositoryImpl();
        IDriverService driverService = new DriverServiceImpl(driverRepository);

        AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Untuk Simpan ?");
        if (result.get()) {
            if (!this.txtDriverID.getText().isEmpty() || !this.txtDriverID.getText().isBlank()) {
                ControlMessage<Driver> response = driverService.updateDriver(_driver);
                if (response.isSuccess) {
                    ((Stage)(((Button) event.getSource()).getScene().getWindow())).hide();
                    this.driverMaintController.onGetAllDataDriver();
                }
                ServiceGlobalComponents.showAlertDialog(response);
            } else {
                ControlMessage<Driver> response = driverService.addDriver(_driver);
                if (response.isSuccess) {
                    ((Stage)(((Button) event.getSource()).getScene().getWindow())).hide();
                    this.driverMaintController.onGetAllDataDriver();
                }
                ServiceGlobalComponents.showAlertDialog(response);
            }
        }
    }

    private void onSetData() {
        this._driver.setUserID(this.globalUser.getUserID());

        if (txtDriverName.getText().isBlank() || txtDriverName.getText().isEmpty())
            this._driver.setDriverName("");
        else this._driver.setDriverName(txtDriverName.getText());
    }

    public void setDataDriver(Driver data) {
        this._driver = data;
        this.txtDriverID.setText(_driver.getDriverID());
        this.txtDriverName.setText(_driver.getDriverName());
    }

    public void setAuth(Auth user) {
        this.globalUser = user;
    }

    public void setParentController(DriverMaintController parent) {
        this.driverMaintController = parent;
    }
}
