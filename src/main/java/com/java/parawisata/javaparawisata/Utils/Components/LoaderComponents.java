package com.java.parawisata.javaparawisata.Utils.Components;

import javafx.scene.layout.AnchorPane;

public class LoaderComponents<T> {
    private AnchorPane anchorPane;
    private T controller;

    public LoaderComponents() {
    }

    public LoaderComponents(AnchorPane anchorPane, T controller) {
        this.anchorPane = anchorPane;
        this.controller = controller;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public T getController() {
        return controller;
    }

    public void setController(T controller) {
        this.controller = controller;
    }
}
