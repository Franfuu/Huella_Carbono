module com.github.Franfuu {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens com.github.Franfuu.model.entities to org.hibernate.orm.core;
    opens com.github.Franfuu.view to javafx.fxml;

    exports com.github.Franfuu;
    exports com.github.Franfuu.view;
}