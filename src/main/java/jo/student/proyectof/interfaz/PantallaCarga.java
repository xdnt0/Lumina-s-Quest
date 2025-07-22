package jo.student.proyectof.interfaz;

/**
 *
 * @author johan
 */
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class PantallaCarga {

    private Stage stage;
    private Scene scene;

    public PantallaCarga(Stage stage) {
        this.stage = stage;

        // Cargar la fuente personalizada 
        Font fuente = Font.loadFont(
            getClass().getResourceAsStream("/fuentes/DepartureMono-Regular.otf"),
            30 // Tamaño 
        );

        // Mensaje 
        Label mensaje = new Label("Cargando...");
        mensaje.setFont(fuente);
        mensaje.setStyle("-fx-text-fill: white;");

        // Indicador de la rueda de carga
        ProgressIndicator loading = new ProgressIndicator();
        loading.setStyle("-fx-progress-color: white;");

        // Layout
        VBox root = new VBox(20, loading, mensaje); // Mostra el texto debajo de la rueda de carga
        root.setAlignment(Pos.CENTER); //Centrado en pantalla
        root.setStyle("-fx-background-color: black;"); //Fondo negro

        // Crear escena a pantalla completa
        scene = new Scene(root, 1920, 1080); 
    }

    public void mostrar() {
        stage.setScene(scene);
        stage.show();
    }
}
