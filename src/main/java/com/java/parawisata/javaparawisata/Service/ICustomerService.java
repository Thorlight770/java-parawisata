package com.java.parawisata.javaparawisata.Service;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Entity.SignUp;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public interface ICustomerService {
    ControlMessage<Customer> onUpdateCustomer(Customer data);
    boolean onDeleteCustomer(Customer data);
    ControlMessage<SignUp> onAddCustomer(SignUp data);
}
