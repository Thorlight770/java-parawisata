package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParamRepositoryImpl implements IParamRepository {
    @Override
    public ControlMessage<List<GlobalParameter>> InquiryGlobalParam(GlobalParameter data) {
        ControlMessage<List<GlobalParameter>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        try {
            // <editor-fold desc="query">
            String query = """
                    DECLARE @Group VARCHAR(100),
                    @Criteria VARCHAR(100),
                    @Value VARCHAR(100)
                    
                    SET @Group = ?
                    SET @Criteria = ?
                    SET @Value = ''
                    
                    IF NOT EXISTS (SELECT TOP 1 1 FROM JavaParawisataParam_TR
                                   WHERE (ISNULL(@Group, '') = '' OR @Group = [Group])
                                   AND (ISNULL(@Criteria, '') = '' OR @Criteria = Criteria))
                    BEGIN
                        RAISERROR('Parameter Not Exists !', 16, 1)
                    END
                    
                    SELECT [Group] AS [group], Criteria AS criteria, [value] AS value, [Text] AS [text],
                    Info01 AS info01, Info02 AS info02, [Sort] AS sort
                    FROM JavaParawisataParam_TR
                    WHERE (ISNULL(@Group, '') = '' OR @Group = [Group])
                    AND (ISNULL(@Criteria, '') = '' OR @Criteria = Criteria)
                    ORDER BY [Criteria], [Sort]
                    """;
            // </editor-fold>
            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, data.getGroup());
            pst.setString(2, data.getCriteria());
            boolean result = pst.execute();
            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(GlobalParameter.class));

            if (result && !resultObj.isEmpty()) {
                resultObj.get(0).forEach(x -> {
                   response.data.add((GlobalParameter) x);
                });
                response.isSuccess = true;
            } else response.isSuccess = false;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<List<GlobalParameter>> InquiryDestinationParam(GlobalParameter data) {
        ControlMessage<List<GlobalParameter>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        try {
            // <editor-fold desc="Query">
            String query = """
                    SELECT DISTINCT a.[Destination] AS [value], a.[Destination] AS [text], a.Duration AS info01
                    FROM BusPrice_TR a
                    """;
            // </editor-fold>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);

            boolean result = pst.execute();
            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(GlobalParameter.class));

            if (result && !resultObj.isEmpty()) {
                resultObj.get(0).forEach(x -> {
                    response.data.add(GlobalParameter.class.cast(x));
                });
                response.isSuccess = true;
            } else response.isSuccess = false;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<List<GlobalParameter>> InquiryBusListParam(GlobalParameter data) {
        ControlMessage<List<GlobalParameter>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE @Criteria VARCHAR(100)
                    SET @Criteria = ?
                    
                    IF (ISNULL(@Criteria, '') = '')
                    BEGIN
                        RAISERROR('Invalid Parameter !', 16, 1)
                    END
                    
                    SET @Criteria = CONCAT('%', @Criteria, '%')
                                     
                    SELECT a.BusPriceID AS [Value], b.[Value] AS [Text], a.Price AS Info01, b.Info02
                    FROM BusPrice_TR a
                    JOIN JavaParawisataParam_TR b
                    ON a.BusName = b.[Value]
                    WHERE (ISNULL(@Criteria, '') = '' OR a.Destination LIKE @Criteria)
                    ORDER BY b.[Sort]
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, data.getCriteria());
            boolean result = pst.execute();
            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(GlobalParameter.class));

            if (result && !resultObj.isEmpty()) {
                resultObj.get(0).forEach(x -> {
                    response.data.add((GlobalParameter) x);
                });
                response.isSuccess = true;
            } else response.isSuccess = false;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
