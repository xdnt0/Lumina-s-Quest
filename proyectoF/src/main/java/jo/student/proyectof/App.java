package jo.student.proyectof;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    // Tamaño de la ventana
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane(); // Contenedor principal
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        
        Image personajeImg = new Image(getClass().getResourceAsStream("/resources/img/personaje.png"));
        ImageView personajeView = new ImageView(personajeImg);
        personajeView.setLayoutX(100);
        personajeView.setLayoutY(300);
        root.getChildren().add(personajeView);
        primaryStage.setTitle("Luz Roja");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
