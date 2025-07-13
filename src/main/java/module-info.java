module jo.student.proyectof {
    requires javafx.controls;
    requires javafx.fxml;


    opens jo.student.proyectof to javafx.fxml;
    exports jo.student.proyectof;
    exports jo.student.proyectof.entidades;
}