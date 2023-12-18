package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Entity.HistoryOrder;
import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Entity.OrderApproval;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Service.IOrderService;
import com.java.parawisata.javaparawisata.Service.IParamService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Objects;

public class OrderServiceImpl implements IOrderService {
    private IOrderRepository orderRepository;

    private IParamService paramService;

    public OrderServiceImpl(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderServiceImpl(IOrderRepository orderRepository, IParamRepository paramRepository) {
        this.orderRepository = orderRepository;
        this.paramService = new ParamServiceImpl(paramRepository);
    }

    @Override
    public ControlMessage<Order> onAddOrder(Order data) {
        ControlMessage<Order> response = new ControlMessage<>();
        response.data = new Order();
        response.isSuccess = true;
        try {
            ControlMessage validateRsp = onAddOrderValidate(data);
            if (!validateRsp.isSuccess) response.isSuccess = false;
            if (!validateRsp.getMessages().isEmpty()) response.messages.addAll(validateRsp.getMessages());

            if (response.isSuccess) {
                response = this.orderRepository.add(data);
            }
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    public ControlMessage onAddOrderValidate(Order data) {
        ControlMessage response = new ControlMessage<>();
        try {
            GlobalParameter destinationRq = new GlobalParameter();
            destinationRq.setGroup("Destination");
            ControlMessage<List<GlobalParameter>> destinationList = this.paramService.InquiryGlobalParam(destinationRq);
            if (!destinationList.isSuccess || destinationList.data.isEmpty()) {
                response.messages.addAll(destinationList.getMessages());
                throw new Exception("Inquiry Destination Param Failed!");
            }

            if (data.getDestination() == null || data.getDestination().isEmpty() || data.getDestination().isBlank())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Destination Point Tidak Boleh Kosong !"));
            else {
                destinationList.data.forEach(x -> {
                    if (x.getValue().equals(data.getDestination())) {
                        //long duration = Duration.between((Temporal) data.getDateFrom(), (Temporal) data.getDateTo()).toDays();
                        //if (duration > Long.parseLong(x.getInfo01())) {
                          //  response.messages.add(new AdditionalMessage(MessageType.ERROR, "Durasi Tur Lebih Besar Dari Durasi Sewa Bus !"));
                        //}
                    }
                });
            }

            if (data.getCustomerID() == null || data.getCustomerID().isEmpty() || data.getCustomerID().isBlank())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Cutomer ID Tidak Boleh Kosong !"));

            if (data.getBusID() == null || data.getBusID().isEmpty() || data.getBusID().isBlank())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Bus ID Tidak Boleh Kosong !"));

            if (data.getDriverID() == null || data.getDriverID().isEmpty() || data.getDriverID().isBlank())
                response.messages.add(new AdditionalMessage(MessageType.ERROR, "Driver ID Tidak Boleh Kosong !"));

            if (response.getMaxMessageType().getValue() >= MessageType.ERROR.getValue()) response.isSuccess = false;
            else response.isSuccess = true;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Order> onUpdateOrder(Order data) {
        return null;
    }

    @Override
    public ControlMessage<List<HistoryOrder>> getAllHistoryOrderByUserID(String userID) {
        return orderRepository.getHistoryOrdersByUserID(userID);
    }

    @Override
    public ControlMessage<List<OrderApproval>> getAllOrderPendingApproval() {
        return this.orderRepository.getAllOrderApproval();
    }
}
