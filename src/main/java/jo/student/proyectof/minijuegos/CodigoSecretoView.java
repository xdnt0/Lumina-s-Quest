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
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class CodigoSecretoView {
    //Atributos
    private Pane root;
    private Lumina lumina;
    private PinPad pinPad;
    private List<Libro> libros = new ArrayList<>();
    private Font fuente = Font.loadFont(getClass().getResourceAsStream("/fuentes/DepartureMono-Regular.otf"),24);
    private Fragmentoalma fragmentoAlma;

    public CodigoSecretoView(int width, int height) {
        root = new Pane();
        root.setPrefSize(width, height);
        inicializarVista();
        System.out.println("CodigoSecretoView cargado con " + root.getChildren().size() + " nodos.");

    }

    private void inicializarVista() {
        //Fondo
        Image fondo = new Image(getClass().getResourceAsStream("/images/minijuegos/2/Biblioteca.png"));
        ImageView fondoView = new ImageView(fondo);
        root.getChildren().add(fondoView);

        //Lumina
        lumina = new Lumina();
        lumina.getSprite().setLayoutX(200);
        lumina.getSprite().setLayoutY(800);
        root.getChildren().add(lumina.getSprite());

        //Libros con pistas
        libros.add(new Libro(175, 488, "1° : 3° + 9"));
        libros.add(new Libro(453, 220, "2° : par mayor a 4"));
        libros.add(new Libro(1500, 50, "3° : # monedas en el laberinto"));
        libros.add(new Libro(1850, 800, "4° : 0"));

        libros.forEach(libro -> {
            root.getChildren().addAll(libro.getSprite(), libro.getTextoPista());
        });

        //PinPad
        pinPad = new PinPad(1200, 600, "2630");
        pinPad.getTextoDisplay().setFont(fuente);
        pinPad.getTextoDisplay().setStyle("-fx-fill: white;"); 
        root.getChildren().addAll(pinPad.getAreaInteraccion(), pinPad.getTextoDisplay());
        
        //Fragmento de alma
        fragmentoAlma = new Fragmentoalma(850,688);
        fragmentoAlma.getSprite().setVisible(false);
        root.getChildren().add(fragmentoAlma.getSprite());
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
            if (input.matches("\\d")) { //Solo dígitos
                pinPad.ingresarDigito(input);
                
                //Verificación automática al completar 4 dígitos
                if (pinPad.getTextoDisplay().getText().replace("_", "").length() == 4) {
                    boolean esCorrecto=pinPad.verificarCodigo();
                    if (esCorrecto) {
                    //Mostrar fragmento de alma si el código es correcto
                    mostrarFragmentoAlma();
                } else {
                    //Reset después de 2 segundos (si es incorrecto)
                    new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> pinPad.resetear());
                            }
                        }, 
                        2000
                    );
                }
            }
        }
    }
}

private void mostrarFragmentoAlma() {
    if (fragmentoAlma == null) {
        fragmentoAlma = new Fragmentoalma(
            pinPad.getAreaInteraccion().getX() + 100,  // Posición X relativa al pinpad
            pinPad.getAreaInteraccion().getY() - 100   // Posición Y relativa al pinpad
        );
        root.getChildren().add(fragmentoAlma.getSprite());
    }
    fragmentoAlma.getSprite().setVisible(true);
    // Animación de aparición
    fragmentoAlma.getSprite().setScaleX(0);
    fragmentoAlma.getSprite().setScaleY(0);
    new Timeline(
        new KeyFrame(Duration.seconds(0.5),
            new KeyValue(fragmentoAlma.getSprite().scaleXProperty(), 1),
            new KeyValue(fragmentoAlma.getSprite().scaleYProperty(), 1)
        )
    ).play();
}

    public Fragmentoalma getFragmentoAlma() {
        return fragmentoAlma;
    }
    
    public Pane getRoot() {
        return root;
    }

    public Lumina getLumina() {
        return lumina;
    }
    
    public PinPad getPinPad() {
    return pinPad;
    }
}
