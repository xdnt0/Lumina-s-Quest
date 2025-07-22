package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Bounds;
import java.util.Random;

public class Laser extends Entidad {

    private Image imagenActiva;
    private Image imagenInactiva;

    private Rectangle hitbox;
    private Rectangle haz;

    private boolean activo = true;
    private long ultimoCambio = System.currentTimeMillis();
    private long duracionActual = 1000;
    private static final Random random = new Random();

    public Laser(int x, int y, Image imagenActiva, Image imagenInactiva) {
        this.imagenActiva = imagenActiva;
        this.imagenInactiva = imagenInactiva;

        this.sprite = new ImageView(imagenActiva);
        this.sprite.setFitWidth(40);
        this.sprite.setFitHeight(100);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);

        // Hitbox del sprite (bloqueo)
        this.hitbox = new Rectangle();
        this.hitbox.setFill(Color.TRANSPARENT);
        this.hitbox.setUserData("pared");

        // Haz de luz (rayo rojo)
        this.haz = new Rectangle();
        this.haz.setFill(Color.rgb(255, 0, 0, 0.2)); // rojo tenue visible
        this.haz.setUserData("laserHaz");

        actualizarHitbox();
    }

    public void actualizar() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoCambio >= duracionActual) {
            activo = !activo;
            ultimoCambio = ahora;

            if (activo) {
                duracionActual = 1000; // encendido dura fijo
            } else {
                duracionActual = 1500 + random.nextInt(1001); // entre 1500 y 2500 ms
            }

            sprite.setImage(activo ? imagenActiva : imagenInactiva);
            sprite.setOpacity(activo ? 1.0 : 0.5);
            haz.setVisible(activo);
            hitbox.setUserData(activo ? "pared" : null);
        }

        actualizarHitbox();
    }

    private void actualizarHitbox() {
        Bounds bounds = sprite.getBoundsInParent();
        hitbox.setX(bounds.getMinX());
        hitbox.setY(bounds.getMinY());
        hitbox.setWidth(bounds.getWidth());
        hitbox.setHeight(bounds.getHeight());
    }

    /**
     * Configura un haz de luz vertical proyectado hacia arriba o hacia abajo.
     * @param alto altura del haz
     * @param haciaArriba true si el haz va hacia arriba, false si hacia abajo
     */
    public void configurarHazVertical(double alto, boolean haciaArriba) {
        double x = sprite.getLayoutX();
        double y = haciaArriba
            ? sprite.getLayoutY() - alto
            : sprite.getLayoutY() + sprite.getFitHeight();

        double ancho = sprite.getFitWidth();

        haz.setX(x);
        haz.setY(y);
        haz.setWidth(ancho);
        haz.setHeight(alto);
    }

    public boolean estaActivo() {
        return activo;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Rectangle getHaz() {
        return haz;
    }

    @Override
    public void colision(Entidad otra) {
        // lógica personalizada si la necesitas
    }
}
