package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Service.IParamService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;

import java.util.ArrayList;
import java.util.List;

public class ParamServiceImpl implements IParamService {
    private IParamRepository paramRepository;

    public ParamServiceImpl(IParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }

    @Override
    public ControlMessage<List<GlobalParameter>> InquiryGlobalParam(GlobalParameter data) {
        ControlMessage<List<GlobalParameter>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        try {
            switch (data.getGroup()) {
                case "Destination":
                    response = paramRepository.InquiryDestinationParam(data);
                    break;
                case "Bus":
                    response = paramRepository.InquiryBusListParam(data);
                    break;
                default:
                    response = paramRepository.InquiryGlobalParam(data);
                    break;
            }
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
