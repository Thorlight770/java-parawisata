package com.java.parawisata.javaparawisata.Utils.ControlMessage;

public class AdditionalMessage {
    public MessageType type;
    public String Message;

    public AdditionalMessage() {
    }

    public AdditionalMessage(MessageType type, String message) {
        this.type = type;
        Message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
