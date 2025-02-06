module com.github.Franfuu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires mysql.connector.j;

    opens com.github.Franfuu to javafx.fxml;
    opens com.github.Franfuu.model.entities to javafx.base, org.hibernate.orm.core;

    exports com.github.Franfuu;
    exports com.github.Franfuu.view;
    opens com.github.Franfuu.view to javafx.fxml;
}