package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author johan
 */
public class Moneda extends Entidad {

    public Moneda(double x, double y) {
        Image img = new Image(getClass().getResourceAsStream("/images/entidades/moneda.png")); // Imagen de la moneda 
        sprite = new ImageView(img);   // Imageview para representar la moneda
        this.sprite.setFitWidth(64);   // Tamaño de la moneda
        this.sprite.setFitHeight(64);
        sprite.setLayoutX(x);          //Ubicacion de  la moneda
        sprite.setLayoutY(y);
    }
    @Override
public void colision(Entidad otra) {
    // La moneda no hace nada al colisionar
}
}