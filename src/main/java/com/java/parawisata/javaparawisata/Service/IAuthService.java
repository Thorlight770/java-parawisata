package com.java.parawisata.javaparawisata.Service;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public interface IAuthService {
    ControlMessage<Auth> getTokenAuthenticate(String username, String password);
}
