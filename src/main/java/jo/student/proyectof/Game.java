package jo.student.proyectof;

// Librerias
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCombination;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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

        // Inicializar Stage sin bordes y full-screen real
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setFullScreen(true);

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

    private void mostrarJuego(Stage stage) {
        salaInicialView = new SalaInicialView(WIDTH, HEIGHT);
        scene = new Scene(salaInicialView.getRoot(), WIDTH, HEIGHT);

        // Acciones para puertas
        salaInicialView.setOnPuerta1(() -> lanzarMinijuegoLaberinto(stage));
        salaInicialView.setOnPuerta2(() -> cargarMinijuegoCodigoSecreto(stage));
        salaInicialView.setOnPuerta3(() -> lanzarMinijuegoLaserRoom(stage));

        // Controles para Lumina con deteccion de paredes
        Controladores controlesSala = new Controladores(
            salaInicialView.getLumina(),
            this::detectarColisionSalaInicial,
            salaInicialView.getRoot()
        );
        controlesSala.configurarControles(scene);

        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.setTitle("Lumina's Quest");
            applyFullscreen(stage);
            stage.show();
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

            minijuegoLaberintoLanzado = true;
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

    private void lanzarMinijuegoLaberinto(Stage stage) {
        final LaberintoView[] laberintoViewRef = new LaberintoView[1];

        mostrarPantallaCargaYDespues(
            () -> laberintoViewRef[0] = new LaberintoView(WIDTH, HEIGHT),
            () -> {
                LaberintoView laberintoView = laberintoViewRef[0];
                Pane laberintoPane = laberintoView.getLaberintoPane();
                Scene nuevaScene = new Scene(laberintoPane, WIDTH, HEIGHT);

                Controladores controles = new Controladores(
                    laberintoView.getLumina(),
                    () -> detectarColisionesLaberinto(laberintoView),
                    laberintoPane
                );
                controles.configurarControles(nuevaScene);

                Platform.runLater(() -> {
                    stage.setScene(nuevaScene);
                    applyFullscreen(stage);
                });

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
                Scene escena = new Scene(vista.getRoot(), WIDTH, HEIGHT);
                Controladores control = new Controladores(vista.getLumina(), vista::InteraccionLibrosPinpad, vista.getRoot());
                control.configurarControles(escena);
                stage.setScene(escena);
                applyFullscreen(stage);
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
                // LaserRoomView configura y muestra su escena
                applyFullscreen(stage);
            });
        }).start();
    }

    // Metodo utilitario para forzar fullscreen y always on top
    private void applyFullscreen(Stage stage) {
        stage.setFullScreen(true);
        stage.setAlwaysOnTop(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
