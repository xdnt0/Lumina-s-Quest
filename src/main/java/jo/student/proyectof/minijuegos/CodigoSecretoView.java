/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jo.student.proyectof.minijuegos;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Fragmentoalma;
import jo.student.proyectof.entidades.Libro;
import jo.student.proyectof.entidades.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Font;

public class CodigoSecretoView {
    //Atributos
    private Pane root;
    private Lumina lumina;
    private PinPad pinPad;
    private List<Libro> libros = new ArrayList<>();
    private Font fuente = Font.loadFont(getClass().getResourceAsStream("/fuentes/DepartureMono-Regular.otf"),24);

    public CodigoSecretoView(int width, int height) {
        
        
        root = new Pane();
        root.setPrefSize(width, height);
        inicializarVista();
    }

    private void inicializarVista() {
        //Fondo
        Image fondo = new Image(getClass().getResourceAsStream("/images/salaPrincipal.png"));
        ImageView fondoView = new ImageView(fondo);
        root.getChildren().add(fondoView);

        //Lumina
        lumina = new Lumina();
        lumina.getSprite().setLayoutX(200);
        lumina.getSprite().setLayoutY(800);
        root.getChildren().add(lumina.getSprite());

        //Libros con pistas
        libros.add(new Libro(300, 400, "1° : 8"));
        libros.add(new Libro(500, 400, "2° : 7"));
        libros.add(new Libro(700, 400, "3° : 4"));
        libros.add(new Libro(900, 400, "4° : 2"));

        libros.forEach(libro -> {
            root.getChildren().addAll(libro.getSprite(), libro.getTextoPista());
        });

        //PinPad
        pinPad = new PinPad(1200, 600, "8742");
        pinPad.getTextoDisplay().setFont(fuente);
        pinPad.getTextoDisplay().setStyle("-fx-fill: white;"); 
        root.getChildren().addAll(pinPad.getAreaInteraccion(), pinPad.getTextoDisplay());
    }

    public void InteraccionLibrosPinpad() {
        //Interacción con libros
        libros.forEach(libro -> {
            if (lumina.getSprite().getBoundsInParent().intersects(libro.getBounds().getBoundsInParent())) {
                libro.mostrarPista();
            } else if (libro.isMostrandoPista()) {
                libro.ocultarPista();
            }
        });

        //Interacción con PinPad
        if (lumina.getSprite().getBoundsInParent().intersects(pinPad.getAreaInteraccion().getBoundsInParent())) {
            pinPad.getTextoDisplay().setText("Ingrese código");
        } else {
            pinPad.resetear();
        }
    }

    public void manejarInput(String input) {
        if (lumina.getSprite().getBoundsInParent().intersects(pinPad.getAreaInteraccion().getBoundsInParent())) {
            pinPad.ingresarDigito(input);
            if (pinPad.getTextoDisplay().getText().length() == 4) {
                pinPad.verificarCodigo();
            }
        }
    }

    public Pane getRoot() {
        return root;
    }

    public Lumina getLumina() {
        return lumina;
    }
    
    //Falta agregar lo del fragmento del alma en cuento el código esté correcto
}
