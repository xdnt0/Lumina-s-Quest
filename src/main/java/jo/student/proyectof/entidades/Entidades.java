
package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entidades {

    public ImageView crearPersonaje() {
        Image personajeImg = new Image(getClass().getResourceAsStream("/images/personaje.png")); //importar imagen
        ImageView personajeView = new ImageView(personajeImg);
        personajeView.setLayoutX(10); //tamano imagen
        personajeView.setLayoutY(10);
        return personajeView;
    }
}
