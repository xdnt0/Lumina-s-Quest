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

        // Guardamos posición previa
        double prevX = lumina.getSprite().getLayoutX();
        double prevY = lumina.getSprite().getLayoutY();

        // Movimiento provisional
        switch (tecla) {
            case RIGHT -> lumina.getSprite().setLayoutX(prevX + 10);
            case LEFT -> lumina.getSprite().setLayoutX(prevX - 10);
            case UP -> lumina.getSprite().setLayoutY(prevY - 10);
            case DOWN -> lumina.getSprite().setLayoutY(prevY + 10);
        }

        // Ejecutar verificación de colisión
        onMovimiento.run();

        // Si hubo colisión, revertimos el movimiento
        if (lumina.huboColision()) {
            lumina.getSprite().setLayoutX(prevX);
            lumina.getSprite().setLayoutY(prevY);
            lumina.setColision(false); // Resetear estado
        }
    });
}
}