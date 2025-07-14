package jo.student.proyectof.utilidades;

/**
 *
 * @author johan
 */
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Moneda;
import javafx.scene.layout.Pane;

import java.util.Iterator;
import java.util.List;

public class Colisiones {

    public static void verificarMonedas(Lumina lumina, List<Moneda> monedas, Pane root) {
        Iterator<Moneda> it = monedas.iterator();
        while (it.hasNext()) {
            Moneda moneda = it.next();
            if (lumina.getSprite().getBoundsInParent().intersects(moneda.getSprite().getBoundsInParent())) {
                lumina.sumarMoneda();
                root.getChildren().remove(moneda.getSprite());
                it.remove();
                System.out.println("Monedas recogidas: " + lumina.getMonedasRecogidas());
            }
        }
    }
}