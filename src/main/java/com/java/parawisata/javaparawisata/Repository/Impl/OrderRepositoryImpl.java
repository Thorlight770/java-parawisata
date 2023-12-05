package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;

public class OrderRepositoryImpl implements IOrderRepository {
    @Override
    public ControlMessage<Order> add(Order data) {
        ControlMessage<Order> response = new ControlMessage<>();
        response.data = new Order();
        try {
            // <editor-fold desc="query">
            String query = """
                    """;
            // </editor-fold>
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Order> update(Order data) {
        return null;
    }
}
