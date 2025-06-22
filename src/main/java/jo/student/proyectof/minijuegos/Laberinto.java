package jo.student.proyectof.minijuegos;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author johan
 */
public abstract class Laberinto {
    protected ImageView sprite;

    public ImageView getSprite() {
        return sprite;
    }
    public Laberinto(double x, double y){
    Image imagen = new Image(getClass().getResourceAsStream("/images/personaje.png"));
        this.sprite = new ImageView(imagen);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);
    }
}
