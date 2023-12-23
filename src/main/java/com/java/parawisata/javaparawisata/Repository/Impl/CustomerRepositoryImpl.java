package com.java.parawisata.javaparawisata.Repository.Impl;

import com.java.parawisata.javaparawisata.Entity.Customer;
import com.java.parawisata.javaparawisata.Entity.SignUp;
import com.java.parawisata.javaparawisata.Repository.ICustomerRepository;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

public class CustomerRepositoryImpl implements ICustomerRepository {
    public CustomerRepositoryImpl() {
    }

    @Override
    public ControlMessage<Customer> update(Customer data) {
        return null;
    }

    @Override
    public boolean delete(Customer data) {
        return false;
    }

    @Override
    public ControlMessage<SignUp> add(SignUp data) {
        ControlMessage<SignUp> response = new ControlMessage<>();
        response.data = new SignUp();
        response.isSuccess = true;
        try {
            // <editor-folds desc="query">
            String query = """
                    DECLARE
                    @CustomerID VARCHAR(100),
                    @Name VARCHAR(100),
                    @IdentityType VARCHAR(100),
                    @IdentityID VARCHAR(100),
                    @Email VARCHAR(100),
                    @Username VARCHAR(100),
                    @Password VARCHAR(100),
                    @ErrMsg VARCHAR(100)
                                        
                    SET @CustomerID = NEWID()
                    SET @Name = ?
                    SET @IdentityType = ?
                    SET @IdentityID = ?
                    SET @Email = ?
                    SET @Username = ?
                    SET @Password = ?
                    SET @ErrMsg = ''
                                        
                    ----------------------------------------------------------
                    --VALIDATION
                    ----------------------------------------------------------
                    IF (ISNULL(@Name, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Name Not Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                   
                    IF (ISNULL(@IdentityType, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Identity Type Not Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF (ISNULL(@IdentityID, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Identity ID Not Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF (ISNULL(@Email, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Email Not Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF (ISNULL(@Username, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Username Not Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                   
                    IF (ISNULL(@Password, '') = '')
                    BEGIN
                        SET @ErrMsg = 'Password Not Null !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                                        
                    IF EXISTS (SELECT TOP 1 1 FROM AuthenticateMs WHERE Username = @Username)
                    BEGIN
                        SET @ErrMsg = 'Username Already Exists !'
                    	RAISERROR(@ErrMsg, 16, 1)
                    END
                    ----------------------------------------------------------
                    --PROCESS
                    ----------------------------------------------------------
                    IF (ISNULL(@ErrMsg, '') = '')
                    BEGIN
                        INSERT INTO CustomerMs (CustomerID, CustomerName, IdentityType, IdentityID, Email, CreatedDate)
                        SELECT @CustomerID, @Name, @IdentityType, @IdentityID, @Email, GETDATE()
                                            
                        INSERT INTO AuthenticateMs (CustomerID, Username, [Password], [Role], CreatedDate)
                        SELECT CustomerID, @Username, @Password, 'Customer', GETDATE()
                        FROM CustomerMs WHERE CustomerID = @CustomerID
                    END
                    """;
            // </editor-folds>

            Connection connection = DBConnection.GetConnection();
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, data.getName());
            pst.setString(2, data.getIdentityType());
            pst.setString(3, data.getIdentityID());
            pst.setString(4, data.getEmail());
            pst.setString(5, data.getUsername());
            pst.setString(6, data.getPassword());

            boolean result = pst.execute();

            if (result) response.data = data;
        } catch (Exception ex) {
            response.isSuccess = false;
            response.data = null;
            response.messages.add(new AdditionalMessage(MessageType.ERROR, ex.getMessage()));
        }
        return response;
    }
}
