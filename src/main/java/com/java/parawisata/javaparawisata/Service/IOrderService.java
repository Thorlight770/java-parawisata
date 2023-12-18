package com.java.parawisata.javaparawisata.Service;

import com.java.parawisata.javaparawisata.Entity.HistoryOrder;
import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Entity.OrderApproval;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import java.util.List;

public interface IOrderService {
    ControlMessage<Order> onAddOrder(Order data);
    ControlMessage<Order> onUpdateOrder(Order data);
    ControlMessage<List<HistoryOrder>> getAllHistoryOrderByUserID(String userID);

    // <editor-folds desc="Approval">
    ControlMessage<List<OrderApproval>> getAllOrderPendingApproval();
    // </editor-folds>
}
