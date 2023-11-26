package com.java.parawisata.javaparawisata.Utils.ControlMessage;

public enum MessageType {
    SUCCESS (1,"SUCCESS"),
    WARNING (2, "WARNING"),
    NEED_APPROVAL (3, "NEED APPROVAL"),
    VALIDATE (4, "VALIDATE"),
    ERROR (4, "ERROR"),
    DEFAULT (-1, "DEFAULT");

    private final int value;
    private final String name;

    MessageType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int compare(MessageType that) {
        return Integer.compare(this.value, that.value);
    }
}
