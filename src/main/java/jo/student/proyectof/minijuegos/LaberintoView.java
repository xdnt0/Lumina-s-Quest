/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jo.student.proyectof.minijuegos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Fragmentoalma;

/**
 *
 * @author Familia Betancourt
 */

public class LaberintoView {

    private Pane laberintoPane;
    private Lumina lumina;
    private Fragmentoalma fragmentoalma;

    public LaberintoView(int width, int height) {
        laberintoPane = new Pane();
        laberintoPane.setPrefSize(width, height);  // ← Usa el tamaño dado por Game.java
        inicializarLaberinto();
    }

    private void inicializarLaberinto() {
        // 1. Fondo del mapa del laberinto
        Image imagenMapa = new Image(getClass().getResourceAsStream("/images/laberinto.png"));
        ImageView fondoMapa = new ImageView(imagenMapa);
        laberintoPane.getChildren().add(fondoMapa);

        // 2. Lista de paredes invisibles (coordenadas x, y, ancho, alto)
        int[][] paredesData = {
            {28, 125, 39, 236},
            {28, 125, 422, 32},
            {413, 125, 37, 136},
            {413, 228, 155, 32},
            {530, 125, 37, 136},
            {530, 125, 379, 32},
            {872, 125, 37, 142},
            {1116, 125, 38, 135},
            {1116, 125, 481, 32},
            {1116, 228, 366, 32},
            {1446, 228, 35, 138},
            {1560, 125, 35, 173},
            {1446, 334, 215, 32},
            {1559, 334, 38, 142},
            {1676, 228, 103, 32},
            {1740, 228, 39, 668},
            {1721, 863, 57, 32},
            {1676, 442, 37, 140},
            {1444, 549, 269, 32},
            {1444, 444, 37, 137},
            {1328, 444, 153, 32},
            
            {1328, 334, 36, 142},
            {755, 334, 611, 32},
            {642, 444, 151, 32},
            {755, 228, 38, 247},
            {645, 228, 148, 32},
            {645, 228, 38, 138},
            {530, 334, 152, 32},
            {530, 334, 38, 562},
            {260, 654, 413, 30},
            {531, 864, 384, 32},
            {878, 760, 36, 135},
            {262, 759, 38, 266},
            {262, 759, 187, 32},
            {412, 759, 38, 138},
            {0, 568, 68, 32},
            {29, 446, 39, 154},
            {29, 446, 184, 32},
            {143, 229, 190, 32},
            {143, 229, 38, 153},
            {143, 349, 189, 32},
            {292, 349, 38, 234},
            {143, 551, 188, 32},
            
            {143, 551, 40, 362},
            {755, 653, 1023, 32},
            {755, 549, 38, 242},
            {641,549, 510, 32},
            {871, 440, 38, 141},
            {1115, 440, 38, 141},
            {1221, 442, 38, 242},
            {1221, 550, 142, 32},
            {640, 759, 153, 32},
            {1116, 653, 35, 348},
            {1116, 969, 367, 32},
            {1444, 758, 38, 243},
            {1444, 758, 189, 32},
            {1329, 653, 38, 244},
            {1226, 865, 140, 32},
            {1226, 767, 38, 135},
            
            {0, 1025, 1920, 55},
            {1865, 0, 55, 1080},
            {0, 0, 1920, 43},
            {0, 0, 1, 1080},
            
            {1007, 43, 22, 50},
            {1007, 364, 22, 107},
            {1525, 41, 215, 55},
            
            // Puedes agregar más si lo necesitas
        };

        for (int[] datos : paredesData) {
            Rectangle pared = new Rectangle(datos[2], datos[3]); // ancho, alto
            pared.setX(datos[0]);
            pared.setY(datos[1]);
            //pared.setFill(Color.rgb(255, 255, 255, 0.0)); // invisible
            pared.setFill(Color.rgb(255, 255, 255, 0.6)); // visible para testing
            laberintoPane.getChildren().add(pared);
        }

        // 3. Crear a Lumina y agregarla al panel
        lumina = new Lumina();
        lumina.getSprite().setX(128);
        lumina.getSprite().setY(128);
        laberintoPane.getChildren().add(lumina.getSprite());
        
        fragmentoalma = new Fragmentoalma(1720, 910); // Ajusta la posición según tu mapa
        laberintoPane.getChildren().add(fragmentoalma.getSprite());
        
    }
    
    public Fragmentoalma getFragmentoalma() {
        return fragmentoalma;
    }
    

    public Pane getLaberintoPane() {
        return laberintoPane;
    }

    public Lumina getLumina() {
        return lumina;
    }
}






/*public class LaberintoView {

    private StackPane laberintoPane;
    private Lumina lumina;

    public LaberintoView() {
        laberintoPane = new StackPane();
        laberintoPane.setStyle("-fx-background-color: black;");
        inicializarLaberinto();
    }

    private void inicializarLaberinto() {
        // Pane interno con tamaño base 16:9
        Pane contenido = new Pane();
        contenido.setPrefSize(1280, 720);

        // Fondo del laberinto
        Image imagenMapa = new Image(getClass().getResourceAsStream("/images/laberinto.png"));
        ImageView fondoMapa = new ImageView(imagenMapa);
        contenido.getChildren().add(fondoMapa);

        // Paredes invisibles
        int[][] paredesData = {
            {22, 119, 338, 30},
        };

        for (int[] datos : paredesData) {
            Rectangle pared = new Rectangle(datos[2], datos[3]);
            pared.setX(datos[0]);
            pared.setY(datos[1]);
            pared.setFill(Color.rgb(255, 255, 255, 0.0)); // completamente invisible
            contenido.getChildren().add(pared);
        }

        // Crear y posicionar a Lumina
        lumina = new Lumina();
        lumina.getSprite().setX(128);
        lumina.getSprite().setY(128);
        contenido.getChildren().add(lumina.getSprite());

        // Escalado proporcional automático
        contenido.scaleXProperty().bind(
            Bindings.createDoubleBinding(() ->
                Math.min(laberintoPane.getWidth() / 1280.0, laberintoPane.getHeight() / 720.0),
                laberintoPane.widthProperty(), laberintoPane.heightProperty()
            )
        );
        contenido.scaleYProperty().bind(contenido.scaleXProperty()); // mantener proporción

        laberintoPane.getChildren().add(contenido);
    }

    public StackPane getLaberintoPane() {
        return laberintoPane;
    }

    public Lumina getLumina() {
        return lumina;
    }
}*/
    
