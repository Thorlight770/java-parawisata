package com.java.parawisata.javaparawisata.Repository;

import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public interface IOrderRepository {
    ControlMessage<Order> add(Order data);
    ControlMessage<Order> update(Order data);
}
