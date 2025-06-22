package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author johan
 */
public class Enemigos extends Entidad{
     public Enemigos(double x, double y) {
        Image img = new Image(getClass().getResourceAsStream("/images/robotCentinela.png"));
        this.sprite = new ImageView(img);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);
    }

    @Override
    public void colision(Entidad otra) {
        if (otra instanceof Lumina) {
            System.out.println("El enemigo dañó a Lumina.");
            // lógica de ataque
        }
    }
}
