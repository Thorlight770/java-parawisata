module com.java.parawisata.javaparawisata {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.java.parawisata.javaparawisata to javafx.fxml;
    exports com.java.parawisata.javaparawisata;
}