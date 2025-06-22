package jo.student.proyectof.interfaz;

/**
 *
 * @author johan
 */
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import jo.student.proyectof.entidades.Lumina;

public class Controladores {

    private final Lumina lumina;
    private final Runnable onMovimiento;

    public Controladores(Lumina lumina, Runnable onMovimiento) {
        this.lumina = lumina;
        this.onMovimiento = onMovimiento;
    }

    public void configurarControles(Scene escena) {
        escena.setOnKeyPressed(e -> {
            KeyCode tecla = e.getCode();
            switch (tecla) {
                case RIGHT -> lumina.getSprite().setLayoutX(lumina.getSprite().getLayoutX() + 10);
                case LEFT -> lumina.getSprite().setLayoutX(lumina.getSprite().getLayoutX() - 10);
                case UP -> lumina.getSprite().setLayoutY(lumina.getSprite().getLayoutY() - 10);
                case DOWN -> lumina.getSprite().setLayoutY(lumina.getSprite().getLayoutY() + 10);
            }
             onMovimiento.run();
        });
    }
}