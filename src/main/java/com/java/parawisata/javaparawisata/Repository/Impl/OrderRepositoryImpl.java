package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
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

    // <editor-folds desc="Approval">
    @Override
    public ControlMessage<List<OrderApproval>> getAllOrderApproval() {
        ControlMessage<List<OrderApproval>> response = new ControlMessage<>();
        response.data = new ArrayList<>();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    SELECT TOP 100 * FROM OrderHist a
                    JOIN CustomerMs b
                    ON a. 
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
    public ControlMessage<OrderApproval> processOrderApproval(OrderApproval orderApproval, String status) {
        return null;
    }
    // <editor-folds>
}
