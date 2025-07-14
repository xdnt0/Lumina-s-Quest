package jo.student.proyectof;

// Librerías
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;

// Clases del juego
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Fragmentoalma;
import jo.student.proyectof.entidades.Moneda;
import jo.student.proyectof.utilidades.Controladores;
import jo.student.proyectof.interfaz.PantallaCarga;
import jo.student.proyectof.minijuegos.LaberintoView;
import jo.student.proyectof.minijuegos.SalaInicialView;

public class Game extends Application {

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private boolean fragmentoRecogido = false;
    private Scene scene;
    private SalaInicialView salaInicialView;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Mostrar pantalla de carga
        PantallaCarga pantalla = new PantallaCarga(primaryStage);
        pantalla.mostrar();

        // Tarea de carga en segundo plano
        Task<Void> tareaCarga = new Task<>() {
            @Override
            protected Void call() {
                cargarJuego(); // Aquí puedes precargar recursos
                return null;
            }

            @Override
            protected void succeeded() {
                mostrarJuego(primaryStage); // Mostrar juego luego de la carga
            }
        };

        new Thread(tareaCarga).start();
    }

    private void cargarJuego() {
        // Simula una carga lenta (puedes precargar imágenes, sonidos, etc.)
        try {
            Thread.sleep(1500); // 1.5 segundos de carga simulada
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void mostrarJuego(Stage primaryStage) {
        salaInicialView = new SalaInicialView(WIDTH, HEIGHT);
        scene = new Scene(salaInicialView.getRoot(), WIDTH, HEIGHT);

        // Acción para puerta 1
        salaInicialView.setOnPuerta1(() -> lanzarMinijuegoLaberinto(primaryStage));
        // Puedes configurar otras puertas aquí

        // Controles para Lumina
        Controladores controlesSala = new Controladores(
            salaInicialView.getLumina(),
            this::detectarColisionSalaInicial
        );
        controlesSala.configurarControles(scene);

        // Mostrar escena del juego en el hilo de JavaFX
        Platform.runLater(() -> {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lumina's Quest");
            primaryStage.setResizable(false);
            primaryStage.show();
        });
    }
    
    
private void mostrarPantallaCargaYDespues(Runnable tareaCarga, Runnable accionFinal) {
    PantallaCarga pantalla = new PantallaCarga(primaryStage);
    pantalla.mostrar();

    Task<Void> tarea = new Task<>() {
        @Override
        protected Void call() {
            tareaCarga.run(); // lo que se carga
            return null;
        }

        @Override
        protected void succeeded() {
            Platform.runLater(accionFinal); // lo que se hace al terminar
        }
    };

    new Thread(tarea).start();
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

        lumina.setColision(false);

        // Verifica si recoge el fragmento
        Fragmentoalma fragmento = laberintoView.getFragmentoalma();
        if (!fragmentoRecogido && fragmento != null &&
            lumina.getSprite().getBoundsInParent().intersects(fragmento.getSprite().getBoundsInParent())) {

            fragmentoRecogido = true;
            pane.getChildren().remove(fragmento.getSprite());
            System.out.println("Has recogido el Fragmento del Alma!");

            laberintoView.mostrarRobotCentinela(); // Aparece el robot
            laberintoView.marcarFragmentoRecogido(); // Comienza la persecución
        }
    }

    
   private void lanzarMinijuegoLaberinto(Stage primaryStage) {
    final LaberintoView[] laberintoViewRef = new LaberintoView[1]; // Para compartir entre hilos

    mostrarPantallaCargaYDespues(
        () -> { // tareaCarga (en segundo plano)
            // Aquí se prepara el contenido en memoria (sin mostrar)
            laberintoViewRef[0] = new LaberintoView(WIDTH, HEIGHT);
        },
        () -> { // accionFinal (en hilo de JavaFX)
            LaberintoView laberintoView = laberintoViewRef[0];
            Pane laberintoPane = laberintoView.getLaberintoPane();
            Scene nuevaScene = new Scene(laberintoPane, WIDTH, HEIGHT);

            Controladores controles = new Controladores(
                laberintoView.getLumina(),
                () -> detectarColisionesLaberinto(laberintoView)
            );
            controles.configurarControles(nuevaScene);

            primaryStage.setScene(nuevaScene);

            AnimationTimer gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    laberintoView.moverRobotPorCamino();
                    laberintoView.verificarColisionRobotLumina();
                }
            };
            gameLoop.start();
        }
    );
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
        salaInicialView.detectarPuertas(); // Verifica interacción con puertas
    }

    public static void main(String[] args) {
        launch(args);
    }
}
