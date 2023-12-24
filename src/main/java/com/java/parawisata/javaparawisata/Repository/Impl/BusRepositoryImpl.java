package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.Bus;
import com.java.parawisata.javaparawisata.Entity.BusPrice;
import com.java.parawisata.javaparawisata.Repository.IBusRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class BusRepositoryImpl implements IBusRepository {
    @Override
    public ControlMessage<Bus> update(Bus data) {
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
