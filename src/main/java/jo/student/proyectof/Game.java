package jo.student.proyectof;

// Librerías
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;
//clases del juego importadas
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Moneda;
import jo.student.proyectof.interfaz.Controladores;
import jo.student.proyectof.minijuegos.Laberinto;
import jo.student.proyectof.minijuegos.LaberintoView;

public class Game extends Application {

    // ----------------------------------------
    // CAMBIAR AQUÍ LA RESOLUCIÓN: Descomentando o comentando
    // ----------------------------------------
    
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    // private static final int WIDTH = 1280;
    // private static final int HEIGHT = 720;
    
    //
    private boolean fragmentoRecogido = false;

    @Override
    public void start(Stage primaryStage) {
        LaberintoView laberintoView = new LaberintoView(WIDTH, HEIGHT);
        Pane laberintoPane = laberintoView.getLaberintoPane();

        Scene scene = new Scene(laberintoPane, WIDTH, HEIGHT);

        // Controles del minijuego
        Controladores controles = new Controladores(
            laberintoView.getLumina(),
            () -> detectarColisionesLaberinto(laberintoView)
        );
        controles.configurarControles(scene);

        // Configuración ventana
        primaryStage.setScene(scene);
        primaryStage.setTitle("Minijuego - Laberinto");


        primaryStage.setResizable(false); // Para evitar redimensionamiento
        primaryStage.show();
    }

    private void detectarColisionesLaberinto(LaberintoView laberintoView) {
        Lumina lumina = laberintoView.getLumina();
        Pane pane = laberintoView.getLaberintoPane();

        for (var nodo : pane.getChildren()) {
            if (nodo instanceof Rectangle pared) {
                if (lumina.getSprite().getBoundsInParent().intersects(pared.getBoundsInParent())) {
                    lumina.setColision(true);
                    return; 
                }
            }
        }

        // Si no colisionó con ninguna
        lumina.setColision(false);
        
        
        
        // Detección con fragmento del alma
        var fragmento = laberintoView.getFragmentoalma();
        if (!fragmentoRecogido && fragmento != null &&
            lumina.getSprite().getBoundsInParent().intersects(fragmento.getSprite().getBoundsInParent())) {

            fragmentoRecogido = true; // marcar como recogido
            pane.getChildren().remove(fragmento.getSprite()); // quitar visualmente
            System.out.println("Has recogido el Fragmento del Alma!");
        }
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}