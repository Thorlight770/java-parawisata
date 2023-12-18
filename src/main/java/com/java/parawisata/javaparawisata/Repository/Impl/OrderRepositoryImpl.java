package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Entity.HistoryOrder;
import com.java.parawisata.javaparawisata.Entity.Order;
import com.java.parawisata.javaparawisata.Entity.OrderApproval;
import com.java.parawisata.javaparawisata.Repository.IOrderRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements IOrderRepository {
    @Override
    public ControlMessage<Order> add(Order data) {
        ControlMessage<Order> response = new ControlMessage<>();
        response.data = new Order();
        response.isSuccess = true;
        try {
            // <editor-fold desc="query">
            String query = """
                    DECLARE
                    @OrderDate DATETIME,
                    @CustomerID VARCHAR(100),
                    @BusID VARCHAR(100),
                    @DriverID VARCHAR(100),
                    @PickUpPoint VARCHAR(100),
                    @Destination VARCHAR(100),
                    @Duration INT,
                    @Status BIT,
                    @FileName VARCHAR(100),
                    @ErrMsg VARCHAR(200)
                    
                    SET @OrderDate = ?
                    SET @CustomerID = ?
                    SET @BusID = ?
                    SET @DriverID = ?
                    SET @PickUpPoint = ?
                    SET @Destination = ?
                    SET @Duration = ?
                    SET @Status = ?
                    SET @FileName = ?
                    SET @ErrMsg = ''
                                        
                    -----------------------------------------------------------------------------
                    ----ORDER VALIDATE
                    -----------------------------------------------------------------------------
                    IF (ISNULL(@CustomerID, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Customer ID Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF (ISNULL(@BusID, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Bus ID Null !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF (ISNULL(@DriverID, '') = '')
                    BEGIN
                    	SET @ErrMsg = 'Driver ID Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF EXISTS (SELECT TOP 1 1 FROM OrderBusHist
                    			WHERE CustomerID = @CustomerID
                    			AND ISNULL(AdministratorID, '') = ''
                    			AND ISNULL(AuthStatus, '') = '')
                    BEGIN
                        SET @ErrMsg = 'CustomerID Masih Ada Pending Approval !'
                        RAISERROR(@ErrMsg, 16, 1)
                    END
                    -----------------------------------------------------------------------------
                    --ORDER PROCESS
                    -----------------------------------------------------------------------------
                    IF(ISNULL(@ErrMsg, '') = '')
                    BEGIN
                        INSERT INTO OrderBusHist
                        (OrderID, OrderDate, CustomerID, BusID, DriverID, PickUpPoint, Destination,
                        Duration, [Status], [FileName], CreatedDate)
                        SELECT NEWID(), @OrderDate, @CustomerID, @BusID, @DriverID, @PickUpPoint, @Destination,
                        @Duration, @Status, @FileName, GETDATE()
                                            
                        SELECT 'Order Success Need Approval !' AS MessageOrder
                    END
                    ELSE
                        RAISERROR(@ErrMsg, 16, 1)
                    """;
            // </editor-fold>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setDate(1, data.getOrderDate());
            pst.setString(2, data.getCustomerID());
            pst.setString(3, data.getBusID());
            pst.setString(4, data.getDriverID());
            pst.setString(5, data.getPickUpPoint());
            pst.setString(6, data.getDestination());
            pst.setInt(7, data.getDuration());
            pst.setBoolean(8, data.getStatus());
            pst.setString(9, data.getFileName());
            boolean result = pst.execute();

            response.messages.add(new AdditionalMessage(MessageType.SUCCESS, "Order Success Need Approval !"));
            response.isSuccess = true;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<Order> update(Order data) {
        return null;
    }

    @Override
    public ControlMessage<List<HistoryOrder>> getHistoryOrdersByUserID(String userID) {
        ControlMessage<List<HistoryOrder>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE @CustomerID VARCHAR(100) = ?
                                        
                    IF (ISNULL(@CustomerID, '') = '')
                    	RAISERROR('Customer ID Not Null !', 16, 1)
                                        
                    SELECT ROW_NUMBER() OVER(ORDER BY IDHist ASC) AS IDHist, a.OrderID, b.BusName, c.DriverName,
                    a.OrderDate, a.PickUpPoint, a.Destination,
                    CASE
                    	WHEN a.OrderDate < GETDATE() AND a.AuthStatus = 'A' THEN 'Done'
                    	WHEN a.OrderDate > GETDATE() AND a.AuthStatus = 'A' THEN 'On Schedule'
                    	WHEN a.AuthStatus = 'R' THEN 'Reject'
                    	WHEN ISNULL(a.AuthStatus, '') = '' THEN 'Pending Approval'
                    END AS [Status],
                    a.Reason
                    FROM OrderBusHist a
                    JOIN BusPrice_TR b
                    ON a.BusID = b.BusPriceID
                    JOIN DriverMs c
                    ON a.DriverID = c.DriverID
                    JOIN CustomerMs d
                    ON a.CustomerID = d.CustomerID
                    WHERE a.CustomerID = @CustomerID
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, userID);
            boolean result = pst.execute();

            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(HistoryOrder.class));
            if (result && !resultObj.isEmpty()) {
                resultObj.get(0).forEach(x -> {
                    response.data.add((HistoryOrder) x);
                });
            } else response.messages.add(new AdditionalMessage(MessageType.WARNING, "Data Order Tidak Di Temukan !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    // <editor-folds desc="Approval">
    @Override
    public ControlMessage<List<OrderApproval>> getAllOrderApproval() {
        ControlMessage<List<OrderApproval>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    SELECT a.OrderDate, a.IDHist, b.CustomerName, c.BusName, d.DriverName,
                         a.PickUpPoint, a.Destination, a.Duration,
                         CASE
                         	WHEN ISNULL(a.FileName, '') = '' THEN 'False'
                         	WHEN ISNULL(a.FileName, '') <> '' THEN 'Need Check !'
                         END AS StatusPayment,
                         a.[FileName]
                         FROM OrderBusHist a
                         JOIN CustomerMs b
                         ON a.CustomerID = b.CustomerID
                         JOIN BusPrice_TR c
                         ON a.BusID = c.BusPriceID
                         JOIN DriverMs d
                         ON a.DriverID = d.DriverID
                         WHERE ISNULL(a.AuthStatus, '') = ''
                         AND ISNULL(a.AdministratorID, '') = ''
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);

            boolean result = pst.execute();

            List<List<Object>> resultObj = DBConnection.MappingStatement(result, pst, List.of(OrderApproval.class));
            if (result && !resultObj.isEmpty()) {
                resultObj.get(0).forEach(x -> {
                    response.data.add((OrderApproval) x);
                });
            } else response.messages.add(new AdditionalMessage(MessageType.WARNING, "Data Approval Order Tidak Ada !"));
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }

    @Override
    public ControlMessage<OrderApproval> processOrderApproval(OrderApproval orderApproval, String status) {
        return null;
    }
    // </editor-folds>
}
