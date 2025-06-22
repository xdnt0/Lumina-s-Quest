package jo.student.proyectof;
//librerias
import javafx.application.Application; //este es Application.java (hace parte de javaFX)
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jo.student.proyectof.entidades.Lumina;

public class Game extends Application {

    // Tamaño de la ventana
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        Lumina jugador = new Lumina();
        root.getChildren().add(jugador.getSprite());

        primaryStage.setTitle("Lumina's Quest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
