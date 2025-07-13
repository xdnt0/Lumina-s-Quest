package jo.student.proyectof.interfaz;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jo.student.proyectof.entidades.Lumina;
import java.util.HashSet;
import java.util.Set;

public class Controladores {

    private final Lumina lumina;
    private final Runnable onMovimiento;
    private final Set<KeyCode> teclasPresionadas = new HashSet<>();
    private AnimationTimer animador;
    private final double velocidad = 3; // Puedes cambiarlo para mayor o menor fluidez

    public Controladores(Lumina lumina, Runnable onMovimiento) {
        this.lumina = lumina;
        this.onMovimiento = onMovimiento;
    }

    public void configurarControles(Scene escena) {
        escena.setOnKeyPressed((KeyEvent e) -> teclasPresionadas.add(e.getCode()));
        escena.setOnKeyReleased((KeyEvent e) -> teclasPresionadas.remove(e.getCode()));

        animador = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double prevX = lumina.getSprite().getLayoutX();
                double prevY = lumina.getSprite().getLayoutY();

                if (teclasPresionadas.contains(KeyCode.RIGHT)) {
                    lumina.getSprite().setLayoutX(prevX + velocidad);
                }
                if (teclasPresionadas.contains(KeyCode.LEFT)) {
                    lumina.getSprite().setLayoutX(prevX - velocidad);
                }
                if (teclasPresionadas.contains(KeyCode.UP)) {
                    lumina.getSprite().setLayoutY(prevY - velocidad);
                }
                if (teclasPresionadas.contains(KeyCode.DOWN)) {
                    lumina.getSprite().setLayoutY(prevY + velocidad);
                }

                // Ejecutar verificacion de colision
                onMovimiento.run();

                if (lumina.huboColision()) {
                    lumina.getSprite().setLayoutX(prevX);
                    lumina.getSprite().setLayoutY(prevY);
                    lumina.setColision(false); // Resetear estado
                }
            }
        };

        animador.start();
    }
}
