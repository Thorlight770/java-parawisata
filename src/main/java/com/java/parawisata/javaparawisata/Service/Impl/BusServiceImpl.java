package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.BusPrice;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Service.IBusService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import java.util.List;

public class BusServiceImpl implements IBusService {
    private IBusRepository busRepository;

    public BusServiceImpl() {
    }

    public BusServiceImpl(IBusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public ControlMessage<Bus> onUpdateBus(Bus data) {
        return this.busRepository.update(data);
    }

    @Override
    public boolean onDeleteBus(Bus data) {
        return this.busRepository.delete(data);
    }

    @Override
    public ControlMessage<List<Bus>> getAllBus() {
        return this.busRepository.getBuss();
    }

    @Override
    public ControlMessage<List<BusPrice>> getBusDetailsPriceByID(String busID) {
        return this.busRepository.getBusDetailsByID(busID);
    }

    @Override
    public ControlMessage<BusPrice> addBusPrice(BusPrice data) {
        return this.busRepository.add(data);
    }

    @Override
    public ControlMessage<BusPrice> updateBusPrice(BusPrice data) {
        return this.busRepository.update(data);
    }

    @Override
    public ControlMessage<BusPrice> deleteBusPrice(BusPrice data) {
        return this.busRepository.delete(data);
    }
}
