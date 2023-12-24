package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.Driver;
import com.java.parawisata.javaparawisata.Repository.IDriverRepository;
import com.java.parawisata.javaparawisata.Service.IDriverService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;

import java.util.List;

public class DriverServiceImpl implements IDriverService {
    private IDriverRepository driverRepository;

    public DriverServiceImpl(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public ControlMessage<List<Driver>> getDrivers() {
        return driverRepository.getDrivers();
    }

    @Override
    public ControlMessage<Driver> getDriverByID(String driverID) {
        return driverRepository.getDriverByID(driverID);
    }

    private ControlMessage driverValidate(Driver data) {
        ControlMessage response = new ControlMessage();
        response.isSuccess = true;
        try {
            if (data.getDriverName().isEmpty() || data.getDriverName().isBlank())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Driver Name Tidak Boleh Kosong !"));

            if (data.getUserID().isBlank() || data.getUserID().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "User ID Tidak Boleh Kosong !"));

            if (response.getMaxMessageType().getValue() >= MessageType.ERROR.getValue()) response.isSuccess = false;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
    @Override
    public ControlMessage<Driver> addDriver(Driver driver) {
        ControlMessage<Driver> response = new ControlMessage<>();
        response.data = new Driver();
        response.isSuccess = true;
        try {
            ControlMessage validate = driverValidate(driver);
            if (!validate.isSuccess) response.isSuccess = false;
            if (!validate.getMessages().isEmpty()) response.messages.addAll(validate.getMessages());

            if (response.isSuccess)
                response = driverRepository.add(driver);
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Driver> updateDriver(Driver driver) {
        ControlMessage<Driver> response = new ControlMessage<>();
        response.data = new Driver();
        response.isSuccess = true;
        try {
            ControlMessage validate = driverValidate(driver);
            if (!validate.isSuccess) response.isSuccess = false;
            if (!validate.getMessages().isEmpty()) response.messages.addAll(validate.getMessages());

            if (response.isSuccess)
                response = driverRepository.update(driver);
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Driver> deleteDriver(Driver driver) {
        ControlMessage<Driver> response = new ControlMessage<>();
        response.data = new Driver();
        response.isSuccess = true;
        try {
            ControlMessage validate = driverValidate(driver);
            if (!validate.isSuccess) response.isSuccess = false;
            if (!validate.getMessages().isEmpty()) response.messages.addAll(validate.getMessages());

            if (response.isSuccess)
                response = driverRepository.delete(driver.getDriverID(), driver.getUserID());
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
