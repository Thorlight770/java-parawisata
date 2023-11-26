module com.java.parawisata.javaparawisata {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires de.jensd.fx.glyphs.fontawesome;
    requires MaterialFX;
    requires java.common.lib;

    opens com.java.parawisata.javaparawisata to javafx.fxml;
    exports com.java.parawisata.javaparawisata;
    exports com.java.parawisata.javaparawisata.Controller;
    exports com.java.parawisata.javaparawisata.Utils.ControlMessage;
    exports com.java.parawisata.javaparawisata.Utils.Dialog;
    exports com.java.parawisata.javaparawisata.Utils.Database;
    exports com.java.parawisata.javaparawisata.Entity;
    exports com.java.parawisata.javaparawisata.Service;
    exports com.java.parawisata.javaparawisata.Repository;
}