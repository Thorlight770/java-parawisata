package com.java.parawisata.javaparawisata.Repository;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.BusPrice;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import java.util.List;

public interface IBusRepository {
    ControlMessage<Bus> update(Bus data);
    ControlMessage<Bus> delete(Bus data);
    ControlMessage<List<Bus>> getBuss();
    ControlMessage<List<BusPrice>> getBusDetailsByID(String busID);
    ControlMessage<BusPrice> add(BusPrice data);
    ControlMessage<BusPrice> update(BusPrice data);
    ControlMessage<BusPrice> delete(BusPrice data);
}
