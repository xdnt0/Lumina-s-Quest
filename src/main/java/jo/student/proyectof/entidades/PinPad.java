package jo.student.proyectof.entidades;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class PinPad extends Entidad {
    //Atributos
    private final Rectangle areaInteraccion;
    private final Text textoDisplay;
    private final StringBuilder codigoIngresado = new StringBuilder(); //Importante, puede ser final porque existe método de reseteo
    private final String codigoSecreto;
    private final Font fuente = Font.loadFont(getClass().getResourceAsStream("/fuentes/DepartureMono-Regular.otf"),24);

    //Métodos
    //Constructor
    public PinPad(double x, double y, String codigoSecreto) {
        this.codigoSecreto = codigoSecreto;
        this.areaInteraccion = new Rectangle(x, y, 200, 200);
        this.areaInteraccion.setOpacity(0.3);
        this.textoDisplay = new Text(x + 50, y + 100, "____"); //Son 4 guiones al piso para que se sepa que el código es de 4 dígitos
        this.textoDisplay.setFont(fuente);
        this.textoDisplay.setStyle("-fx-font-size: 24; -fx-fill: white;"); 
    }
    
    //Para que se vaya viendo el código que se ingresa
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

    //Verificamos código con feedback para el usuario
    public boolean verificarCodigo() {
        boolean correcto = codigoIngresado.toString().equals(codigoSecreto);
        if (correcto) {
            textoDisplay.setStyle("-fx-fill: #00ff00; -fx-font-size: 24;"); //color verde si es correcto
            textoDisplay.setText("¡Correcto!");
        } else {
            textoDisplay.setStyle("-fx-fill: #ff0000; -fx-font-size: 24;"); //color rojo si es incorrecto
            textoDisplay.setText("Incorrecto");
        }
        return correcto;
    }

    //Si el codigo es incorrecto, el código ingresado se resetea (de aquí que pueda ser atributo final)
    public void resetear() {
        codigoIngresado.setLength(0);
        textoDisplay.setStyle("-fx-fill: white; -fx-font-size: 24;");
        textoDisplay.setText("____");
    }

    //A cada que se ingresa un dígito se actualiza el display y el código ingresado
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
    
    @Override
    public void colision(Entidad otra) {
    }
}