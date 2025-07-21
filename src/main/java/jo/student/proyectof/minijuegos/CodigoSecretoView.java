package jo.student.proyectof.minijuegos;

//Librerías
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;

//Clases del juego
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Fragmentoalma;
import jo.student.proyectof.entidades.Libro;
import jo.student.proyectof.entidades.*;

public class CodigoSecretoView {
    //Atributos
    private Pane root;
    private Lumina lumina;
    private PinPad pinPad;
    private List<Libro> libros = new ArrayList<>();
    private Font fuente = Font.loadFont(getClass().getResourceAsStream("/fuentes/DepartureMono-Regular.otf"),24);
    private Fragmentoalma fragmentoAlma;
    private List<Moneda> monedas = new ArrayList<>();

    //Métodos
    public CodigoSecretoView(int width, int height) {
        root = new Pane();
        root.setPrefSize(width, height);
        inicializarVista();
        System.out.println("CodigoSecretoView cargado con " + root.getChildren().size() + " nodos.");
        inicializarParedes();
        inicializarMonedas();
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
    
    private void inicializarParedes() {
        int[][] paredesData = {
            {0, 0, 2, 1080}, {0, 153, 555, 4}, {555, 156, 4, 444}, {555, 596, 150, 4}, {701, 176, 4, 424},{701, 176, 370, 4}, 
            {1071, 176, 4, 439}, {1071, 611, 215, 4}, {1286, 30, 4, 585}, {1286, 30, 634, 4}, {1920, 0, 2, 1080}, {0, 1080, 1920, 2},
        };

        for (int[] datos : paredesData) {
            Rectangle pared = new Rectangle(datos[2], datos[3]); // ancho, alto
            pared.setX(datos[0]); 
            pared.setY(datos[1]); 
            pared.setFill(Color.TRANSPARENT); // invisible
            pared.setUserData("pared");
            root.getChildren().add(pared);
        }
    }
    
    private void inicializarMonedas() {
        double[][] posiciones = {
            {1200, 900},
            {10, 1000},
            {990, 570}
        };

        for (double[] pos : posiciones) {
            Moneda m = new Moneda(pos[0], pos[1]);
            monedas.add(m);
            root.getChildren().add(m.getSprite());
        }
    }
    
    public void InteraccionLibrosPinpadMonedas() {
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
        
        //Interacción con monedas
        monedas.removeIf(moneda -> {
        if (moneda.getSprite().isVisible() && 
            lumina.getSprite().getBoundsInParent().intersects(moneda.getSprite().getBoundsInParent())) {
            
            root.getChildren().remove(moneda.getSprite());
            System.out.println("Moneda recogida!");
            return true;
        }
        return false;
    });
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

    public void mostrarFragmentoAlma() {
        //Solo mostrar si no ha sido recogido
        if (!fragmentoAlma.isRecogido()) {
            fragmentoAlma.getSprite().setVisible(true);

            //Animación
            fragmentoAlma.getSprite().setScaleX(0);
            fragmentoAlma.getSprite().setScaleY(0);

            new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                    new KeyValue(fragmentoAlma.getSprite().scaleXProperty(), 1),
                    new KeyValue(fragmentoAlma.getSprite().scaleYProperty(), 1)
                    )
                ).play();
        }
    }

    public void mostrarMensajeTransicion(String mensaje) {
        Text textoTransicion = new Text(mensaje);
        textoTransicion.setFont(fuente);
        textoTransicion.setFill(Color.WHITE);
        textoTransicion.setStyle("-fx-font-size: 36; -fx-effect: dropshadow(three-pass-box, black, 10, 0.5, 0, 0);");
        textoTransicion.setX(600);
        textoTransicion.setY(950);

        root.getChildren().add(textoTransicion);

        //Animación de fade out con más tiempo pa que no se vea extraño
        Timeline animacion = new Timeline(
            new KeyFrame(Duration.seconds(6.0),
                new KeyValue(textoTransicion.opacityProperty(), 0)
            )
        );
        animacion.setOnFinished(e -> root.getChildren().remove(textoTransicion));
        animacion.play();
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
