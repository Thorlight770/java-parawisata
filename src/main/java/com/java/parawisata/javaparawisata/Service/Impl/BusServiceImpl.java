package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.BusMaint;
import com.java.parawisata.javaparawisata.Entity.BusPrice;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Service.IBusService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;

import java.util.List;

public class BusServiceImpl implements IBusService {
    private IBusRepository busRepository;

    public BusServiceImpl() {
    }

    public BusServiceImpl(IBusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public ControlMessage<BusMaint> onUpdateBus(BusMaint data) {
        ControlMessage<BusMaint> response = new ControlMessage<>();
        response.data = new BusMaint();
        response.isSuccess = true;
        try {
            ControlMessage validate = onUpdateBusValidate(data);
            if (!validate.isSuccess) response.isSuccess = false;
            if (!validate.getMessages().isEmpty()) response.getMessages().addAll(validate.getMessages());

            if (response.isSuccess) {
                response = this.busRepository.update(data);
            }
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    private ControlMessage onUpdateBusValidate(BusMaint data) {
        ControlMessage response = new ControlMessage();
        response.isSuccess = true;
        try {
            if (data.getBusName().isBlank() || data.getBusName().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Bus Name Tidak Boleh Kosong !"));

            if (data.getUserID().isBlank() || data.getUserID().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "User ID Tidak Boleh Kosong !"));

            if (data.getBusPrices().isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "List Bus Price Tidak Boleh Kosong !"));

            if (!data.getBusPrices().isEmpty() && data.getBusPrices().stream().allMatch(x -> x.getAction() != null && x.getUserID() == null))
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "User ID List Tidak Boleh Kosong !"));

            if (response.getMaxMessageType().getValue() >= MessageType.ERROR.getValue()) response.isSuccess = false;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Bus> onDeleteBus(Bus data) {
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
