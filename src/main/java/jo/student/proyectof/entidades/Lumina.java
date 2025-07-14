package jo.student.proyectof.entidades;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author johan
 */

public class Lumina extends Entidad{
    
    private int vidas = 3;
    private boolean colisionDetectada = false;
    private boolean invulnerable = false;
    private int monedasRecogidas = 0;

    public Lumina(){
        Image imagen = new Image(getClass().getResourceAsStream("/images/personaje.png"));
        this.sprite = new ImageView(imagen);
        this.sprite.setFitWidth(50);   // ancho deseado (en píxeles)
        this.sprite.setFitHeight(50);
        this.sprite.setLayoutX(0);
        this.sprite.setLayoutY(256);
    }
    public int getMonedasRecogidas() {
    return monedasRecogidas;
    }

    public void sumarMoneda() {
    monedasRecogidas++;
    }
    
    @Override
    public void colision(Entidad otra) {
        if (otra instanceof Enemigos) {
            System.out.println("Lumina fue dañada por un enemigo");
            perderVida();
        } else if (otra instanceof Moneda) {
            System.out.println("Lumina recogió una moneda");
        }
    }
    
    
    public void hacerInvulnerable() {
        invulnerable = true;
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 3 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invulnerable = false;
        }).start();
    }
    
    
    public void perderVida() {
        if (!invulnerable && vidas > 0) {
            vidas--;
            hacerInvulnerable();
            System.out.println("Lumina perdio una vida. Vidas restantes: " + vidas);
        }
    }
    
    public int getVidas() {
        return vidas;
    }

    public void setColision(boolean colision) {
        this.colisionDetectada = colision;
    }

    public boolean huboColision() {
        return this.colisionDetectada;
    }
    
    public boolean estaInvulnerable() {
        return invulnerable;
    }
        
}
