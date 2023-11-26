package com.java.parawisata.javaparawisata.Service;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public interface IBusService {
    ControlMessage<Bus> onUpdateBus(Bus data);
    boolean onDeleteBus(Bus data);
}
