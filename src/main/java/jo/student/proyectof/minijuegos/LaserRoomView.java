package jo.student.proyectof.minijuegos;

/**
 *
 * @author johan
 */
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

    private Pane root;
    private Lumina lumina;
    private List<Laser> lasers = new ArrayList<>();
    private List<ImageView> corazones = new ArrayList<>();
    private Fragmentoalma fragmentoalma;
    private Controladores control;

    private AnimationTimer loop;

    public LaserRoomView(Stage stage) {
        root = new Pane();
        root.setPrefSize(1920, 1080);

        inicializarFondo();
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

    private void inicializarLumina() {
        lumina = new Lumina();
        lumina.getSprite().setLayoutX(100);
        lumina.getSprite().setLayoutY(400);
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

    // Agregar cada láser y su hitbox al root
    for (Laser laser : lasers) {
        root.getChildren().add(laser.getSprite());
        root.getChildren().add(laser.getHitbox());
        }
    }


    private void inicializarFragmento() {
        fragmentoalma = new Fragmentoalma(1440, 625);
        root.getChildren().add(fragmentoalma.getSprite());
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
            if (laser.estaActivo() && laser.getSprite().getBoundsInParent().intersects(lumina.getSprite().getBoundsInParent())) {
                if (!lumina.estaInvulnerable()) {
                    lumina.perderVida();
                    actualizarCorazones();
                }
            }
        }
    }

    private void verificarEventos() {
        if (!fragmentoalma.isRecogido() &&
            lumina.getSprite().getBoundsInParent().intersects(fragmentoalma.getSprite().getBoundsInParent())) {
            fragmentoalma.setRecogido(true);
            root.getChildren().remove(fragmentoalma.getSprite());
            System.out.println("Lúmina obtenida");
        }
    }
    public Lumina getLumina() {
        return lumina;
    }

    public Pane getRoot() {
        return root;
    }
}
