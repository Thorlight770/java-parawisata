package com.java.parawisata.javaparawisata.Repository;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;

public interface IAuthRepository {
    ControlMessage<Auth> getTokeAuthenticate(String username, String password);
}
