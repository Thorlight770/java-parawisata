package com.java.parawisata.javaparawisata.Repository;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import javax.sound.sampled.Control;
import java.util.List;

public interface IParamRepository {
    ControlMessage<List<GlobalParameter>> InquiryGlobalParam(GlobalParameter data);
    ControlMessage<List<GlobalParameter>> InquiryDestinationParam(GlobalParameter data);
    ControlMessage<List<GlobalParameter>> InquiryBusListParam(GlobalParameter data);
}
