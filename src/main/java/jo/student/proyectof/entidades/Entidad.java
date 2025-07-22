package jo.student.proyectof.entidades;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


// Clase base para todos los objetos del juego que tienen imagen y colision
public abstract class Entidad {
    
    //Imagen que se muestra en pantalla
    protected ImageView sprite;

    public ImageView getSprite() {
        return sprite;
    }
    
    //Devuelve los limites del objeto para poder detectar colisiones
    public Rectangle getBounds() { 
        return new Rectangle(
        sprite.getLayoutX(),
        sprite.getLayoutY(),
        sprite.getFitWidth(),      
        sprite.getFitHeight()      
         );
    }

    // Método abstracto para definir que pasa cuando esta entidad choca con otra
    public abstract void colision(Entidad otra);
}
