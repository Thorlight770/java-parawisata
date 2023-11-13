package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.HistoryOrder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class HistoryOrderController implements Initializable {
    @FXML
    public TableColumn<HistoryOrder, String> colAction;

    @FXML
    public TableColumn<HistoryOrder, String> colBusID;

    @FXML
    public TableColumn<HistoryOrder, Date> colDateOrder;

    @FXML
    public TableColumn<HistoryOrder, String> colDriverID;

    @FXML
    public TableColumn<HistoryOrder, Long> colID;

    @FXML
    public TableColumn<HistoryOrder, String> colOrderID;

    @FXML
    public TableView<HistoryOrder> tableHistoryOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // this.setUpTableHistoryOrder();
    }

    private void setUpTableHistoryOrder() {
        colID.setCellValueFactory(new PropertyValueFactory<HistoryOrder, Long>("idHist"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("orderID"));
        colBusID.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("busID"));
        colDriverID.setCellValueFactory(new PropertyValueFactory<HistoryOrder, String>("driverID"));
        colDateOrder.setCellValueFactory(new PropertyValueFactory<HistoryOrder, Date>("createdDate"));
        Callback<TableColumn<HistoryOrder, String>, TableCell<HistoryOrder, String>> cellAction = (TableColumn<HistoryOrder, String> param) -> {
          final TableCell<HistoryOrder, String> cell = new TableCell<HistoryOrder, String>() {
              @Override
              protected void updateItem(String s, boolean b) {
                  super.updateItem(s, b);
                  if (b) {
                      setGraphic(null);
                      setItem(null);
                  } else {
                      FontAwesomeIconView edit = new FontAwesomeIconView();
                      edit.setSize("15");
                      edit.setFill(Color.web("Black"));
                      edit.setGlyphName("EDIT");
                      edit.setOnMouseClicked((MouseEvent event) -> {
                          // nanti set stage view dialog edit
                      });

                      HBox managebtn = new HBox(edit);
                      managebtn.setStyle("-fx-alignment:center");
                      HBox.setMargin(edit, new Insets(2, 3, 0, 5));

                      setGraphic(managebtn);

                      setText(null);
                  }
              }
          };
            return cell;
        };
        colAction.setCellFactory(cellAction);
    }

    private void updateHistoryOrder() {
        System.out.println("Masuk Method");
    }
}
