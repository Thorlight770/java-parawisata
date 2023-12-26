package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.*;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.BusRepositoryImpl;
import com.java.parawisata.javaparawisata.Repository.Impl.ParamRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IBusService;
import com.java.parawisata.javaparawisata.Service.IParamService;
import com.java.parawisata.javaparawisata.Service.Impl.BusServiceImpl;
import com.java.parawisata.javaparawisata.Service.Impl.ParamServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.models.spinner.IntegerSpinnerModel;
import io.github.palexdev.materialfx.controls.models.spinner.SpinnerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private Bus busData = new Bus();
    private BusMaint busMaint = new BusMaint();
    private BusPrice busPrice = new BusPrice();
    private List<BusPrice> busPrices = new ArrayList<>();
    private Auth globalUser = new Auth();
    private String action = "";
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
        this.busPrices = busPrices;
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
                    action = "U";
                    busPrice = data;
                    txtPriceGrid.setText(String.valueOf(data.getPrice()));
                    spinnerDurationGrid.setValue(data.getDuration());
                    cmbBoxDestination.setValue(data.getDestination());
                    txtBusNameGrid.setText(data.getBusName());
                }

                private void onDeleteBus(BusPrice data) {
                    AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Untuk Menghapus ?");
                    if (result.get()) {
                        tableBusPrice.getItems().remove(data);
                        ServiceGlobalComponents.showAlertDialog("Success", MessageType.SUCCESS, List.of("Success Delete Bus Price - " + data.getBusPriceID()));
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
        ControlMessage validateProc = this.onGridActionValidate();
        if (validateProc.isSuccess) {
            this.onSetDisable(false);
            if (action.equals("I")) {
                tableBusPrice.getItems().add(onSetDataGrid());
                ServiceGlobalComponents.showAlertDialog("Success", MessageType.SUCCESS, List.of("Success Insert Bus Price !"));
            } else if (action.equals("U")) {
                int index = tableBusPrice.getItems().indexOf(busPrice);
                tableBusPrice.getItems().set(index, onSetDataGrid());
                ServiceGlobalComponents.showAlertDialog("Success", MessageType.SUCCESS, List.of("Success Update Bus Price !"));
            }
        } else ServiceGlobalComponents.showAlertDialog(validateProc);
    }
    private ControlMessage onGridActionValidate() {
        ControlMessage response = new ControlMessage();
        response.isSuccess = true;
        try {
            if (!txtBusNameGrid.isValid() || txtBusNameGrid.getText().isEmpty() || txtBusNameGrid.getText().isBlank())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Bus Name Tidak Boleh Kosong !"));

            if (!txtPriceGrid.isValid() || txtPriceGrid.getText().isEmpty() || txtPriceGrid.getText().isBlank())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Price Tidak Boleh Kosong !"));
            else if (!txtPriceGrid.getText().matches("[0-9]+"))
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Price Tidak Boleh Mengandung Karakter !"));

            if (spinnerDurationGrid.isSelectable() && spinnerDurationGrid.getValue() <= 0)
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Duration Harus Lebih Besar Dari 0 !"));

            if (!cmbBoxDestination.isValid() || cmbBoxDestination.getValue().isBlank() || cmbBoxDestination.getValue().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Destination Tidak Boleh Kosong !"));

            if (response.getMaxMessageType().getValue() >= MessageType.ERROR.getValue()) response.isSuccess = false;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
    private BusPrice onSetDataGrid() {
        if (action.equals("I")) {
            BusPrice response = new BusPrice();
            response.setBusPriceID("-1");
            response.setBusName(txtBusNameGrid.getText());
            response.setDestination(cmbBoxDestination.getValue());
            response.setDuration(spinnerDurationGrid.getValue());
            response.setPrice(Double.valueOf(txtPriceGrid.getText()));
            response.setAction(this.action);
            response.setUserID(this.globalUser.getUserID());
            return response;
        } else {
            busPrice.setDestination(cmbBoxDestination.getValue());
            busPrice.setDuration(spinnerDurationGrid.getValue());
            busPrice.setPrice(Double.valueOf(txtPriceGrid.getText()));
            busPrice.setAction(this.action);
            busPrice.setUserID(this.globalUser.getUserID());
            return busPrice;
        }
    }
    @FXML
    public void onBtnAddGrid(ActionEvent event){
        this.action = "I";
        this.onSetDisable(true);
    }

    @FXML
    public void onBtnSaveProcess(ActionEvent event) {
        this.onSetDataMaint();
        System.out.println(this.busMaint);

        AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Ingin Simpan ?");
        if (result.get()) {
            ControlMessage<BusMaint> response = onUpdateBusProcess(this.busMaint);
            if (response.isSuccess) {
                this.parentController.onSetTable();
                ((Stage)(((Button) event.getSource()).getScene().getWindow())).hide();
            }
            ServiceGlobalComponents.showAlertDialog(response);
        }
    }

    public ControlMessage<BusMaint> onUpdateBusProcess(BusMaint data) {
        this.busRepository = new BusRepositoryImpl();
        IBusService busService = new BusServiceImpl(busRepository);
        return busService.onUpdateBus(data);
    }

    private void onSetDataMaint() {
        this.busMaint.setBusID(busData.getBusID());
        this.busMaint.setBusName(busData.getBusName());
        // <editor-folds desc="build fasilitas">
        StringBuilder sb = new StringBuilder();
        String fasilitas = "";
        if (cBoxAC.isSelected())
            sb.append("AC|");
        if (cBoxMicrophone.isSelected())
            sb.append("MICROPHONE|");
        if (cBoxLuggage.isSelected())
            sb.append("LUGGAGE|");
        if (cBoxEat.isSelected())
            sb.append("EAT|");
        if (cBoxWifi.isSelected())
            sb.append("WIFI|");
        if (cBoxCharge.isSelected())
            sb.append("CHARGE|");
        if (!sb.isEmpty() && sb.toString().endsWith("|"))
            fasilitas = sb.substring(0, sb.length()-1);
        // </editor-folds>
        this.busMaint.setFasilitas(fasilitas);
        this.busMaint.setUserID(globalUser.getUserID());
        // <editor-folds desc="build list price">
        this.tableBusPrice.getItems().forEach(x -> {
            if (x.getBusPriceID().equals("-1")) {
                this.busPrices.add(x);
            } else {
                for (BusPrice data: busPrices) {
                    if (x.getBusPriceID().equals(data.getBusPriceID())) {
                        x.setAction(data.getAction());
                        break;
                    }
                }
            }
        });
        this.busPrices.forEach(x -> {
            if (this.tableBusPrice.getItems().stream().noneMatch(y -> y.getBusPriceID().equals(x.getBusPriceID()))) {
                x.setAction("D");
            }
        });

        this.busPrices.forEach(x -> {
            ModelMapper map = new ModelMapper();
            BusPriceMaint busPriceMaint = map.map(x, BusPriceMaint.class);
            this.busMaint.getBusPrices().add(busPriceMaint);
        });
        // </editor-folds>
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
