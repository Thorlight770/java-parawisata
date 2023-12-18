package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.BusPrice;
import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.BusRepositoryImpl;
import com.java.parawisata.javaparawisata.Repository.Impl.ParamRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IBusService;
import com.java.parawisata.javaparawisata.Service.IParamService;
import com.java.parawisata.javaparawisata.Service.Impl.BusServiceImpl;
import com.java.parawisata.javaparawisata.Service.Impl.ParamServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.models.spinner.IntegerSpinnerModel;
import io.github.palexdev.materialfx.controls.models.spinner.SpinnerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class BusDialogController implements Initializable {
    @FXML
    public Accordion accordionMenus;

    @FXML
    public MFXCheckbox cBoxAC;

    @FXML
    public MFXCheckbox cBoxCharge;

    @FXML
    public MFXCheckbox cBoxEat;

    @FXML
    public MFXCheckbox cBoxLuggage;

    @FXML
    public MFXCheckbox cBoxMicrophone;

    @FXML
    public MFXCheckbox cBoxWifi;

    @FXML
    public MFXComboBox<String> cmbBoxDestination;

    @FXML
    public TableView<BusPrice> tableBusPrice;

    @FXML
    public TableColumn<BusPrice, Date> colCreatedDate;

    @FXML
    public TableColumn<BusPrice, String> colDestination;

    @FXML
    public TableColumn<BusPrice, Integer> colDuration;

    @FXML
    public TableColumn<BusPrice, Long> colID;

    @FXML
    public TableColumn<BusPrice, Double> colPrice;

    @FXML
    public TableColumn<BusPrice, Date> colUpdateDate;

    @FXML
    public TableColumn<BusPrice, String> colUserID;

    @FXML
    public TableColumn<BusPrice, String> colAction;

    @FXML
    public MFXSpinner<Integer> spinnerDurationGrid;

    @FXML
    public MFXTextField txtBusName;

    @FXML
    public MFXTextField txtBusNameGrid;

    @FXML
    public MFXTextField txtPriceGrid;

    @FXML
    public HBox menuBtnProcess;


    @FXML
    public MFXButton btnAddGrid;

    private BusMaintController parentController;
    private IBusRepository busRepository;
    private IParamRepository paramRepository;
    private Bus busData;
    private Auth globalUser = new Auth();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onSetAllParam();
    }

    public void onSetTable() {
        ControlMessage<List<BusPrice>> busPrices = this.getAllDataBusPriceByBusTypeID(this.busData.getBusID());
        if (busPrices.isSuccess) this.onSetUpTableBusPrice(busPrices.data);
        else ServiceGlobalComponents.showAlertDialog(busPrices);
    }

    public void onSetUpTableBusPrice(List<BusPrice> busPrices) {
        ObservableList<BusPrice> list = FXCollections.observableArrayList(busPrices);
        this.colID.setCellValueFactory(new PropertyValueFactory<BusPrice, Long>("id"));
        this.colPrice.setCellValueFactory(new PropertyValueFactory<BusPrice, Double>("price"));
        this.colDestination.setCellValueFactory(new PropertyValueFactory<BusPrice, String>("destination"));
        this.colDuration.setCellValueFactory(new PropertyValueFactory<BusPrice, Integer>("duration"));
        this.colCreatedDate.setCellValueFactory(new PropertyValueFactory<BusPrice, Date>("createdDate"));
        this.colUpdateDate.setCellValueFactory(new PropertyValueFactory<BusPrice, Date>("updateDate"));
        this.colUserID.setCellValueFactory(new PropertyValueFactory<BusPrice, String>("userID"));
        Callback<TableColumn<BusPrice, String>, TableCell<BusPrice, String>> cellAction = (TableColumn<BusPrice, String> param) -> {
            final TableCell<BusPrice, String> cell = new TableCell<BusPrice, String>() {
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
                            this.onUpdateBus(tableBusPrice.getSelectionModel().getSelectedItem());
                        });

                        FontAwesomeIconView delete = new FontAwesomeIconView();
                        delete.setSize("25");
                        delete.setFill(Color.web("#ff0033"));
                        delete.setGlyphName("TRASH");
                        delete.setCursor(Cursor.HAND);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            this.onDeleteBus(tableBusPrice.getSelectionModel().getSelectedItem());
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
                private void onUpdateBus(BusPrice data) {
                    onSetDisable(true);
                    txtPriceGrid.setText(String.valueOf(data.getPrice()));
                    spinnerDurationGrid.setValue(data.getDuration());
                }

                private void onDeleteBus(BusPrice data) {
                    AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Untuk Menghapus ?");
                    if (result.get()) {
                        busRepository = new BusRepositoryImpl();
                        IBusService busService = new BusServiceImpl(busRepository);
                        ControlMessage<BusPrice> response = busService.deleteBusPrice(data);
                        ServiceGlobalComponents.showAlertDialog(response);
                    }
                }
                //</editor-fold>
            };
            return cell;
        };
        this.colAction.setCellFactory(cellAction);
        this.tableBusPrice.setItems(list);
    }
    public ControlMessage<List<BusPrice>> getAllDataBusPriceByBusTypeID(String busTypeID) {
        this.busRepository = new BusRepositoryImpl();
        IBusService service = new BusServiceImpl(this.busRepository);
        return service.getBusDetailsPriceByID(busTypeID);
    }

    public void onSetAllParam() {
        ControlMessage<List<GlobalParameter>> paramDestination = this.getDestinationParam();
        if (!paramDestination.isSuccess) ServiceGlobalComponents.showAlertDialog(paramDestination);
        if (!paramDestination.data.isEmpty()) {
            List<String> destinations = new ArrayList<>();
            paramDestination.data.forEach(x -> {
                destinations.add(x.getValue());
            });
            this.cmbBoxDestination.setItems(FXCollections.observableArrayList(destinations));
        }

        SpinnerModel<Integer> model = new IntegerSpinnerModel();
        model.setValue(10);

        this.spinnerDurationGrid.setSpinnerModel(model);
        // this.spinnerDurationGrid.setGraphicTextGap(-100);
    }
    public ControlMessage<List<GlobalParameter>> getDestinationParam(){
        this.paramRepository = new ParamRepositoryImpl();
        IParamService paramService = new ParamServiceImpl(this.paramRepository);
        GlobalParameter request = new GlobalParameter();
        request.setGroup("DestinationOrder");
        return paramService.InquiryGlobalParam(request);
    }

    @FXML
    public void onBtnCancelGrid(ActionEvent event) {
        this.onSetDisable(false);
    }

    @FXML
    public void onBtnSaveGrid(ActionEvent event) {
        this.onSetDisable(false);
    }

    @FXML
    public void onBtnAddGrid(ActionEvent event){
        this.onSetDisable(true);
    }

    @FXML
    public void onBtnSaveProcess(ActionEvent event) {

    }

    @FXML
    public void onBtnCancelProcess(ActionEvent event) {
        ((Stage)(((Button) event.getSource()).getScene().getWindow())).hide();
    }

    public void onSetDisable(boolean disabled) {
        this.tableBusPrice.setDisable(disabled);
        this.txtBusName.setDisable(disabled);
        this.cBoxCharge.setDisable(disabled);
        this.cBoxLuggage.setDisable(disabled);
        this.cBoxWifi.setDisable(disabled);
        this.cBoxEat.setDisable(disabled);
        this.cBoxAC.setDisable(disabled);
        this.cBoxMicrophone.setDisable(disabled);
        this.btnAddGrid.setDisable(disabled);
        this.accordionMenus.setDisable(!disabled);
        this.accordionMenus.getPanes().get(0).setExpanded(disabled);
        this.menuBtnProcess.setVisible(!disabled);
    }

    public void setDataBus(Bus data) {
        this.busData = data;
        this.txtBusName.setText(data.getBusName());
        String[] facilities = data.getFasilitas().split("\\|");
        for (String item : facilities) {
            switch (item) {
                case "AC" -> this.cBoxAC.setSelected(true);
                case "MICROPHONE" -> this.cBoxMicrophone.setSelected(true);
                case "LUGGAGE" -> this.cBoxLuggage.setSelected(true);
                case "EAT" -> this.cBoxEat.setSelected(true);
                case "WIFI" -> this.cBoxWifi.setSelected(true);
                case "CHARGE" -> this.cBoxCharge.setSelected(true);
            }
        }
        this.txtBusNameGrid.setText(data.getBusName());
    }

    public void setAuth(Auth user) {
        this.globalUser = user;
    }

    public void setParentController(BusMaintController parent) {
        this.parentController = parent;
    }
}
