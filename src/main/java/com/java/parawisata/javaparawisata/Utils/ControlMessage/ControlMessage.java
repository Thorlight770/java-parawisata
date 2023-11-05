package com.java.parawisata.javaparawisata.Utils.ControlMessage;

import java.util.ArrayList;
import java.util.List;

public class ControlMessage<T> {
    public T data;
    public String TransactionGUID;
    public boolean isSuccess;
    public String userID;
    public List<AdditionalMessage> messages;

    public ControlMessage() {
        this.messages = new ArrayList<>();
        this.isSuccess = false;
        this.TransactionGUID = "";
        this.data = null;
        this.userID = "";
    }

    public ControlMessage(T data, String transactionGUID, boolean isSuccess, String userID, List<AdditionalMessage> messages) {
        this.data = data;
        TransactionGUID = transactionGUID;
        this.isSuccess = isSuccess;
        this.userID = userID;
        this.messages = messages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTransactionGUID() {
        return TransactionGUID;
    }

    public void setTransactionGUID(String transactionGUID) {
        TransactionGUID = transactionGUID;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<AdditionalMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<AdditionalMessage> messages) {
        this.messages = messages;
    }
}
