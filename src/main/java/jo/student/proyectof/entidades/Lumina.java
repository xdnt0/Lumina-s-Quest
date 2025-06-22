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
        this.sprite.setFitWidth(128);   // ancho deseado (en píxeles)
        this.sprite.setFitHeight(128);
        this.sprite.setLayoutX(0);
        this.sprite.setLayoutY(256);
            }
     @Override
    public void colision(Entidad otra) {
        if (otra instanceof Enemigos) {
            System.out.println("Lumina fue dañada por un enemigo");
            // Lógica para perder vida cuando toca un enemigo
        } else if (otra instanceof Moneda) {
            System.out.println("Lumina recogio una moneda");
            // Lógica para sumar moneda
        }
    }
}