package com.java.parawisata.javaparawisata.Service;

import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public interface IOrderService {
    ControlMessage<Order> onAddOrder(Order data);
    ControlMessage<Order> onUpdateOrder(Order data);
}
