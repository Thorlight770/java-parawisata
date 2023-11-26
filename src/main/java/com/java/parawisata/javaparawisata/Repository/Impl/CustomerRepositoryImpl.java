package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Repository.ICustomerRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public class CustomerRepositoryImpl implements ICustomerRepository {
    public CustomerRepositoryImpl() {
    }

    @Override
    public ControlMessage<Customer> update(Customer data) {
        return null;
    }

    @Override
    public boolean delete(Customer data) {
        return false;
    }
}
