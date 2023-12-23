package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Entity.SignUp;
import com.java.parawisata.javaparawisata.Repository.ICustomerRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.CustomerRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.ICustomerService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;

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

    @Override
    public ControlMessage<SignUp> onAddCustomer(SignUp data) {
        ControlMessage<SignUp> response = new ControlMessage<>();
        response.data = new SignUp();
        response.isSuccess = true;
        try {
            ControlMessage validate = onAddCustomerValidate(data);
            if (!validate.isSuccess) response.isSuccess = false;
            if (!validate.getMessages().isEmpty()) response.messages.addAll(validate.getMessages());

            if (response.isSuccess) response = customerRepository.add(data);
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    private ControlMessage onAddCustomerValidate(SignUp data) {
        ControlMessage response = new ControlMessage();
        response.isSuccess = true;
        try {
            if (data.getName().isBlank() || data.getName().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Nama Tidak Boleh Kosong !"));

            if (data.getIdentityType().isBlank() || data.getIdentityType().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Identity Type Tidak Boleh Kosong !"));

            if (data.getIdentityID().isBlank() || data.getIdentityID().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Identity ID Tidak Boleh Kosong !"));

            if (!data.getIdentityID().matches("[0-9]+"))
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Identity ID Tidak Boleh Ada Karakter !"));

            if (data.getEmail().isBlank() || data.getEmail().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Email Tidak Boleh Kosong !"));

            if (data.getUsername().isBlank() || data.getUsername().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Username Tidak Boleh Kosong !"));

            if (data.getUsername().contains(" "))
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Username Tidak Boleh Mengandung Spasi !"));

            if (data.getPassword().isBlank() || data.getPassword().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Password Tidak Boleh Kosong !"));

            if (data.getPassword().contains(" "))
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Password Tidak Boleh Mengandung Spasi !"));

            if (data.getPassword().length() <= 6)
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Password Harus Lebih Dari 6 Karakter !"));

            if (response.getMaxMessageType().getValue() >= MessageType.ERROR.getValue()) response.isSuccess = false;

        } catch (Exception ex) {
            response.isSuccess = false;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
