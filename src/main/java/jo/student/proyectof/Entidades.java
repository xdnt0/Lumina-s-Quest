
package jo.student.proyectof;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Entidades {

    public ImageView crearPersonaje() {
        Image personajeImg = new Image(getClass().getResourceAsStream("/resources/img/personaje.png")); //importar imagen
        ImageView personajeView = new ImageView(personajeImg);
        personajeView.setLayoutX(10); //tamano imagen
        personajeView.setLayoutY(10);
        return personajeView;
    }
}
