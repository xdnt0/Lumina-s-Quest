package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Bounds;

public class Laser extends Entidad {

    private boolean activo;
    private long tiempoCambio;
    private long ultimoCambio;

    private Image imagenActiva;
    private Image imagenInactiva;

    private Rectangle hitbox;

    public Laser(int x, int y, Image imagenActiva, Image imagenInactiva) {
        this.activo = true;
        this.tiempoCambio = 1000; // 1 segundo
        this.ultimoCambio = System.currentTimeMillis();
        this.imagenActiva = imagenActiva;
        this.imagenInactiva = imagenInactiva;

        this.sprite = new ImageView(imagenActiva);
        this.sprite.setFitWidth(40);
        this.sprite.setFitHeight(100);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);

        this.hitbox = new Rectangle();
        this.hitbox.setFill(Color.TRANSPARENT);
        this.hitbox.setUserData("pared");

        actualizarHitbox(); // Inicializar hitbox en la posición correcta
    }

    public void actualizar() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoCambio >= tiempoCambio) {
            activo = !activo;
            ultimoCambio = ahora;
            sprite.setImage(activo ? imagenActiva : imagenInactiva);
            hitbox.setUserData(activo ? "pared" : null); // solo bloquea si está activo
        }

        actualizarHitbox(); // siempre actualiza posición y tamaño real
    }

    private void actualizarHitbox() {
        Bounds bounds = sprite.getBoundsInParent();
        hitbox.setX(bounds.getMinX());
        hitbox.setY(bounds.getMinY());
        hitbox.setWidth(bounds.getWidth());
        hitbox.setHeight(bounds.getHeight());
        hitbox.setFill(Color.rgb(255, 0, 0, 0.3)); // rojo semitransparente

    }

    public boolean estaActivo() {
        return activo;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void colision(Entidad otra) {
        // lógica opcional
    }
}
