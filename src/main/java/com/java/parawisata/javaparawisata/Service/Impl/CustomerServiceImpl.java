package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Repository.ICustomerRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.CustomerRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.ICustomerService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public class CustomerServiceImpl implements ICustomerService {
    private ICustomerRepository customerRepository;

    public CustomerServiceImpl() {
    }

    public CustomerServiceImpl(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ControlMessage<Customer> onUpdateCustomer(Customer data) {
        return this.customerRepository.update(data);
    }

    @Override
    public boolean onDeleteCustomer(Customer data) {
        return this.customerRepository.delete(data);
    }
}
