package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Driver;
import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Repository.IDriverRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.DriverRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IDriverService;
import com.java.parawisata.javaparawisata.Service.Impl.DriverServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.Helper.Helper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class DriverMaintController implements Initializable {
    @FXML
    public MFXButton btnAdd;

    @FXML
    public MFXButton btnPrint;

    @FXML
    public TableColumn<Driver, String> colAction;

    @FXML
    public TableColumn<Driver, Date> colCreatedDate;

    @FXML
    public TableColumn<Driver, String> colDriverID;

    @FXML
    public TableColumn<Driver, String> colDriverName;

    @FXML
    public TableColumn<Driver, Date> colUpdateDate;

    @FXML
    public TableColumn<Driver, String> colUserID;

    @FXML
    public TableView<Driver> tableDriver;

    private IDriverRepository driverRepository;

    private Driver driverData = new Driver();
    private Auth globalUser = new Auth();
    private DriverMaintController driverMaintController = this;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onGetAllDataDriver();
    }

    private void setUpTableDriver(List<Driver> drivers) {
        ObservableList<Driver> list = FXCollections.observableArrayList(drivers);
        this.colDriverID.setCellValueFactory(new PropertyValueFactory<Driver, String>("driverID"));
        this.colDriverName.setCellValueFactory(new PropertyValueFactory<Driver, String>("driverName"));
        this.colCreatedDate.setCellValueFactory(new PropertyValueFactory<Driver, Date>("createdDate"));
        this.colUpdateDate.setCellValueFactory(new PropertyValueFactory<Driver, Date>("updateDate"));
        this.colUserID.setCellValueFactory(new PropertyValueFactory<Driver, String>("userID"));
        Callback<TableColumn<Driver, String>, TableCell<Driver, String>> cellAction = (TableColumn<Driver, String> param) -> {
            final TableCell<Driver, String> cell = new TableCell<Driver, String>() {
                @Override
                protected void updateItem(String s, boolean b) {
                    super.updateItem(s, b);
                    if (b) {
                        setGraphic(null);
                        setItem(null);
                    } else {
                        FontAwesomeIconView edit = new FontAwesomeIconView();
                        edit.setSize("25");
                        edit.setFill(Color.web("green"));
                        edit.setGlyphName("EDIT");
                        edit.setCursor(Cursor.HAND);
                        edit.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                this.onUpdateDriver(tableDriver.getSelectionModel().getSelectedItem());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        FontAwesomeIconView delete = new FontAwesomeIconView();
                        delete.setSize("25");
                        delete.setFill(Color.web("#ff0033"));
                        delete.setGlyphName("TRASH");
                        delete.setCursor(Cursor.HAND);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            this.onDeleteDriver(tableDriver.getSelectionModel().getSelectedItem());
                            onGetAllDataDriver();
                        });

                        HBox managebtn = new HBox(edit, delete);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(edit, new Insets(2, 3, 0, 5));
                        HBox.setMargin(delete, new Insets(2, 3, 0, 5));

                        setGraphic(managebtn);

                        setText(null);
                    }
                }

                //<editor-fold desc="Anon Method">
                private void onUpdateDriver(Driver data) throws IOException {
                    FXMLLoader loader = ServiceGlobalComponents.generateFXMLLoader("fxml/driver-dialog-view.fxml");
                    ServiceGlobalComponents.showPopUpDialog(loader, "Update Driver".concat("-" + data.getDriverName()));
                    DriverDialogController controller = loader.getController();
                    controller.setAuth(globalUser);
                    controller.setParentController(driverMaintController);
                    controller.setDataDriver(data);
                }

                private void onDeleteDriver(Driver data) {
                    data.setUserID(globalUser.getUserID());
                    AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Ingin Hapus ?");
                    if (result.get()) {
                        IDriverRepository repository = new DriverRepositoryImpl();
                        IDriverService serviceDriver = new DriverServiceImpl(repository);
                        ControlMessage<Driver> responseDelete = serviceDriver.deleteDriver(data);
                        ServiceGlobalComponents.showAlertDialog(responseDelete);
                    }
                }
                //</editor-fold>
            };
            return cell;
        };
        colAction.setCellFactory(cellAction);
        tableDriver.setItems(list);
    }

    public void onGetAllDataDriver() {
        this.driverRepository = new DriverRepositoryImpl();
        IDriverService driverService = new DriverServiceImpl(this.driverRepository);
        ControlMessage<List<Driver>> drivers = driverService.getDrivers();
        if (drivers.isSuccess && !drivers.data.isEmpty()) {
            this.setUpTableDriver(drivers.data);
        } else ServiceGlobalComponents.showAlertDialog(drivers);
    }

    @FXML
    public void onBtnAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = ServiceGlobalComponents.generateFXMLLoader("fxml/driver-dialog-view.fxml");
        ServiceGlobalComponents.showPopUpDialog(loader, "Create Driver");
        DriverDialogController controller = loader.getController();
        controller.setParentController(this);
        controller.setAuth(globalUser);
    }

    @FXML
    public void onBtnPrintProcess(ActionEvent event) throws JRException {
        Helper.printReport("driver-maint-report.jrxml");
    }

    public void setAuth(Auth user) {
        this.globalUser = user;
    }
}
