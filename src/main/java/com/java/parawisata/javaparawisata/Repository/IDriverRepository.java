package com.java.parawisata.javaparawisata.Repository;

import com.java.parawisata.javaparawisata.Entity.Driver;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import java.util.List;

public interface IDriverRepository {
    ControlMessage<List<Driver>> getDrivers();
    ControlMessage<Driver> getDriverByID(String driverID);
    ControlMessage<Driver> add(Driver driver);
    ControlMessage<Driver> update(Driver driver);
    ControlMessage<Driver> delete(String driverID, String userID);
}
