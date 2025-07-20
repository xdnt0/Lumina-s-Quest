package jo.student.proyectof;

// Librerías
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;

// Clases del juego
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Fragmentoalma;
import jo.student.proyectof.utilidades.Controladores;
import jo.student.proyectof.interfaz.PantallaCarga;
import jo.student.proyectof.minijuegos.CodigoSecretoView;
import jo.student.proyectof.minijuegos.LaberintoView;
import jo.student.proyectof.minijuegos.SalaInicialView;
import jo.student.proyectof.minijuegos.LaserRoomView;

public class Game extends Application {

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private Scene scene;
    private SalaInicialView salaInicialView;
    private boolean minijuegoLaberintoLanzado = false;
    private boolean fragmentoRecogido = false;
    private boolean minijuegoCodigoSecreto = false;
    private boolean minijuegoLaserRoom = false;
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
                cargarJuego(); // precargar recursos
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
        salaInicialView.setOnPuerta2(() -> cargarMinijuegoCodigoSecreto(primaryStage));
        salaInicialView.setOnPuerta3(() -> lanzarMinijuegoLaserRoom(primaryStage));

        // Controles para Lumina con detección automática de paredes
        Controladores controlesSala = new Controladores(
            salaInicialView.getLumina(),
            this::detectarColisionSalaInicial,
            salaInicialView.getRoot()
        );
        controlesSala.configurarControles(scene);

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
                tareaCarga.run();
                return null;
            }

            @Override
            protected void succeeded() {
                Platform.runLater(accionFinal);
            }
        };

        new Thread(tarea).start();
    }
    public void detectarColisionSalaInicial() {
    if (!minijuegoLaberintoLanzado &&
        salaInicialView.getLumina().getSprite().getBoundsInParent().intersects(
        salaInicialView.getPuerta1().getBoundsInParent())) {

        minijuegoLaberintoLanzado = true; // evita reentrada
        Platform.runLater(() -> lanzarMinijuegoLaberinto(primaryStage));
    }
    if (!minijuegoCodigoSecreto &&
        salaInicialView.getLumina().getSprite().getBoundsInParent().intersects(
        salaInicialView.getPuerta2().getBoundsInParent())) {
        minijuegoCodigoSecreto = true;
    Platform.runLater(() -> cargarMinijuegoCodigoSecreto(primaryStage));
    }
    if (!minijuegoLaserRoom &&
        salaInicialView.getLumina().getSprite().getBoundsInParent().intersects(
        salaInicialView.getPuerta3().getBoundsInParent())) {
        minijuegoLaserRoom = true;
        Platform.runLater(() -> lanzarMinijuegoLaserRoom(primaryStage));
    }

 }
    private void lanzarMinijuegoLaberinto(Stage primaryStage) {
        final LaberintoView[] laberintoViewRef = new LaberintoView[1];

        mostrarPantallaCargaYDespues(
            () -> {
                laberintoViewRef[0] = new LaberintoView(WIDTH, HEIGHT);
            },
            () -> {
                LaberintoView laberintoView = laberintoViewRef[0];
                Pane laberintoPane = laberintoView.getLaberintoPane();
                Scene nuevaScene = new Scene(laberintoPane, WIDTH, HEIGHT);

                // Controlador con detección automática de paredes
                Controladores controles = new Controladores(
                    laberintoView.getLumina(),
                    () -> detectarColisionesLaberinto(laberintoView),
                    laberintoPane
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

    private void detectarColisionesLaberinto(LaberintoView laberintoView) {
        Lumina lumina = laberintoView.getLumina();
        Pane pane = laberintoView.getLaberintoPane();

        // Verifica si recoge el fragmento (las paredes ya se manejan en Controladores)
        Fragmentoalma fragmento = laberintoView.getFragmentoalma();
        if (!fragmentoRecogido && fragmento != null &&
            lumina.getSprite().getBoundsInParent().intersects(fragmento.getSprite().getBoundsInParent())) {

            fragmentoRecogido = true;
            pane.getChildren().remove(fragmento.getSprite());
            System.out.println("Has recogido el Fragmento del Alma!");

            laberintoView.mostrarRobotCentinela();
            laberintoView.marcarFragmentoRecogido();
        }
    }

        private void cargarMinijuegoCodigoSecreto(Stage stage) {
        PantallaCarga carga = new PantallaCarga(stage);
        carga.mostrar();

        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            CodigoSecretoView vista = new CodigoSecretoView(WIDTH, HEIGHT);
            Platform.runLater(() -> {
                Scene escena = new Scene(vista.getRoot());
                Controladores control = new Controladores(vista.getLumina(), vista::InteraccionLibrosPinpad, vista.getRoot());
                control.configurarControles(escena);
                stage.setScene(escena);
                stage.show();
            });
        }).start();
    }
private void lanzarMinijuegoLaserRoom(Stage stage) {
    PantallaCarga carga = new PantallaCarga(stage);
    carga.mostrar();

    new Thread(() -> {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            LaserRoomView vista = new LaserRoomView(stage);
            // La clase LaserRoomView ya hace stage.setScene(...) internamente.
            // Por eso NO necesitamos crear otra Scene ni agregar Controladores aquí.
        });
    }).start();
}


    public static void main(String[] args) {
        launch(args);
    }
}
