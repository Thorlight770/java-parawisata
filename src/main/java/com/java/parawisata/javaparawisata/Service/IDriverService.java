package com.java.parawisata.javaparawisata.Service;

import com.java.parawisata.javaparawisata.Entity.Driver;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import java.util.List;

public interface IDriverService {
    ControlMessage<List<Driver>> getDrivers();
    ControlMessage<Driver> getDriverByID(String driverID);
    ControlMessage<Driver> addDriver(Driver driver);
    ControlMessage<Driver> updateDriver(Driver driver);
    ControlMessage<Driver> deleteDriver(Driver driver);
}
