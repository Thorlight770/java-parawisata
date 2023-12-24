package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.BusRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IBusService;
import com.java.parawisata.javaparawisata.Service.Impl.BusServiceImpl;
import com.java.parawisata.javaparawisata.Service.Impl.CustomerServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.fxml.FXML;
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

public class BusMaintController implements Initializable {
    @FXML
    public TableColumn<Bus, String> colAction;

    @FXML
    public TableColumn<Bus, String> colBusID;

    @FXML
    public TableColumn<Bus, String> colBusName;

    @FXML
    public TableColumn<Bus, Date> colCreatedDate;

    @FXML
    public TableColumn<Bus, String> colFasilitas;

    @FXML
    public TableColumn<Bus, String> colUserID;

    @FXML
    public TableView<Bus> tableBus;

    private Bus bus;
    private IBusRepository busRepository;
    private Auth globalUser = new Auth();
    private BusMaintController busMaintController = this;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onSetTable();
    }

    public void onSetTable() {
        ControlMessage<List<Bus>> buss = this.getAllBus();
        if (buss.isSuccess) this.setUpTableBus(buss.data);
        else ServiceGlobalComponents.showAlertDialog(buss);
    }
    public void setUpTableBus(List<Bus> buss) {
        ObservableList<Bus> list = FXCollections.observableArrayList(buss);

        this.colBusID.setCellValueFactory(new PropertyValueFactory<Bus, String>("busID"));
        this.colBusName.setCellValueFactory(new PropertyValueFactory<Bus, String>("busName"));
        this.colFasilitas.setCellValueFactory(new PropertyValueFactory<Bus, String>("fasilitas"));
        this.colCreatedDate.setCellValueFactory(new PropertyValueFactory<Bus, Date>("createdDate"));
        this.colUserID.setCellValueFactory(new PropertyValueFactory<Bus, String>("userID"));

        Callback<TableColumn<Bus, String>, TableCell<Bus, String>> cellAction = (TableColumn<Bus, String> param) -> {
            final TableCell<Bus, String> cell = new TableCell<Bus, String>() {
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
                                this.onUpdateBus(tableBus.getSelectionModel().getSelectedItem());
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
                            this.onDeleteBus(tableBus.getSelectionModel().getSelectedItem());
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
                private void onUpdateBus(Bus data) throws IOException {
                    FXMLLoader loader = ServiceGlobalComponents.generateFXMLLoader("fxml/bus-dialog-view.fxml");
                    ServiceGlobalComponents.showPopUpDialog(loader, "Update Bus".concat("-" + data.getBusName()));
                    BusDialogController controller = loader.getController();
                    controller.accordionMenus.setDisable(true);
                    controller.menuBtnProcess.setVisible(true);
                    controller.setParentController(busMaintController);
                    controller.setDataBus(data);
                    controller.setAuth(globalUser);
                    controller.onSetTable();
                }

                private void onDeleteBus(Bus bus) {
                    bus.setUserID(globalUser.getUserID());
                    AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Ingin Hapus ?");
                    if (result.get()) {
                        busRepository = new BusRepositoryImpl();
                        IBusService busService = new BusServiceImpl(busRepository);
                        ControlMessage<Bus> response = busService.onDeleteBus(bus);
                        if (response.isSuccess) {
                            onSetTable();
                        }
                        ServiceGlobalComponents.showAlertDialog(response);
                    }
                }
                //</editor-fold>
            };
            return cell;
        };
        colAction.setCellFactory(cellAction);
        tableBus.setItems(list);
    }

    public ControlMessage<List<Bus>> getAllBus() {
        this.busRepository = new BusRepositoryImpl();
        IBusService busService = new BusServiceImpl(this.busRepository);
        return busService.getAllBus();
    }

    public void setAuth(Auth auth) {
        this.globalUser = auth;
    }
}
