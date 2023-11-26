package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Service.Impl.BusServiceImpl;
import com.java.parawisata.javaparawisata.Service.Impl.CustomerServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUpTableBus(ObservableList<Bus> list) {
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
                            this.onUpdateBus(tableBus.getSelectionModel().getSelectedItem());
                        });

                        FontAwesomeIconView delete = new FontAwesomeIconView();
                        delete.setSize("25");
                        delete.setFill(Color.web("#ff0033"));
                        delete.setGlyphName("TRASH");
                        delete.setCursor(Cursor.HAND);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            this.onDeleteBus(bus);
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
                private void onUpdateBus(Bus data) {
                    FXMLLoader loader = ServiceGlobalComponents.generateFXMLLoader("fxml/bus-dialog-view.fxml");
                    BusDialogController controller = loader.getController();
                    controller.setDataBus(data);
                    ServiceGlobalComponents.showPopUpDialog(loader, "Update Customer".concat("-" + data.getBusName()));
                }

                private boolean onDeleteBus(Bus bus) {
                    boolean response = false;
                    try {
                        BusServiceImpl service = new BusServiceImpl(busRepository);
                        response = service.onDeleteBus(bus);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return response;
                }
                //</editor-fold>
            };
            return cell;
        };
        colAction.setCellFactory(cellAction);
        tableBus.setItems(list);
    }
}
