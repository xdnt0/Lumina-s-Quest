package jo.student.proyectof.entidades;

/**
 *
 * @author johan
 */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Laser extends Entidad {

    private boolean activo;
    private long tiempoCambio;
    private long ultimoCambio;

    private Image imagenActiva;
    private Image imagenInactiva;

    public Laser(int x, int y, Image imagenActiva, Image imagenInactiva) {
        this.activo = true;
        this.tiempoCambio = 1000; // 1 segundo
        this.ultimoCambio = System.currentTimeMillis();
        this.imagenActiva = imagenActiva;
        this.imagenInactiva = imagenInactiva;

        this.sprite = new ImageView(imagenActiva);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);
    }

    public void actualizar() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoCambio >= tiempoCambio) {
            activo = !activo;
            ultimoCambio = ahora;
            sprite.setImage(activo ? imagenActiva : imagenInactiva);
        }
    }

    public boolean estaActivo() {
        return activo;
    }

    @Override
    public void colision(Entidad otra) {
        // Este laser no hace nada por defecto al colisionar
        // Puedes definir lógica si quieres que cause daño, etc.
    }
}
