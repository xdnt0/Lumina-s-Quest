package jo.student.proyectof.entidades;

/**
 *
 * @author johan
 */


import java.io.InputStream;//para cargar cosas
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class Fragmentoalma extends Entidad {

    private boolean recogido = false;
    private List<Image> frames = new ArrayList<>();//se mete la lista de imagenes en un arreglo
    private Timeline animacion;

    public Fragmentoalma(double x, double y) {
       int totalFrames = 7;

for (int i = 1; i <= totalFrames; i++) {
    String ruta = "/images/fragmentoAlma/" + i + ".png";
    InputStream is = getClass().getResourceAsStream(ruta);
    if (is == null) {
        System.err.println("No se encontro la imagen: " + ruta);
        continue;
    }
    Image img = new Image(is);
    frames.add(img);
}

        // Inicializar el sprite con el primer frame
        this.sprite = new ImageView(frames.get(0));
        sprite.setLayoutX(x);
        sprite.setLayoutY(y);
        ((ImageView) sprite).setFitWidth(60);
        ((ImageView) sprite).setFitHeight(60);

        // Crear animación en bucle
        animacion = new Timeline(new KeyFrame(Duration.millis(100), e -> nextFrame()));
        animacion.setCycleCount(Timeline.INDEFINITE);
        animacion.play();
    }

    private int frameIndex = 0;

    private void nextFrame() {
        frameIndex = (frameIndex + 1) % frames.size();
        ((ImageView) sprite).setImage(frames.get(frameIndex));
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
        if (recogido) {
            animacion.stop();
            sprite.setVisible(false);
        }
    }
}

