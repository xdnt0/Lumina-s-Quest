package jo.student.proyectof.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Bounds;
import java.util.Random;

public class Laser extends Entidad {
    //Atributos
    private Image imagenActiva;
    private Image imagenInactiva;
    private Rectangle hitbox;
    private Rectangle haz;
    private boolean activo = true;
    private long ultimoCambio = System.currentTimeMillis();
    private long duracionActual = 1000;
    private static final Random random = new Random();
    private boolean enCooldown = false; //para cuando hay daño
    private long tiempoCooldown = 1000; 
    private long tiempoUltimoDaño = 0;

    //Métodos
    //Constructor
    public Laser(int x, int y, Image imagenActiva, Image imagenInactiva) {
        this.imagenActiva = imagenActiva;
        this.imagenInactiva = imagenInactiva;
        
        this.sprite = new ImageView(imagenActiva);
        this.sprite.setFitWidth(40);
        this.sprite.setFitHeight(100);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);

        this.hitbox = new Rectangle();
        this.hitbox.setFill(Color.TRANSPARENT);
        this.hitbox.setUserData("pared");

        //para los láseres
        this.haz = new Rectangle();
        this.haz.setFill(Color.rgb(255, 0, 0, 0.2));
        this.haz.setUserData("laserHaz");

        actualizarHitbox();
    }

    //para manejar el estado de los láseres (que se prendan y se apaguen) y el cooldown si le dan a Lumina
    public void actualizar() {
        long ahora = System.currentTimeMillis();
        
        if (ahora - ultimoCambio >= duracionActual) {
            activo = !activo;
            ultimoCambio = ahora;

            if (activo) {
                duracionActual = 1000;
            } else {
                duracionActual = 1500 + random.nextInt(1001); //tiempo en el que se activan los láseres (es randomizado entre 1.5 segundos y 2.5 segundos)
            }

            //cómo lucen los láseres según el estado
            sprite.setImage(activo ? imagenActiva : imagenInactiva);
            sprite.setOpacity(activo ? 1.0 : 0.5);
            haz.setVisible(activo);
            hitbox.setUserData(activo ? "pared" : null);
        }

        //cooldown cuando el láser se vuelve peligroso otra vez
        if (enCooldown && ahora - tiempoUltimoDaño >= tiempoCooldown) {
            enCooldown = false;
        }

        actualizarHitbox();
    }
    
    //desactivar el láser para que no haga daño
    public void desactivarTemporalmente() {
        activo = false;
        sprite.setOpacity(0.3);
        haz.setVisible(false);
        hitbox.setUserData(null);
        haz.setUserData(null);

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //aquí se reactiva el laser después de un segundo
            activo = true;
            sprite.setOpacity(1.0);
            haz.setVisible(true);
            hitbox.setUserData("pared");
            haz.setUserData("laserHaz");

            //volvemos al encendido/apagado normal
            ultimoCambio = System.currentTimeMillis();
            duracionActual = 1000;
        }).start();
    }
    
    //sincronizamos la hitbox con las dimensiones del láser
    private void actualizarHitbox() {
        Bounds bounds = sprite.getBoundsInParent();
        hitbox.setX(bounds.getMinX());
        hitbox.setY(bounds.getMinY());
        hitbox.setWidth(bounds.getWidth());
        hitbox.setHeight(bounds.getHeight());
    }

    //configura el haz proyectado y lo maneja junto con el sprite del láser en sí
    public void configurarHazVertical(double alto, boolean haciaArriba) {
        double x = sprite.getLayoutX();
        double y = haciaArriba
            ? sprite.getLayoutY() - alto
            : sprite.getLayoutY() + sprite.getFitHeight();

        double ancho = sprite.getFitWidth();

        haz.setX(x);
        haz.setY(y);
        haz.setWidth(ancho);
        haz.setHeight(alto);
    }

    public boolean estaActivo() {
        return activo;
    }

    public boolean puedeHacerDanio() {
        return activo && !enCooldown;
    }

    public void activarCooldown() {
        enCooldown = true;
        tiempoUltimoDaño = System.currentTimeMillis();
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Rectangle getHaz() {
        return haz;
    }

    @Override
    public void colision(Entidad otra) {
    }
}
