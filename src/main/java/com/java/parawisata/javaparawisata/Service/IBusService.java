package com.java.parawisata.javaparawisata.Service;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.BusMaint;
import com.java.parawisata.javaparawisata.Entity.BusPrice;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import java.util.List;

public interface IBusService {
    ControlMessage<BusMaint> onUpdateBus(BusMaint data);
    ControlMessage<Bus> onDeleteBus(Bus data);
    ControlMessage<List<Bus>> getAllBus();
    ControlMessage<List<BusPrice>> getBusDetailsPriceByID(String busID);
    ControlMessage<BusPrice> addBusPrice(BusPrice data);
    ControlMessage<BusPrice> updateBusPrice(BusPrice data);
    ControlMessage<BusPrice> deleteBusPrice(BusPrice data);
}
