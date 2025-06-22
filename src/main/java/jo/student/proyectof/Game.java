package jo.student.proyectof;

// Librerías
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//clases del juego importadas
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Moneda;
import jo.student.proyectof.interfaz.Controladores;
import jo.student.proyectof.minijuegos.Laberinto;

public class Game extends Application {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private Lumina lumina;
    private final List<Moneda> monedas = new ArrayList<>();
    private Pane root;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Crear al personaje
        lumina = new Lumina(); 
        root.getChildren().add(lumina.getSprite());

        // Crear monedas
        for (int i = 0; i < 5; i++) {
            Moneda m = new Moneda(100 + i * 60, 400);//arreglo para generar monedas,
            monedas.add(m);                          //se debe corregir cuando se implemente en los minijuegos
            root.getChildren().add(m.getSprite());
        }

        // Crear controles y la detección de colisiones
        Controladores controles = new Controladores(lumina, this::detectarColisiones);
        controles.configurarControles(scene);

        primaryStage.setTitle("Lumina's Quest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Llamado después del movimiento de Lumina
    private void detectarColisiones() {
        Iterator<Moneda> it = monedas.iterator();
        while (it.hasNext()) {
            Moneda moneda = it.next();
            if (lumina.getBounds().intersects(moneda.getBounds().getBoundsInLocal())) {
                lumina.colision(moneda);
                root.getChildren().remove(moneda.getSprite());
                it.remove();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
