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

/**
 *
 * @author Familia Betancourt
 */
public class LaberintoView {
    
    private Pane laberintoPane;
    private Lumina lumina;

    public LaberintoView() {
        laberintoPane = new Pane();
        inicializarLaberinto();
    }

    private void inicializarLaberinto() {
    // 1. Fondo del mapa del laberinto
    Image imagenMapa = new Image(getClass().getResourceAsStream("/images/laberinto.png"));
    ImageView fondoMapa = new ImageView(imagenMapa);
    laberintoPane.getChildren().add(fondoMapa);

    // 2. Lista de paredes invisibles (coordenadas x, y, ancho, alto)
    int[][] paredesData = {
        {22, 119, 338, 30},
        {22, 119, 30, 223},
        {330, 119, 30, 125},
        {330, 216, 122, 30},
        {425, 119, 30, 128},
        {425, 119, 303, 30},
        {698, 119, 30, 135},
        {893, 119, 384, 30},
        {893, 119, 30, 129},
        {1248, 119, 30, 164},
        {893, 217, 294, 30},
        {1157, 217, 30, 130},
        {1157, 316, 173, 30},
        {1247, 316, 30, 135},
        {1341, 216, 80, 30},
        {1391, 216, 30, 632},
        {1376, 819, 45, 30},
        {604, 619, 817, 30},
        {1155, 718, 150, 30},
        {1155, 718, 30, 231},
        {893, 918, 293, 30},
        {893, 619, 30, 330},
        {1064, 619, 30, 231},
        {981, 821, 112, 30},
        {981, 722, 30, 127},
        {698, 417, 30, 134},
        {892, 417, 30, 134},
        {513, 521, 408, 30},
        {604, 521, 30, 229},
        {511, 719, 123, 30},
        {978, 420, 30, 229},
        {978, 521, 113, 30},
        
        // Agrega aquí más coordenadas según tu imagen
    };

    for (int[] datos : paredesData) {
        Rectangle pared = new Rectangle(datos[2], datos[3]); // ancho, alto
        pared.setX(datos[0]);
        pared.setY(datos[1]);
        pared.setFill(Color.rgb(255, 255, 255, 0.6)); 
        // pared.setFill(Color.rgb(255, 0, 0, 0.3)); // visible para testing
        laberintoPane.getChildren().add(pared);
    }

    // 3. Crear a Lumina y agregarla al panel
    lumina = new Lumina();
    lumina.getSprite().setX(128); // Ajusta esta posición según el inicio de tu mapa
    lumina.getSprite().setY(128);
    laberintoPane.getChildren().add(lumina.getSprite());
}

    // Devuelve el panel del laberinto
    public Pane getLaberintoPane() {
        return laberintoPane;
    }

    // Devuelve la instancia de Lumina
    public Lumina getLumina() {
        return lumina;
    }
}
    
