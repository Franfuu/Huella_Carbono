module com.github.Franfuu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires mysql.connector.j;
    requires org.jfree.jfreechart;
    requires java.desktop;
    requires itextpdf;
    requires java.naming;

    opens com.github.Franfuu.view to javafx.fxml;
    exports com.github.Franfuu.view;

    opens com.github.Franfuu to javafx.fxml;
    opens com.github.Franfuu.model.entities to javafx.base, org.hibernate.orm.core;

    exports com.github.Franfuu;
    exports com.github.Franfuu.model.dao;
}