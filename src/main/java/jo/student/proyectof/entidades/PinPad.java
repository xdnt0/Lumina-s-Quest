/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    public PinPad(double x, double y, String codigoSecreto) {
        this.codigoSecreto = codigoSecreto;
        this.areaInteraccion = new Rectangle(x, y, 200, 200);
        this.areaInteraccion.setOpacity(0.0);
        this.textoDisplay = new Text(x, y - 30, "");
        this.textoDisplay.setFont(fuente);
        this.textoDisplay.setStyle("-fx-font-size: 24; -fx-fill: white;");
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

    public boolean verificarCodigo() {
        boolean correcto = codigoIngresado.toString().equals(codigoSecreto);
        textoDisplay.setText(correcto ? "¡Correcto!" : "Incorrecto");
        return correcto;
    }

    public void resetear() {
        codigoIngresado.setLength(0);
        textoDisplay.setText("Ingrese código");
    }

    private void actualizarDisplay() {
        textoDisplay.setText(codigoIngresado.toString());
    }

    public Rectangle getAreaInteraccion() {
        return areaInteraccion;
    }

    public Text getTextoDisplay() {
        return textoDisplay;
    }
}