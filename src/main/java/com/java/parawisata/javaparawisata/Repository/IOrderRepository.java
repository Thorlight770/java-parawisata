package com.java.parawisata.javaparawisata.Repository;

import com.java.parawisata.javaparawisata.Entity.HistoryOrder;
import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Entity.OrderApproval;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import java.util.List;

public interface IOrderRepository {
    ControlMessage<Order> add(Order data);
    ControlMessage<Order> update(Order data);
    ControlMessage<List<HistoryOrder>> getHistoryOrdersByUserID(String userID);

    // <editor-folds desc="Approval">
    ControlMessage<List<OrderApproval>> getAllOrderApproval();
    ControlMessage<OrderApproval> processOrderApproval(OrderApproval orderApproval, String status);
    // </editor-folds>
}
