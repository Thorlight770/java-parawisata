package com.java.parawisata.javaparawisata.Service;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

import java.util.List;

public interface IParamService {
    ControlMessage<List<GlobalParameter>> InquiryGlobalParam(GlobalParameter data);
}
