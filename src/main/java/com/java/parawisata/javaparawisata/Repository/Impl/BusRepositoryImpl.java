package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public class BusRepositoryImpl implements IBusRepository {
    @Override
    public ControlMessage<Bus> update(Bus data) {
        return null;
    }

    @Override
    public boolean delete(Bus data) {
        return false;
    }
}
