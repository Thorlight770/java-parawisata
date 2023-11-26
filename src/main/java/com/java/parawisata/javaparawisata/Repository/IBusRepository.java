package com.java.parawisata.javaparawisata.Repository;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public interface IBusRepository {
    ControlMessage<Bus> update(Bus data);
    boolean delete(Bus data);
}
