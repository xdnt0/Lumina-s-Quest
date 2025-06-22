package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author johan
 */
public abstract class Moneda extends Entidad {

    public Moneda(double x, double y) {
        Image img = new Image(getClass().getResourceAsStream("/images/moneda.png"));
        sprite = new ImageView(img);
        sprite.setLayoutX(x);
        sprite.setLayoutY(y);
    }
}