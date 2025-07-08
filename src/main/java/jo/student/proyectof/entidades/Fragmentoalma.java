package jo.student.proyectof.entidades;

/**
 *
 * @author johan
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Fragmentoalma extends Entidad {

    private boolean recogido = false;

    public Fragmentoalma(double x, double y) {
        Image img = new Image(getClass().getResourceAsStream("/images/fragmentoAlma.png"));
        this.sprite = new ImageView(img);
        sprite.setFitWidth(60); // Ajusta tamaño según tu sprite
        sprite.setFitHeight(60);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);
    }

    @Override
    public void colision(Entidad otra) {
        // No hace nada por ahora
    }

    public boolean isRecogido() {
        return recogido;
    }

    public void setRecogido(boolean recogido) {
        this.recogido = recogido;
    }
}


/*public class Fragmentoalma {
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
}*/
