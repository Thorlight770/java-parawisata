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
                    
                    SELECT * FROM JavaParawisataParam_TR
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
                   response.data.add(GlobalParameter.class.cast(x));
                });
                response.isSuccess = true;
            } else response.isSuccess = false;
        } catch (Exception ex) {
            ex.printStackTrace();
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
                    SELECT DISTINCT a.[Destination] AS [Value], a.[Destination] AS [Text]
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
}