/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jo.student.proyectof.entidades;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 *
 * @author cavelasquezo
 */
public class Libro extends Entidad{
    //Atributos
    private String pista;
    private Text textoPista;
    private boolean mostrandoPista = false;
    private Font fuente = Font.loadFont(getClass().getResourceAsStream("/fuentes/DepartureMono-Regular.otf"),16);

    public Libro(double x, double y, String pista) {
        this.pista = pista;
        Image img = new Image(getClass().getResourceAsStream("/images/libro.png"));
        this.sprite = new ImageView(img);
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);
        this.sprite.setFitWidth(80);
        this.sprite.setFitHeight(80);
        this.textoPista = new Text(x, y - 20, "");
        this.textoPista.setFont(fuente);
        this.textoPista.setStyle("-fx-font-size: 16; -fx-fill: white;");
    }

    @Override
    public Rectangle getBounds() {
    Bounds bounds = sprite.getBoundsInParent();
    return new Rectangle(
        bounds.getMinX(), 
        bounds.getMinY(),
        bounds.getWidth(),
        bounds.getHeight()
    );
}

    public boolean isMostrandoPista() {
        return mostrandoPista;
    }

    public void mostrarPista() {
        if (!mostrandoPista) {
            textoPista.setText(pista);
            mostrandoPista = true;
        }
    }

    public void ocultarPista() {
        textoPista.setText("");
        mostrandoPista = false;
    }

    public Text getTextoPista() {
        return textoPista;
    }

    @Override
    public void colision(Entidad otra) {
    }
}
