package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Service.IBusService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

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
}
