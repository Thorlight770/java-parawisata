package com.java.parawisata.javaparawisata.Service.Impl;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Repository.IAuthRepository;
import com.java.parawisata.javaparawisata.Service.IAuthService;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;

public class AuthServiceImpl implements IAuthService {
    private IAuthRepository authRepository;

    public AuthServiceImpl() {
    }

    public AuthServiceImpl(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public ControlMessage<Auth> getTokenAuthenticate(String username, String password) {
        ControlMessage<Auth> response = new ControlMessage<>();
        response.data = new Auth();
        try {
            ControlMessage validateRsp = getTokenAuthValidate(username, password);
            if (!validateRsp.messages.isEmpty()) response.messages.addAll(validateRsp.messages);
            response.isSuccess = validateRsp.isSuccess;

            if (response.isSuccess)
                response = authRepository.getTokeAuthenticate(username, password);
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    public ControlMessage getTokenAuthValidate(String username, String password) {
        ControlMessage response = new ControlMessage();
        try {
            if (username.isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.VALIDATE, "Invalid Username"));

            if (password.isEmpty())
                response.messages.add(new AdditionalMessage(MessageType.VALIDATE, "Invalid Password"));

            if (response.getMaxMessageType().getValue() >= MessageType.VALIDATE.getValue())
                response.isSuccess = false;
            else response.isSuccess = true;

        } catch (Exception ex) {
            response.isSuccess = false;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
