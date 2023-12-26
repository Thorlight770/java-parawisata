package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.BusMaint;
import com.java.parawisata.javaparawisata.Entity.BusPrice;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Database.DBConnection;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BusRepositoryImpl implements IBusRepository {
    @Override
    public ControlMessage<BusMaint> update(BusMaint data) {
        ControlMessage<BusMaint> response = new ControlMessage<>();
        response.data = new BusMaint();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE @InputTable TABLE (
                    	BusPriceID varchar(100),
                    	BusName varchar(100),
                    	Price decimal(20, 2),
                    	Duration int,
                    	Destination	varchar(100),
                    	UserID	varchar(100),
                    	[Action] VARCHAR(1)
                    )
                    INSERT INTO @InputTable
                    SELECT * FROM ?
                    
                    DECLARE
                    @BusID INT,
                    @Fasilitas VARCHAR(100),
                    @UserID VARCHAR(100),
                    @ErrMsg VARCHAR(100)
                                        
                    SET @BusID = ?
                    SET @Fasilitas = ?
                    SET @UserID = ?
                    SET @ErrMsg = ''
                                        
                    -----------------------------------------------------------
                    --VALIDATE
                    -----------------------------------------------------------
                    IF (@BusID <= 0)
                    BEGIN
                    	SET @ErrMsg = 'Bus ID Not Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF (ISNULL(@UserID, '') = '')
                    BEGIN
                    	SET @ErrMsg = 'User ID Not Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF EXISTS(SELECT TOP 1 1 FROM @InputTable a
                    		  JOIN OrderBusMs b ON a.BusPriceID = b.BusID
                    		  WHERE a.[Action] = 'D')
                    BEGIN
                    	SET @ErrMsg = 'Cant Delete Bus Price ID (Bus Price Already In Use) !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                    -----------------------------------------------------------
                    --PROCESS
                    -----------------------------------------------------------
                    IF (ISNULL(@ErrMsg, '') = '')
                    BEGIN
                    	IF EXISTS(SELECT TOP 1 1 FROM @InputTable WHERE [Action] = 'I')
                    	BEGIN
                    		INSERT INTO BusPrice_TR (BusPriceID,BusName,Price,Duration,Destination,UserID,CreatedDate)
                    		SELECT NEWID(), BusName, Price, Duration, Destination, UserID, GETDATE() FROM @InputTable WHERE [Action] = 'I'
                    	END
                                        
                    	IF EXISTS(SELECT TOP 1 1 FROM @InputTable WHERE [Action] = 'U')
                    	BEGIN
                    		INSERT INTO BusPriceHist_TR(BusPriceID,BusName,Price,Duration,Destination,UserID,SupervisorID,CreatedDate,UpdateDate)
                    		SELECT a.BusPriceID,a.BusName,a.Price,a.Duration,a.Destination,a.UserID,a.SupervisorID,a.CreatedDate,a.UpdateDate
                    		FROM BusPrice_TR a
                    		JOIN @InputTable b
                    		ON a.BusPriceID = b.BusPriceID
                    		WHERE b.[Action] = 'U'
                                        
                    		UPDATE a
                    		SET
                    		a.Price = b.Price,
                    		a.Duration = b.Duration,
                    		a.Destination = b.Destination,
                    		a.UpdateDate = GETDATE()
                    		FROM BusPrice_TR a
                    		JOIN @InputTable b
                    		ON a.BusPriceID = b.BusPriceID
                    		WHERE b.[Action] = 'U'
                    	END
                                        
                    	IF EXISTS(SELECT TOP 1 1 FROM @InputTable WHERE [Action] = 'D')
                    	BEGIN
                    		INSERT INTO BusPriceHist_TR(BusPriceID,BusName,Price,Duration,Destination,UserID,SupervisorID,CreatedDate,UpdateDate)
                    		SELECT a.BusPriceID,a.BusName,a.Price,a.Duration,a.Destination,a.UserID,a.SupervisorID,a.CreatedDate,a.UpdateDate
                    		FROM BusPrice_TR a
                    		JOIN @InputTable b
                    		ON a.BusPriceID = b.BusPriceID
                    		WHERE b.[Action] = 'D'
                                        
                    		DELETE a
                    		FROM BusPrice_TR a
                    		JOIN @InputTable b
                    		ON a.BusPriceID = b.BusPriceID
                    		WHERE b.[Action] = 'D'
                    	END
                                        
                    	UPDATE JavaParawisataParam_TR
                    	SET
                    	Info02 = @Fasilitas,
                    	UpdateDate = GETDATE(),
                    	UserID = @UserID
                    	WHERE [Group] = 'Bus' AND Sort = @BusID
                    END
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            SQLServerPreparedStatement pst = (SQLServerPreparedStatement) connection.prepareStatement(query);
            pst.setStructured(1, "dbo.BusPriceProcess", DBConnection.MappingListToTable(data.getBusPrices()));
            pst.setString(2, data.getBusID());
            pst.setString(3, data.getFasilitas());
            pst.setString(4, data.getUserID());

            ResultSet result = pst.executeQuery();

            response.messages.add(new AdditionalMessage(MessageType.SUCCESS, "Update Bus Success !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Bus> delete(Bus data) {
        ControlMessage<Bus> response = new ControlMessage<>();
        response.data = new Bus();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);

            response.messages.add(new AdditionalMessage(MessageType.SUCCESS, "Delete Bus Success !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<List<Bus>> getBuss() {
        ControlMessage<List<Bus>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    SELECT [Sort] AS BusID, [Text] AS BusName, Info02 AS Fasilitas, CreatedDate, UserID\s
                    FROM JavaParawisataParam_TR
                    WHERE [Group] = 'Bus'
                    ORDER BY [Sort]
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            boolean result = pst.execute();

            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(Bus.class));
            if (result && !resultObj.isEmpty()){
                resultObj.get(0).forEach(x -> {
                    response.data.add((Bus) x);
                });
            } else response.messages.add(new AdditionalMessage(MessageType.WARNING, "Data Bus Tidak Ada !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<List<BusPrice>> getBusDetailsByID(String busID) {
        ControlMessage<List<BusPrice>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE @BusID INT = ?
                                        
                    IF (@BusID = 0)
                    	RAISERROR('Bus ID Not Null !', 16, 1)
                                        
                    SELECT ROW_NUMBER() OVER(ORDER BY a.[Sort]) AS ID, b.*
                    FROM JavaParawisataParam_TR a
                    JOIN BusPrice_TR b
                    ON a.[Value] = b.BusName
                    WHERE a.[Group] = 'Bus' AND a.[Sort] = @BusID
                    """;
            // </editor-folds>
            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(busID));
            boolean result = pst.execute();

            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(BusPrice.class));
            if (result && !resultObj.isEmpty()) {
                resultObj.get(0).forEach(x -> {
                    response.data.add((BusPrice) x);
                });
            } else response.messages.add(new AdditionalMessage(MessageType.WARNING, "Data Bus Price Tidak Ada !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<BusPrice> add(BusPrice data) {
        ControlMessage<BusPrice> response = new ControlMessage<>();
        response.data = new BusPrice();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    """;
            // </editor-folds>
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<BusPrice> update(BusPrice data) {
        ControlMessage<BusPrice> response = new ControlMessage<>();
        response.data = new BusPrice();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    """;
            // </editor-folds>
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<BusPrice> delete(BusPrice data) {
        ControlMessage<BusPrice> response = new ControlMessage<>();
        response.data = new BusPrice();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    """;
            // </editor-folds>
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
