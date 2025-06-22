package jo.student.proyectof;

// Librerías
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Moneda;
import jo.student.proyectof.interfaz.Controladores;

public class Game extends Application {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 720;

    private Lumina lumina;
    private final List<Moneda> monedas = new ArrayList<>();
    private Pane root;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Crear al jugador
        lumina = new Lumina(); // ← usar el atributo de clase
        root.getChildren().add(lumina.getSprite());

        // Crear monedas
        for (int i = 0; i < 5; i++) {
            Moneda m = new Moneda(100 + i * 60, 100);
            monedas.add(m);
            root.getChildren().add(m.getSprite());
        }

        // Crear controles con detección de colisiones
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
