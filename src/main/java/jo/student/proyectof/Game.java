package jo.student.proyectof;
//librerias
import javafx.application.Application; //este es Application.java (hace parte de javaFX)
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application {

    // Tamaño de la ventana
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 720;

    @Override //marca que "Game" es una extension de Application.java
public void start(Stage primaryStage) {
    Pane root = new Pane(); // Contenedor principal
    Scene scene = new Scene(root, WIDTH, HEIGHT);

    // Crea e inserta el personaje desde "Entidades"
    Entidades entidades = new Entidades();
    ImageView personaje = entidades.crearPersonaje();
    root.getChildren().add(personaje);

    primaryStage.setTitle("Lumina's Quest");//titulo ventana
    primaryStage.setScene(scene);
    primaryStage.show();
}


    public static void main(String[] args) {
        launch(args); //llama a start desde Application.java
    }
}
