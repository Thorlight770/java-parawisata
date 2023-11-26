package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Repository.ICustomerRepository;
import com.java.parawisata.javaparawisata.Service.Impl.CustomerServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.lib.common.GenerateFilePDF;
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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerMaintController implements Initializable {
    @FXML
    public MFXButton btnAdd;

    @FXML
    public MFXButton btnPrint;

    @FXML
    public TableColumn<Customer, String> colAction;

    @FXML
    public TableColumn<Customer, String> colAddress;

    @FXML
    public TableColumn<Customer, String> colCustomerID;

    @FXML
    public TableColumn<Customer, String> colCustomerName;

    @FXML
    public TableColumn<Customer, String> colEmail;

    @FXML
    public TableColumn<Customer, String> colGender;

    @FXML
    public TableColumn<Customer, String> colIdentityType;

    @FXML
    public TableColumn<Customer, String> colIndetityID;

    @FXML
    public TableColumn<Customer, Integer> colStatusFDS;

    @FXML
    public TableView<Customer> tableCustomer;

    private Customer customer;
    private ICustomerRepository customerRepository;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setUpTableCustomer(FXCollections.observableArrayList(
                new Customer("1","dummy1","dummy","dummy","dummy","dummy","dummy","dummy",1, Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now())),
                new Customer("2","dummy2","dummy","dummy","dummy","dummy","dummy","dummy",1, Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now())),
                new Customer("3","dummy3","dummy","dummy","dummy","dummy","dummy","dummy",1, Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now())),
                new Customer("4","dummy4","dummy","dummy","dummy","dummy","dummy","dummy",1, Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now())),
                new Customer("5","dummy5","dummy","dummy","dummy","dummy","dummy","dummy",1, Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now()))
        ));
    }

    private void setUpTableCustomer(ObservableList<Customer> list) {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        colIndetityID.setCellValueFactory(new PropertyValueFactory<Customer, String>("identityID"));
        colIdentityType.setCellValueFactory(new PropertyValueFactory<Customer, String>("identityType"));
        colGender.setCellValueFactory(new PropertyValueFactory<Customer, String>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        colStatusFDS.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("fds"));

        Callback<TableColumn<Customer, String>, TableCell<Customer, String>> cellAction = (TableColumn<Customer, String> param) -> {
            final TableCell<Customer, String> cell = new TableCell<Customer, String>() {
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
                            this.onUpdateCustomer(tableCustomer.getSelectionModel().getSelectedItem());
                        });

                        FontAwesomeIconView delete = new FontAwesomeIconView();
                        delete.setSize("25");
                        delete.setFill(Color.web("#ff0033"));
                        delete.setGlyphName("TRASH");
                        delete.setCursor(Cursor.HAND);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            this.onDeleteCustomer(customer);
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
                private void onUpdateCustomer(Customer data) {
                    FXMLLoader loader = ServiceGlobalComponents.generateFXMLLoader("fxml/customer-dialog-view.fxml");
                    CustomerDialogController controller = loader.getController();
                    controller.setDataCustomer(data);
                    ServiceGlobalComponents.showPopUpDialog(loader, "Update Customer".concat("-" + data.getCustomerName()));
                }

                private boolean onDeleteCustomer(Customer customer) {
                    boolean response = false;
                    try {
                        CustomerServiceImpl service = new CustomerServiceImpl(customerRepository);
                        response = service.onDeleteCustomer(customer);
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
        tableCustomer.setItems(list);
    }

    @FXML
    public void onBtnPrintProcess(ActionEvent event) throws IOException {
        GenerateFilePDF generateFilePDF = new GenerateFilePDF();
        generateFilePDF.generateFileHTML(Paths.get("fileReportDependency.html"), generateHTML());
        generateFilePDF.generateFilePDF(Paths.get("fileReportDependency.pdf"), Paths.get("fileReportDependency.html"));
    }

    private String generateHTML() {
        return """
                <!DOCTYPE html>
                <html>
                  <head>
                    <title>File Report</title>
                    <style>
                    @page { size: A4 landscape;}
                        table {
                          border: 1px solid #ccc;
                          margin: 0;
                          padding: 0;
                          width: 100%;
                        }
                
                        table caption {
                          font-size: 1.5em;
                          margin: .5em 0 .75em;
                        }
                
                        table tr {
                          background-color: #f8f8f8;
                          border: 1px solid #ddd;
                          padding: .35em;
                        }
                
                        table th,
                        table td {
                          padding: .625em;
                          text-align: center;
                        }
                
                        table th {
                          font-size: .85em;
                          letter-spacing: .1em;
                          text-transform: uppercase;
                        }
                
                        /* general styling */
                        body {
                          font-family: "Open Sans", sans-serif;
                        }
                    </style>
                  </head>
                  <body>
                        <table>
                            <caption>List Report Order Customer</caption>
                            <thead>
                            <tr>
                                <th scope="col">Created Date</th>
                                <th scope="col">CustomerID</th>
                                <th scope="col">Customer Name</th>
                                <th scope="col">BusName</th>
                                <th scope="col">Duration</th>
                                <th scope="col">Pick Up Point</th>
                                <th scope="col">Destination</th>
                                <th scope="col">Price</th>
                                <th scope="col">Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td data-label="Created Date">04/01/2016</td>
                                <td data-label="CustomerID">1</td>
                                <td data-label="Customer Name">cus dummy 1</td>
                                <td data-label="BusName">bus dummy 1</td>
                                <td data-label="Duration">03/01/2016 - 03/31/2016</td>
                                <td data-label="Pick Up Point">Jakarta Kota</td>
                                <td data-label="Destination">Carita</td>
                                <td data-label="Price">Rp.1,300,000,-</td>
                                <td data-label="Status">Done Payment</td>
                            </tr>
                            <tr>
                                <td data-label="Created Date">04/01/2016</td>
                                <td data-label="CustomerID">1</td>
                                <td data-label="Customer Name">cus dummy 2</td>
                                <td data-label="BusName">bus dummy 2</td>
                                <td data-label="Duration">03/01/2016 - 03/31/2016</td>
                                <td data-label="Pick Up Point">Jakarta Kota</td>
                                <td data-label="Destination">Carita</td>
                                <td data-label="Price">Rp.1,300,000,-</td>
                                <td data-label="Status">Done Payment</td>
                            </tr>
                            <tr>
                                <td data-label="Created Date">04/01/2016</td>
                                <td data-label="CustomerID">1</td>
                                <td data-label="Customer Name">cus dummy 3</td>
                                <td data-label="BusName">bus dummy 3</td>
                                <td data-label="Duration">03/01/2016 - 03/31/2016</td>
                                <td data-label="Pick Up Point">Jakarta Kota</td>
                                <td data-label="Destination">Carita</td>
                                <td data-label="Price">Rp.1,300,000,-</td>
                                <td data-label="Status">Done Payment</td>
                            </tr>
                            <tr>
                                <td data-label="Created Date">04/01/2016</td>
                                <td data-label="CustomerID">1</td>
                                <td data-label="Customer Name">cus dummy 4</td>
                                <td data-label="BusName">bus dummy 4</td>
                                <td data-label="Duration">03/01/2016 - 03/31/2016</td>
                                <td data-label="Pick Up Point">Jakarta Kota</td>
                                <td data-label="Destination">Carita</td>
                                <td data-label="Price">Rp.1,300,000,-</td>
                                <td data-label="Status">Done Payment</td>
                            </tr>
                            </tbody>
                        </table>
                        <h3 style="text-align:right"><strong>PT. PERFORMA TRANS</strong></h3>
                  </body>
                </html>
                """;
    }
}
