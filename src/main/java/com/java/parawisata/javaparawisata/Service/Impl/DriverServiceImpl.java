package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.Driver;
import com.java.parawisata.javaparawisata.Repository.IDriverRepository;
import com.java.parawisata.javaparawisata.Service.IDriverService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

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

    @Override
    public ControlMessage<Driver> addDriver(Driver driver) {
        return driverRepository.add(driver);
    }

    @Override
    public ControlMessage<Driver> updateDriver(Driver driver) {
        return driverRepository.update(driver);
    }

    @Override
    public ControlMessage<Driver> deleteDriver(String driverID) {
        return driverRepository.delete(driverID);
    }
}
