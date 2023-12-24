package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.Driver;
import com.java.parawisata.javaparawisata.Repository.IDriverRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DriverRepositoryImpl implements IDriverRepository {
    @Override
    public ControlMessage<List<Driver>> getDrivers() {
        ControlMessage<List<Driver>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    SELECT TOP 100 * FROM DriverMs
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            boolean result = pst.execute();

            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(Driver.class));
            if (result && !resultObj.isEmpty()) {
                resultObj.get(0).forEach(x -> {
                    response.data.add((Driver) x);
                });
                response.isSuccess = true;
            } else response.messages.add(new AdditionalMessage(MessageType.WARNING, "Data Tidak Ada !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Driver> getDriverByID(String driverID) {
        ControlMessage<Driver> response = new ControlMessage<>();
        response.data = new Driver();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE @DriverID VARCHAR(200) = ?
                    
                    IF (ISNULL(@DriverID, '') <> '')
                        SELECT * FROM DriverMs WHERE DriverID = @DriverID
                    ELSE
                        RAISERROR('Driver ID Not Null !', 16, 1)
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, driverID);
            boolean result = pst.execute();
            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(Driver.class));

            if (result && !resultObj.isEmpty()) {
                response.data = (Driver) resultObj.get(0).get(0);
                response.isSuccess = true;
            } else response.messages.add(new AdditionalMessage(MessageType.WARNING, "Data Tidak Ada !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Driver> add(Driver driver) {
        ControlMessage<Driver> response = new ControlMessage<>();
        response.data = new Driver();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE
                    @DriverName VARCHAR(200),
                    @UserID VARCHAR(20),
                    @ErrMsg VARCHAR(100)
                    
                    SET @DriverName = ?
                    SET @UserID = ?
                    SET @ErrMsg = ''
                    
                    IF (ISNULL(@DriverName, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Driver Name Not Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                        
                    IF (ISNULL(@UserID, '') = '')
                    BEGIN
                        SET @ErrMsg = 'User ID Not Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                        
                    IF EXISTS (SELECT TOP 1 1 FROM DriverMs WHERE UPPER(DriverName) = @DriverName)
                    BEGIN
                        SET @ErrMsg = 'Driver Name Already Exists !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                    -------------------------------------------------------------------------------
                    IF (ISNULL(@ErrMsg, '') = '')
                    BEGIN
                        INSERT INTO DriverMs (DriverID, DriverName, CreatedDate, UserID)
                        SELECT NEWID(), @DriverName, GETDATE(), @UserID
                    END
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, driver.getDriverName());
            pst.setString(2, driver.getUserID());
            int result = pst.executeUpdate();

            response.data = driver;
            response.messages.add(new AdditionalMessage(MessageType.SUCCESS, "Create Driver Success !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Driver> update(Driver driver) {
        ControlMessage<Driver> response = new ControlMessage<>();
        response.data = new Driver();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE @DriverID VARCHAR(200)
                    DECLARE @DriverName VARCHAR(200)
                    DECLARE @UserID VARCHAR(20)
                    DECLARE @ErrMsg VARCHAR(100)
                    
                    SET @DriverID = ?
                    SET @DriverName = ?
                    SET @UserID = ?
                    SET @ErrMsg = ''
                    
                    IF (ISNULL(@DriverID, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Driver ID Not Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                        
                    IF (ISNULL(@DriverName, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Driver Name Not Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                        
                    IF (ISNULL(@UserID, '') = '')
                    BEGIN
                        SET @ErrMsg = 'User ID Not Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                        
                    IF NOT EXISTS (SELECT TOP 1 1 FROM DriverMs WHERE UPPER(DriverID) = @DriverID)
                    BEGIN
                        SET @ErrMsg = 'DriverID Not Found !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                        
                    IF EXISTS (SELECT TOP 1 1 FROM DriverMs WHERE UPPER(DriverName) = @DriverName)
                    BEGIN
                        SET @ErrMsg = 'Driver Name Already Exists !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                    -------------------------------------------------------------------------------
                    IF (ISNULL(@ErrMsg, '') = '')
                    BEGIN
                        INSERT INTO DriverHist (DriverID,DriverName,CreatedDate,UpdatedDate,UserID)
                        SELECT * FROM DriverMs WHERE DriverID = @DriverID
                        
                        UPDATE DriverMs
                        SET DriverName = @DriverName,
                        UpdateDate = GETDATE(),
                        UserID = @UserID
                        WHERE DriverID = @DriverID
                    END
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, driver.getDriverID());
            pst.setString(2, driver.getDriverName());
            pst.setString(3, driver.getUserID());
            int result = pst.executeUpdate();

            response.data = driver;
            response.messages.add(new AdditionalMessage(MessageType.SUCCESS, "Update Driver Success !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Driver> delete(String driverID, String userID) {
        ControlMessage<Driver> response = new ControlMessage<>();
        response.data = new Driver();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE
                    @DriverID VARCHAR(100),
                    @UserID VARCHAR(100),
                    @ErrMsg VARCHAR(100)
                    
                    SET @DriverID = ?
                    SET @UserID = ?
                    SET @ErrMsg = ''
                    
                    IF (ISNULL(@DriverID, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Driver ID Not Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                    
                    IF (ISNULL(@UserID, '') = '')
                    BEGIN
                        SET @ErrMsg = 'User ID Not Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                        
                    IF NOT EXISTS(SELECT TOP 1 1 FROM DriverMs WHERE DriverID = @DriverID)
                    BEGIN
                        SET @ErrMsg = 'Driver ID Not Found !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                    
                    IF (ISNULL(@ErrMsg, '') = '')
                    BEGIN
                        INSERT INTO DriverHist (DriverID,DriverName,CreatedDate,UpdatedDate,UserID)
                        SELECT * FROM DriverMs WHERE DriverID = @DriverID
                        
                        DELETE DriverMs WHERE DriverID = @DriverID
                    END
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, driverID);
            pst.setString(2, userID);

            int result = pst.executeUpdate();

            response.messages.add(new AdditionalMessage(MessageType.SUCCESS, "Delete Driver Success !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
