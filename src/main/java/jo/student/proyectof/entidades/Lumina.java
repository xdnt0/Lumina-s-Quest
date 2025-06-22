package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author johan
 */
public class Lumina extends Entidad{
    public Lumina(){
        Image imagen = new Image(getClass().getResourceAsStream("/images/personaje.png"));
        this.sprite = new ImageView(imagen);
        this.sprite.setLayoutX(10);
        this.sprite.setLayoutY(10);
            }

}
