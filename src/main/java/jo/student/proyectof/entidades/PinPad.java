package jo.student.proyectof.entidades;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class PinPad extends Entidad {
    //Atributos
    private Rectangle areaInteraccion;
    private Text textoDisplay;
    private StringBuilder codigoIngresado = new StringBuilder();
    private String codigoSecreto;
    private Font fuente = Font.loadFont(getClass().getResourceAsStream("/fuentes/DepartureMono-Regular.otf"),24);

    //Métodos
    public PinPad(double x, double y, String codigoSecreto) {
        this.codigoSecreto = codigoSecreto;
        this.areaInteraccion = new Rectangle(x, y, 200, 200);
        this.areaInteraccion.setOpacity(0.3);
        this.textoDisplay = new Text(x + 50, y + 100, "____");
        this.textoDisplay.setFont(fuente);
        this.textoDisplay.setStyle("-fx-font-size: 24; -fx-fill: white; -fx-font-weight: bold;"); 
    }
    
    private void actualizarDisplay() {
        StringBuilder display = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i < codigoIngresado.length()) {
                display.append(codigoIngresado.charAt(i));
            } else {
                display.append("_");
            }
        }
        textoDisplay.setText(display.toString());
    }

    public boolean verificarCodigo() {
        boolean correcto = codigoIngresado.toString().equals(codigoSecreto);
        if (correcto) {
            textoDisplay.setStyle("-fx-fill: #00ff00; -fx-font-size: 24;"); // Verde si es correcto
            textoDisplay.setText("¡Correcto!");
        } else {
            textoDisplay.setStyle("-fx-fill: #ff0000; -fx-font-size: 24;"); // Rojo si es incorrecto
            textoDisplay.setText("Incorrecto");
        }
        return correcto;
    }

    public void resetear() {
        codigoIngresado.setLength(0);
        textoDisplay.setStyle("-fx-fill: white; -fx-font-size: 24;");
        textoDisplay.setText("____");
    }

    @Override
    public void colision(Entidad otra) {
    }

    public void ingresarDigito(String digito) {
        if (codigoIngresado.length() < 4) {
            codigoIngresado.append(digito);
            actualizarDisplay();
        }
    }

    public Rectangle getAreaInteraccion() {
        return areaInteraccion;
    }

    public Text getTextoDisplay() {
        return textoDisplay;
    }
}