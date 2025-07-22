package jo.student.proyectof.minijuegos;

// Javafx
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Imports de entidades
import jo.student.proyectof.entidades.Lumina;
import jo.student.proyectof.entidades.Fragmentoalma;
import jo.student.proyectof.entidades.Enemigos;
import jo.student.proyectof.entidades.Moneda;

//Imports de utilidades
import jo.student.proyectof.utilidades.Colisiones;
import java.util.ArrayList;
import java.util.List;

public class LaberintoView {
    
    private boolean salidaActivada = false; //Marca si se activo la salida
    private Rectangle zonaSalida; //Zona para salir del laberinto
    private Runnable onSalida; //Accion a ejecutar para la salida
    
    private Pane laberintoPane;
    private Lumina lumina;
    private Fragmentoalma fragmentoalma;
    private Enemigos robotCentinela;
    private List<Moneda> monedas = new ArrayList<>();
    private List<ImageView> corazones = new ArrayList<>();
    private boolean fragmentoRecogido = false;
    private int puntoActual = 0;
    private List<double[]> caminoRobot;

    public LaberintoView(int width, int height) {
        laberintoPane = new Pane();
        laberintoPane.setPrefSize(width, height);

        inicializarFondoYLumina(); //Fondo y personaje
        inicializarHUD(); //Corazones
        inicializarCaminoRobot(); //Ruta del robot centinela
        inicializarMonedas(); //Monedas que estan en el laberinto
        inicializarParedes(); // Paredes invisibles del laberinto

        gameLoop.start(); //Iniciar el bucle del juego
    }

    private void inicializarFondoYLumina() {
        Image imagenMapa = new Image(getClass().getResourceAsStream("/images/minijuegos/1/laberinto.png"));
        ImageView fondoMapa = new ImageView(imagenMapa);
        laberintoPane.getChildren().add(fondoMapa);

        lumina = new Lumina();
        lumina.getSprite().setX(128);
        lumina.getSprite().setY(128);
        laberintoPane.getChildren().add(lumina.getSprite());

        fragmentoalma = new Fragmentoalma(1720, 910);
        laberintoPane.getChildren().add(fragmentoalma.getSprite());
        
        // Zona de salida         
        zonaSalida = new Rectangle(13, 361, 37, 84); // ejemplo en la parte inferior
        zonaSalida.setOpacity(0.0); // invisible
        laberintoPane.getChildren().add(zonaSalida);
        
    }

    private void inicializarParedes() {
        // Cordenadas de las paredes (x, y, Ancho, Alto)
        int[][] paredesData = {
            {28, 125, 39, 236}, {28, 125, 422, 32}, {413, 125, 37, 136}, {413, 228, 154, 32},
            {530, 125, 37, 136}, {530, 125, 379, 32}, {872, 125, 37, 142}, {1116, 125, 38, 135},
            {1116, 125, 481, 32}, {1116, 228, 366, 32}, {1446, 228, 35, 138}, {1560, 125, 35, 173},
            {1446, 334, 215, 32}, {1559, 334, 38, 142}, {1676, 228, 103, 32}, {1740, 228, 39, 668},
            {1721, 863, 57, 32}, {1676, 442, 37, 140}, {1444, 549, 269, 32}, {1444, 444, 37, 137},
            {1328, 444, 153, 32}, {1328, 334, 36, 142}, {755, 334, 611, 32}, {642, 444, 151, 32},
            {755, 228, 38, 247}, {645, 228, 148, 32}, {645, 228, 38, 138}, {530, 334, 152, 32},
            {530, 334, 38, 562}, {260, 654, 413, 30}, {531, 864, 384, 32}, {878, 760, 36, 135},
            {262, 759, 38, 266}, {262, 759, 187, 32}, {412, 759, 38, 138}, {0, 568, 68, 32},
            {29, 446, 39, 154}, {29, 446, 184, 32}, {143, 229, 190, 32}, {143, 229, 38, 153},
            {143, 349, 189, 32}, {292, 349, 38, 234}, {143, 551, 188, 32}, {143, 551, 40, 362},
            {755, 653, 1023, 32}, {755, 549, 38, 242}, {641, 549, 510, 32}, {871, 440, 38, 141},
            {1115, 440, 38, 141}, {1221, 442, 38, 242}, {1221, 550, 142, 32}, {640, 759, 153, 32},
            {1116, 653, 35, 348}, {1116, 969, 367, 32}, {1444, 758, 38, 243}, {1444, 758, 189, 32},
            {1329, 653, 38, 244}, {1226, 865, 140, 32}, {1226, 767, 38, 135}, {0, 1025, 1920, 55},
            {1865, 0, 55, 1080}, {0, 0, 1920, 43}, {0, 0, 1, 1080}, {1007, 43, 22, 50},
            {1007, 364, 22, 107}, {1525, 41, 215, 55}
        };

            for (int[] datos : paredesData) {
        Rectangle pared = new Rectangle(datos[2], datos[3]);
        pared.setX(datos[0]);
        pared.setY(datos[1]);
        pared.setFill(Color.rgb(255, 255, 255, 0.0));
        pared.setUserData("pared"); // marcar la pared para colisiones
        laberintoPane.getChildren().add(pared);
        }
    }

    private void inicializarCaminoRobot() {
        //Lista de puntos por donde se mueve el robot
        caminoRobot = new ArrayList<>();
        caminoRobot.add(new double[]{1794, 958});
        caminoRobot.add(new double[]{1786, 147});
        caminoRobot.add(new double[]{1614, 147});
        caminoRobot.add(new double[]{1614, 270});
        caminoRobot.add(new double[]{1679, 270});
        caminoRobot.add(new double[]{1679, 376});
        caminoRobot.add(new double[]{1606, 376});
        caminoRobot.add(new double[]{1606, 482});
        caminoRobot.add(new double[]{1496, 482});
        caminoRobot.add(new double[]{1496, 383});
        caminoRobot.add(new double[]{1377, 383});
        caminoRobot.add(new double[]{1377, 276});
        caminoRobot.add(new double[]{807, 276});
        caminoRobot.add(new double[]{807, 168});
        caminoRobot.add(new double[]{579, 168});
        caminoRobot.add(new double[]{579, 272});
        caminoRobot.add(new double[]{345, 272});
        caminoRobot.add(new double[]{345, 170});
        caminoRobot.add(new double[]{80, 170});
        caminoRobot.add(new double[]{80, 380});
        caminoRobot.add(new double[]{24, 380});
    }

    private void inicializarMonedas() {
        double[][] posiciones = {
            {1270, 800},
            {685, 300},
            {455, 160}
        };

        for (double[] pos : posiciones) {
            Moneda m = new Moneda(pos[0], pos[1]);
            monedas.add(m);
            laberintoPane.getChildren().add(m.getSprite());
        }
    }

    private void inicializarHUD() {
        Image corazonImg = new Image(getClass().getResourceAsStream("/images/corazon.png"));
        for (int i = 0; i < lumina.getVidas(); i++) {
            ImageView corazon = new ImageView(corazonImg);
            corazon.setFitWidth(40);
            corazon.setFitHeight(40);
            corazon.setX(20 + i * 50);
            corazon.setY(20);
            corazones.add(corazon);
            laberintoPane.getChildren().add(corazon);
        }
    }

    private void actualizarCorazones() {
        //Ocultar un corazon si lumina pierde vida
        for (int i = 0; i < corazones.size(); i++) {
            corazones.get(i).setVisible(i < lumina.getVidas());
        }
    }

    public void mostrarRobotCentinela() {
        if (robotCentinela == null) {
            robotCentinela = new Enemigos(1490, 800);
            laberintoPane.getChildren().add(robotCentinela.getSprite());
            puntoActual = 0; //Reiniciar el recorrido
        }
    }

    public void moverRobotPorCamino() {
        if (robotCentinela != null && fragmentoRecogido && puntoActual < caminoRobot.size()) {
            double[] objetivo = caminoRobot.get(puntoActual);
            double x = objetivo[0];
            double y = objetivo[1];

            robotCentinela.moverHacia(x, y, 1.2);

            double dx = Math.abs(robotCentinela.getX() - x);
            double dy = Math.abs(robotCentinela.getY() - y);

            if (dx < 5 && dy < 5) {
                puntoActual++; //Pasa al siguiente punto del camino
            }
        }
    }

    public void verificarColisionRobotLumina() {
        if (robotCentinela == null || lumina == null) return;

        if (robotCentinela.getSprite().getBoundsInParent().intersects(lumina.getSprite().getBoundsInParent())) {
            if (!lumina.estaInvulnerable()) {
                lumina.perderVida();
                actualizarCorazones();
            }
        }
    }
    
    public List<Moneda> getMonedas() {
        return monedas;
    }

    public Fragmentoalma getFragmentoalma() {
        return fragmentoalma;
    }

    public void marcarFragmentoRecogido() {
        if (fragmentoalma != null) {
            fragmentoalma.setRecogido(true);
            fragmentoRecogido = true;
        }
    }
    
    public void detectarSalida() {
    // Activa la salida si el fragmento fue recogido
    if (!salidaActivada && fragmentoRecogido &&
            lumina.getSprite().getBoundsInParent().intersects(zonaSalida.getBoundsInParent())) {
        
            salidaActivada = true;

            if (onSalida != null) {
               onSalida.run();  // Ejecuta la acción de volver a la sala
            }
        }
    }
    
    public void setOnSalida(Runnable onSalida) {
        this.onSalida = onSalida;
    }

    public Enemigos getRobotCentinela() {
        return robotCentinela;
    }

    public Pane getLaberintoPane() {
        return laberintoPane;
    }
    
    public boolean isFragmentoRecogido() {
        return fragmentoRecogido;
    }

    public void setFragmentoRecogido(boolean recogido) {
        this.fragmentoRecogido = recogido;
    }
    
    public Lumina getLumina() {
        return lumina;
    }
    
    //Bucle principal del minijuego
    AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long now) {
            moverRobotPorCamino();
            verificarColisionRobotLumina();
            Colisiones.verificarMonedas(lumina, monedas, laberintoPane);
            detectarSalida();
        }
    };
}
