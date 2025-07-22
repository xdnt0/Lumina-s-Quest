package jo.student.proyectof.minijuegos;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Laser;
import jo.student.proyectof.entidades.Fragmentoalma;
import jo.student.proyectof.utilidades.Controladores;

import java.util.ArrayList;
import java.util.List;

public class LaserRoomView {

    private boolean fragmentoRecogido = false;
    private boolean salidaActivada = false;
    private Pane root;
    private Lumina lumina;
    private List<Laser> lasers = new ArrayList<>();
    private List<ImageView> corazones = new ArrayList<>();
    private Fragmentoalma fragmentoalma;
    private Controladores control;
    private Rectangle zonaSalida;
    private Runnable onSalida;
    private AnimationTimer loop;

    public LaserRoomView(Stage stage) {
        root = new Pane();
        root.setPrefSize(1920, 1080);

        inicializarFondo();
        inicializarParedes();
        inicializarLumina();
        inicializarLasers();
        inicializarFragmento();
        inicializarCorazones();

        Scene escena = new Scene(root);
        control = new Controladores(lumina, this::verificarEventos, root);
        control.configurarControles(escena);

        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                actualizarLasers();
                verificarColisionLasers();
                detectarSalida();
            }
        };
        loop.start();
        stage.setScene(escena);
        stage.show();
    }

    private void inicializarFondo() {
        Image fondo = new Image(getClass().getResourceAsStream("/images/minijuegos/3/LaserRoom.png"));
        ImageView fondoView = new ImageView(fondo);
        root.getChildren().add(fondoView);
    }

    private void inicializarParedes() {
        int[][] paredesData = {
            {0, 424, 147, 2}, {143, 172, 4, 252}, {143, 172, 213, 4}, {352, 172, 4, 252}, {352, 424, 806, 2},
            {1154, 90, 4, 334}, {1154, 86, 682, 4}, {1836, 90, 4, 990}, {0, 1080, 1920, 2}, {0, 0, 2, 1080},
        };

        for (int[] datos : paredesData) {
            Rectangle pared = new Rectangle(datos[2], datos[3]);
            pared.setX(datos[0]);
            pared.setY(datos[1]);
            pared.setFill(Color.rgb(0, 0, 0, 0.6));
            pared.setUserData("pared");
            root.getChildren().add(pared);
        }

        zonaSalida = new Rectangle(202, 60);
        zonaSalida.setX(149);
        zonaSalida.setY(210);
        zonaSalida.setFill(Color.TRANSPARENT);
        zonaSalida.setUserData("salida");
        zonaSalida.setVisible(false);
        root.getChildren().add(zonaSalida);
    }

    private void inicializarLumina() {
        lumina = new Lumina();
        lumina.getSprite().setLayoutX(80);
        lumina.getSprite().setLayoutY(580);
        root.getChildren().add(lumina.getSprite());
    }

    private void inicializarLasers() {
        Image imgActiva = new Image(getClass().getResourceAsStream("/images/laser.png"));
        Image imgInactiva = new Image(getClass().getResourceAsStream("/images/laser.png"));

        Laser laser1 = new Laser(900, 430, imgActiva, imgInactiva);
        Laser laser2 = new Laser(580, 430, imgActiva, imgInactiva);
        Laser laser3 = new Laser(750, 1000, imgActiva, imgInactiva);
        Laser laser4 = new Laser(1050, 1000, imgActiva, imgInactiva);

        lasers.add(laser1);
        lasers.add(laser2);
        lasers.add(laser3);
        lasers.add(laser4);

            // Asumimos que laser1 y 2 van hacia abajo, laser3 y 4 hacia arriba
        laser1.configurarHazVertical(600, false); // hacia abajo
        laser2.configurarHazVertical(600, false); // hacia abajo
        laser3.configurarHazVertical(580, true);  // hacia arriba
        laser4.configurarHazVertical(580, true);  // hacia arriba

        // Agregar todos al root (en orden visual)
        for (Laser laser : lasers) {
            root.getChildren().add(laser.getHaz());     // el haz primero
            root.getChildren().add(laser.getSprite());  // sprite encima
            root.getChildren().add(laser.getHitbox());  // colisión
        }

    }

    private void inicializarFragmento() {
        fragmentoalma = new Fragmentoalma(1440, 625);
        root.getChildren().add(fragmentoalma.getSprite());
    }

    public void marcarFragmentoRecogido() {
        if (fragmentoalma != null) {
            fragmentoalma.setRecogido(true);
            fragmentoRecogido = true;
        }
    }

    private void inicializarCorazones() {
        Image corazonImg = new Image(getClass().getResourceAsStream("/images/corazon.png"));
        for (int i = 0; i < lumina.getVidas(); i++) {
            ImageView corazon = new ImageView(corazonImg);
            corazon.setFitWidth(40);
            corazon.setFitHeight(40);
            corazon.setX(20 + i * 50);
            corazon.setY(20);
            corazones.add(corazon);
            root.getChildren().add(corazon);
        }
    }

    private void actualizarCorazones() {
        for (int i = 0; i < corazones.size(); i++) {
            corazones.get(i).setVisible(i < lumina.getVidas());
        }
    }

    private void actualizarLasers() {
        lasers.forEach(Laser::actualizar);
    }

        private void verificarColisionLasers() {
        for (Laser laser : lasers) {
            if (laser.estaActivo()) {
                // Colisión con sprite del láser (base)
                if (laser.getSprite().getBoundsInParent().intersects(lumina.getSprite().getBoundsInParent())) {
                    if (!lumina.estaInvulnerable()) {
                        lumina.perderVida();
                        actualizarCorazones();
                        laser.desactivarTemporalmente(); // ahora también el sprite es pared
                    }
                }

                // Colisión con el haz del láser
                if (laser.getHaz().isVisible() &&
                    laser.getHaz().getBoundsInParent().intersects(lumina.getSprite().getBoundsInParent())) {
                    if (!lumina.estaInvulnerable()) {
                        lumina.perderVida();
                        actualizarCorazones();
                        laser.desactivarTemporalmente(); // evita daño repetido
                    }
                }
            }
        }
    }

    private void detectarSalida() {
        if (fragmentoalma.isRecogido() &&
            lumina.getSprite().getBoundsInParent().intersects(zonaSalida.getBoundsInParent())) {
            if (onSalida != null) {
                loop.stop();
                onSalida.run();
            }
        }
    }

    private void verificarEventos() {
        if (!fragmentoalma.isRecogido() &&
            lumina.getSprite().getBoundsInParent().intersects(fragmentoalma.getSprite().getBoundsInParent())) {
            fragmentoalma.setRecogido(true);
            root.getChildren().remove(fragmentoalma.getSprite());
            zonaSalida.setVisible(true);
            System.out.println("Lúmina obtenida");
        }

        if (!salidaActivada &&
            fragmentoalma.isRecogido() &&
            zonaSalida != null &&
            lumina.getSprite().getBoundsInParent().intersects(zonaSalida.getBoundsInParent())) {
            salidaActivada = true;
            if (onSalida != null) {
                loop.stop();
                onSalida.run();
            }
        }
    }

    public void setOnSalida(Runnable onSalida) {
        this.onSalida = onSalida;
    }

    public Scene getScene() {
        return root.getScene();
    }

    public Lumina getLumina() {
        return lumina;
    }

    public Pane getRoot() {
        return root;
    }
}
