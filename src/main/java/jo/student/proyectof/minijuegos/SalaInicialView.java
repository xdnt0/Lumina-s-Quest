package jo.student.proyectof.minijuegos;

/**
 *
 * @author Familia Betancourt
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import jo.student.proyectof.entidades.Lumina;

public class SalaInicialView {

    private Pane root;
    private Lumina lumina;

    private Rectangle puerta1, puerta2, puerta3; // Rectangulos invisibles para representar las puertas
    
    private Runnable onPuerta1, onPuerta2, onPuerta3; // Acciones que se eejcutan al tocar la puerta
    
    //Constructor
    public SalaInicialView(int width, int height) {
        root = new Pane();
        root.setPrefSize(width, height);
        inicializarVista();
    }

    private void inicializarVista() {
        // 1. Fondo
        Image fondo = new Image(getClass().getResourceAsStream("/images/salaPrincipal.png"));
        ImageView fondoView = new ImageView(fondo);
        root.getChildren().add(fondoView);

        // 2. Lumina
        lumina = new Lumina();
        lumina.getSprite().setLayoutX(200);
        lumina.getSprite().setLayoutY(800);
        root.getChildren().add(lumina.getSprite());

        // 3. Puerta invisible para los rectangulos
        puerta1 = new Rectangle(145, 150, 152, 73);
        puerta1.setOpacity(0.0);
        root.getChildren().add(puerta1);
        
        puerta2 = new Rectangle(800, 150, 140, 70);
        puerta2.setOpacity(0.0);
        root.getChildren().add(puerta2);

        puerta3 = new Rectangle(1380, 150, 140, 70); 
        puerta3.setOpacity(0.0);
        root.getChildren().add(puerta3);


        // 4. Paredes de la sala principal
        int[][] paredesData = {
            {0, 0, 1, 1080},
            {0, 960, 1920, 20},
            {1858, 0, 20, 1080},
            {0, 322, 145, 35},
            {110, 109, 36, 248},
            {110, 109, 220, 34},
            {290, 109, 40, 248},
            {290, 322, 510, 35},
            {764, 109, 36, 248},
            {764, 109, 216, 36},
            {940, 109, 40, 248},
            {940, 322, 438, 35},
            {1340, 109, 38, 248},
            {1340, 109, 224, 36},
            {1524, 109, 39, 248},
            {1524, 322, 396, 35},
        };
        
        //Agregar las paredes
        for (int[] datos : paredesData) {
            Rectangle pared = new Rectangle(datos[0], datos[1], datos[2], datos[3]); // x, y, Ancho, Alto
            pared.setFill(Color.TRANSPARENT); // invisible
            pared.setUserData("pared"); // sistema de colisión
            root.getChildren().add(pared);
        }
    }
    //Detectar si lumina toca alguna puerta
    public void detectarPuertas() {
        if (lumina.getSprite().getBoundsInParent().intersects(puerta1.getBoundsInParent())) {
            if (onPuerta1 != null) {
                javafx.application.Platform.runLater(onPuerta1);
            }
        }

        if (lumina.getSprite().getBoundsInParent().intersects(puerta2.getBoundsInParent())) {
            if (onPuerta2 != null) {
                javafx.application.Platform.runLater(onPuerta2);
            }
        }
        if (lumina.getSprite().getBoundsInParent().intersects(puerta3.getBoundsInParent())) {
        if (onPuerta3 != null) {
            javafx.application.Platform.runLater(onPuerta3);
            }
        }

    }

    //Metodos para acceder a elementos principales
    public Pane getRoot() {
        return root;
    }

    public Lumina getLumina() {
        return lumina;
    }
    public Rectangle getPuerta1() {
        return puerta1;
    }
    public Rectangle getPuerta2() {
        return puerta2;
    }
    public Rectangle getPuerta3() {
    return puerta3;
    }
    
    //Metodos para asignar acciones al tocar la puerta
    public void setOnPuerta1(Runnable r) {
        this.onPuerta1 = r;
    }

    public void setOnPuerta2(Runnable r) {
        this.onPuerta2 = r;
    }

    public void setOnPuerta3(Runnable r) {
        this.onPuerta3 = r;
    }
}
