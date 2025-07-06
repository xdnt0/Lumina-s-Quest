package jo.student.proyectof.entidades;

/**
 *
 * @author johan
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fragmentoalma {
    private ImageView sprite;

    public Fragmentoalma(double x, double y) {
        Image imagen = new Image(getClass().getResourceAsStream("/images/fragmentoalma.png")); 
        sprite = new ImageView(imagen);
        sprite.setFitWidth(60); // Ajusta tamaño según tu sprite
        sprite.setFitHeight(60);
        sprite.setLayoutX(x);
        sprite.setLayoutY(y);
    }

    public ImageView getSprite() {
        return sprite;
    }
}
