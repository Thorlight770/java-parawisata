package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Repository.ICustomerRepository;
import com.java.parawisata.javaparawisata.Service.Impl.CustomerServiceImpl;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerDialogController implements Initializable {
    @FXML
    public Label labelCustomerID;

    @FXML
    public Label labelCustomerName;
    private ICustomerRepository customerRepository;
    private Customer _customer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public ControlMessage<Customer> onBtnProcessUpdate() {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepository);
        return service.onUpdateCustomer(_customer);
    }

    public void setDataCustomer(Customer data) {
        this._customer = data;
        this.labelCustomerID.setText(data.getCustomerID());
        this.labelCustomerName.setText(data.getCustomerName());
    }
}
