module de.fhkiel.fotomanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.crypto;
    requires static lombok;
    requires vavr;
    requires java.rmi;
    requires java.desktop;
    requires javafx.media;
    requires java.sql;
    requires metadata.extractor;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.databind;
    requires jcodec;

    opens de.fhkiel.fotomanager to javafx.fxml;
    exports de.fhkiel.fotomanager;
}