package jo.student.proyectof.utilidades;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jo.student.proyectof.entidades.Lumina;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.Node;//para identificar colisiones
import javafx.scene.layout.Pane;//donde se dibuja el juego

public class Controladores {

    private final Lumina lumina;
    private final Runnable onMovimiento;
    private final Set<KeyCode> teclasPresionadas = new HashSet<>();
    private AnimationTimer animador;
    private final double velocidad = 3; // Puedes cambiarlo para mayor o menor fluidez

   private final List<Node> paredes = new ArrayList<>();

public Controladores(Lumina lumina, Runnable onMovimiento, Pane root) {
    this.lumina = lumina;
    this.onMovimiento = onMovimiento;

    // Buscar todas las paredes automáticamente dentro del root(pantalla)
        for (Node nodo : root.getChildren()) {
        boolean esPared = false;

        if ("pared".equals(nodo.getUserData())) {
            esPared = true;
        } else if (nodo.getStyleClass().contains("pared")) {
            esPared = true;
        }

        if (esPared) {
            paredes.add(nodo);
        }
    }
}

    public void configurarControles(Scene escena) {
        escena.setOnKeyPressed((KeyEvent e) -> teclasPresionadas.add(e.getCode()));
        escena.setOnKeyReleased((KeyEvent e) -> teclasPresionadas.remove(e.getCode()));

        animador = new AnimationTimer() {
            @Override//logica del juego en tiempo real
        public void handle(long now) {
            double prevX = lumina.getSprite().getLayoutX();
            double prevY = lumina.getSprite().getLayoutY();

            boolean seMovio = false;

            // Movimiento eje X
            if (teclasPresionadas.contains(KeyCode.RIGHT)) {
                lumina.getSprite().setLayoutX(prevX + velocidad);
                seMovio = true;
                if (Colisiones.colisionConParedes(lumina.getSprite(), paredes)) {
                    lumina.getSprite().setLayoutX(prevX);
                }
            }
            //eje Y
            if (teclasPresionadas.contains(KeyCode.LEFT)) {
                lumina.getSprite().setLayoutX(prevX - velocidad);
                seMovio = true;
                if (Colisiones.colisionConParedes(lumina.getSprite(), paredes)) {
                    lumina.getSprite().setLayoutX(prevX);
                }
            }

            prevY = lumina.getSprite().getLayoutY();

            // Movimiento eje Y
            if (teclasPresionadas.contains(KeyCode.UP)) {
                lumina.getSprite().setLayoutY(prevY - velocidad);
                seMovio = true;
                if (Colisiones.colisionConParedes(lumina.getSprite(), paredes)) {
                    lumina.getSprite().setLayoutY(prevY);
                }
            }
            if (teclasPresionadas.contains(KeyCode.DOWN)) {
                lumina.getSprite().setLayoutY(prevY + velocidad);
                seMovio = true;
                if (Colisiones.colisionConParedes(lumina.getSprite(), paredes)) {
                    lumina.getSprite().setLayoutY(prevY);
                }
            }

            if (seMovio) {
                onMovimiento.run(); // ESTO verifica monedas, fragmentos, puertas, etc.
            }
        }

        };

        animador.start();
    }
}
