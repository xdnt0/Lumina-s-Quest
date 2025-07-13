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

        // Cargar la fuente personalizada desde resources/fuentes
        Font fuente = Font.loadFont(
            getClass().getResourceAsStream("/fuentes/DepartureMono-Regular.otf"),
            30 // Tamaño base
        );

        // Mensaje visible
        Label mensaje = new Label("Cargando...");
        mensaje.setFont(fuente);
        mensaje.setStyle("-fx-text-fill: white;");

        // Indicador de carga giratorio
        ProgressIndicator loading = new ProgressIndicator();
        loading.setStyle("-fx-progress-color: white;");

        // Layout
        VBox root = new VBox(20, loading, mensaje);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");

        // Crear escena
        scene = new Scene(root, 1920, 1080); // Ajusta al tamaño real del juego
    }

    public void mostrar() {
        stage.setScene(scene);
        stage.show();
    }
}
