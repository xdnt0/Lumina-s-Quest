package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author johan
 */
public class Enemigos extends Entidad {

    public Enemigos(double x, double y) {
        Image img = new Image(getClass().getResourceAsStream("/images/robotCentinela.png"));
        this.sprite = new ImageView(img);
        this.sprite.setFitWidth(50);  // Ajusta tamaño si es necesario
        this.sprite.setFitHeight(50);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);
    }

    @Override
    public void colision(Entidad otra) {
        if (otra instanceof Lumina) {
            System.out.println("El enemigo dañó a Lumina.");
            // Aquí puedes poner más lógica de daño o reinicio
        }
    }

    // Getters y setters para posición

    public double getX() {
        return sprite.getLayoutX();
    }

    public double getY() {
        return sprite.getLayoutY();
    }

    public void setX(double x) {
        sprite.setLayoutX(x);
    }

    public void setY(double y) {
        sprite.setLayoutY(y);
    }

    // Movimiento hacia una posición objetivo (por ejemplo, Lumina)
    public void moverHacia(double objetivoX, double objetivoY, double velocidad) {
        double dx = objetivoX - getX();
        double dy = objetivoY - getY();
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia > 0) {
            double nx = dx / distancia;
            double ny = dy / distancia;
            setX(getX() + nx * velocidad);
            setY(getY() + ny * velocidad);
        }
    }
}
