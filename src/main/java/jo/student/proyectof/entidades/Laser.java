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

    // Cooldown por daño
    private boolean enCooldown = false;
    private long tiempoCooldown = 1000; // 1 segundo
    private long tiempoUltimoDaño = 0;

    public Laser(int x, int y, Image imagenActiva, Image imagenInactiva) {
        this.imagenActiva = imagenActiva;
        this.imagenInactiva = imagenInactiva;

        this.sprite = new ImageView(imagenActiva);
        this.sprite.setFitWidth(40);
        this.sprite.setFitHeight(100);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);

        // Hitbox del sprite
        this.hitbox = new Rectangle();
        this.hitbox.setFill(Color.TRANSPARENT);
        this.hitbox.setUserData("pared");

        // Haz de luz
        this.haz = new Rectangle();
        this.haz.setFill(Color.rgb(255, 0, 0, 0.2)); // rojo tenue
        this.haz.setUserData("laserHaz");

        actualizarHitbox();
    }

    public void actualizar() {
        long ahora = System.currentTimeMillis();

        // Actualiza estado activo/inactivo
        if (ahora - ultimoCambio >= duracionActual) {
            activo = !activo;
            ultimoCambio = ahora;

            if (activo) {
                duracionActual = 1000;
            } else {
                duracionActual = 1500 + random.nextInt(1001); // 1500 - 2500ms
            }

            sprite.setImage(activo ? imagenActiva : imagenInactiva);
            sprite.setOpacity(activo ? 1.0 : 0.5);
            haz.setVisible(activo);
            hitbox.setUserData(activo ? "pared" : null);
        }

        // Actualiza cooldown (haz se vuelve peligroso otra vez)
        if (enCooldown && ahora - tiempoUltimoDaño >= tiempoCooldown) {
            enCooldown = false;
        }

        actualizarHitbox();
    }
    public void desactivarTemporalmente() {
        activo = false;
        sprite.setOpacity(0.3);
        haz.setVisible(false);
        hitbox.setUserData(null);
        haz.setUserData(null);

        new Thread(() -> {
            try {
                Thread.sleep(1000); // 1 segundo de invulnerabilidad
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Reactivar
            activo = true;
            sprite.setOpacity(1.0);
            haz.setVisible(true);
            hitbox.setUserData("pared");
            haz.setUserData("laserHaz");

            // Reiniciar el temporizador de encendido/apagado normal
            ultimoCambio = System.currentTimeMillis();
            duracionActual = 1000;
        }).start();
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
     * @param haciaArriba true si el haz va hacia arriba, false si va hacia abajo
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

    public boolean puedeHacerDanio() {
        return activo && !enCooldown;
    }

    public void activarCooldown() {
        enCooldown = true;
        tiempoUltimoDaño = System.currentTimeMillis();
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Rectangle getHaz() {
        return haz;
    }

    @Override
    public void colision(Entidad otra) {
        // lógica personalizada si se necesita
    }
}
