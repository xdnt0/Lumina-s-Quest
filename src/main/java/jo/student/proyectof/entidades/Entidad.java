package jo.student.proyectof.entidades;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class Entidad {
    
    protected ImageView sprite;

    public ImageView getSprite() {
        return sprite;
    }
    public Rectangle getBounds() { //esto calcula el hitbox de la imagen
        return new Rectangle(
        sprite.getLayoutX(),
        sprite.getLayoutY(),
        sprite.getFitWidth(),      // Tamaño mostrado
        sprite.getFitHeight()      // Tamaño mostrado
         );
    }

    // Método abstracto (se debe implementar en cada subclase de Entidad)
    public abstract void colision(Entidad otra);
}
