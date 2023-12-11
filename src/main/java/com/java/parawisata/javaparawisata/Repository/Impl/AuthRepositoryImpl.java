package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Repository.IAuthRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class AuthRepositoryImpl implements IAuthRepository {
    @Override
    public ControlMessage<Auth> getTokeAuthenticate(String username, String password) {
        ControlMessage<Auth> response = new ControlMessage<>();
        response.data = new Auth();
        try {
            // <editor-fold desc="query">
            String query = """
                    DECLARE @username VARCHAR(100),
                    @password VARCHAR(100)
                    
                    SET @username = ?
                    SET @password = ?
                    
                    IF (ISNULL(@username, '') = '' OR ISNULL(@password, '') = '')
                    BEGIN
                        RAISERROR('Invalid Username & Password', 16, 1)
                    END
                    
                    IF NOT EXISTS(SELECT TOP 1 1 FROM AuthenticateMs
                              WHERE Username = @username AND password = @password)
                    BEGIN
                        RAISERROR('Invalid Username & Password', 16, 1)
                    END
                    
                    SELECT Username, Password, Role, ISNULL(CustomerID, AdminID) AS UserID FROM AuthenticateMs
                    WHERE Username = @username AND password = @password
                    """;
            // </editor-fold>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            boolean result = pst.execute();
            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(Auth.class));

            if (result && !resultObj.isEmpty()) {
                response.data = Auth.class.cast(resultObj.get(0).get(0));
                response.isSuccess = true;
            }
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
