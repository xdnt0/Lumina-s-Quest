package jo.student.proyectof;

// Librerías
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.animation.AnimationTimer;

//clases del juego importadas

import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Moneda;
import jo.student.proyectof.interfaz.Controladores;
import jo.student.proyectof.minijuegos.Laberinto;
import jo.student.proyectof.minijuegos.LaberintoView;
import jo.student.proyectof.minijuegos.SalaInicialView;
import jo.student.proyectof.entidades.Fragmentoalma;
import javafx.animation.Timeline;

public class Game extends Application {
    
    

    // ----------------------------------------
    // CAMBIAR AQUÍ LA RESOLUCIÓN: Descomentando o comentando
    // ----------------------------------------
    
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    // private static final int WIDTH = 1280;
    // private static final int HEIGHT = 720;
    
    private boolean fragmentoRecogido = false;
    private Scene scene;
    private SalaInicialView salaInicialView;
    private Stage primaryStage; // Guardar para poder cambiar escena más tarde
    

    @Override
    
    public void start(Stage primaryStage) {
        // Inicializar sala inicial
        salaInicialView = new SalaInicialView(WIDTH, HEIGHT);
        scene = new Scene(salaInicialView.getRoot(), WIDTH, HEIGHT);

        // Configurar acciones al entrar en cada puerta
        salaInicialView.setOnPuerta1(() -> lanzarMinijuegoLaberinto(primaryStage));
        // salaInicialView.setOnPuerta2(() -> ... );
        // salaInicialView.setOnPuerta3(() -> ... );

        // Controles para mover a Lumina
        Controladores controlesSala = new Controladores(
            salaInicialView.getLumina(),
            () -> detectarColisionSalaInicial()
        );
        controlesSala.configurarControles(scene);

        // Configurar ventana
        primaryStage.setTitle("Luminas quest");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        this.primaryStage = primaryStage;
    }
    
    
    private void lanzarMinijuegoLaberinto(Stage primaryStage) {
        LaberintoView laberintoView = new LaberintoView(WIDTH, HEIGHT);
        Pane laberintoPane = laberintoView.getLaberintoPane();

        Scene nuevaScene = new Scene(laberintoPane, WIDTH, HEIGHT);

        // Reutiliza controladores con verificación de colisión con paredes
        Controladores controles = new Controladores(
            laberintoView.getLumina(),
            () -> detectarColisionesLaberinto(laberintoView)
        );
        controles.configurarControles(nuevaScene);

        // Mostrar la nueva escena
        primaryStage.setScene(nuevaScene);

        // Bucle del minijuego
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                laberintoView.moverRobotPorCamino();
                laberintoView.verificarColisionRobotLumina();
            }
        };
        gameLoop.start();
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
        Fragmentoalma fragmento = laberintoView.getFragmentoalma();
        if (!fragmentoRecogido && fragmento != null &&
            lumina.getSprite().getBoundsInParent().intersects(fragmento.getSprite().getBoundsInParent())) {

            fragmentoRecogido = true;
            pane.getChildren().remove(fragmento.getSprite());
            System.out.println("Has recogido el Fragmento del Alma!");

            laberintoView.mostrarRobotCentinela(); // Aparece el robot
            laberintoView.marcarFragmentoRecogido(); // ← Notifica que debe empezar a moverse
        }
        
        
    }
    
    
    private void detectarColisionSalaInicial() {
        Lumina lumina = salaInicialView.getLumina();

        for (Rectangle pared : salaInicialView.getParedes()) {
            if (lumina.getSprite().getBoundsInParent().intersects(pared.getBoundsInParent())) {
                lumina.setColision(true);
                return;
            }
        }

        lumina.setColision(false);

        // Además, verifica si entra a la puerta
        salaInicialView.detectarPuertas();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}