package com.java.parawisata.javaparawisata.Repository;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public interface ICustomerRepository {
    ControlMessage<Customer> update(Customer data);
    boolean delete(Customer data);
}
