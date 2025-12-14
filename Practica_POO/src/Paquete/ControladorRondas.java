package Paquete;

import Tormenta.ArrayMapa; // Asumiendo que ArrayMapa es la clase de tu matriz de mapa
import Tormenta.Tormenta;
import javax.swing.JButton;

/**
 * Clase Controladora: Gestiona el flujo de la partida (Tormenta, Movimiento, Batalla).
 * Actúa como intermediario entre la lógica (Partida) y la interfaz (MapaGUI).
 */
public class ControladorRondas {

    private Partida partida;
    private MapaGUI mapaGUI;
    private ArrayMapa mapaData; // El mapa de juego
    private int rondaActual;

    public ControladorRondas(Partida partida, ArrayMapa mapaData) {
        this.partida = partida;
        this.mapaData = mapaData;
        this.rondaActual = 0;
        // Asumo que Partida tiene un método para obtener la dificultad
        System.out.println("Controlador de Rondas inicializado. Dificultad: " + partida.getDificultadPartida()); 
    }

    /**
     * Conecta el controlador al MapaGUI y le añade el ActionListener al botón.
     * Esto debe ser llamado una vez que MapaGUI está inicializada.
     * @param mapaGUI La instancia de la interfaz gráfica del mapa.
     */
    public void setMapaGUI(MapaGUI mapaGUI) {
        this.mapaGUI = mapaGUI;
        
        // 1. Conectar el botón del MapaGUI al método que avanza el juego
        JButton btn = mapaGUI.getBoton();
        if (btn != null) {
            btn.addActionListener(e -> {
                ejecutarSiguienteFase();
            });
        }
        
        // 2. Asegurar el dibujo inicial del mapa
        mapaGUI.actualizar();
    }

    /**
     * Ejecuta el ciclo completo de una fase: Tormenta y transición a Movimiento.
     */
    private void ejecutarSiguienteFase() {
        // Lógica de fin de juego (pendiente de implementar: jugadores restantes, límite de rondas)
        // if (partida.partidaTerminada()) { return; } 

        rondaActual++;
        System.out.println("\n==================================================");
        System.out.println("            INICIANDO RONDA " + rondaActual);
        System.out.println("==================================================");

        // 1. Fase de Tormenta
        faseTormenta();
        
        // 2. Transición a Fase de Movimiento
        // Se llama a MapaGUI para que cambie el botón y habilite la selección de zona.
        mapaGUI.iniciarFaseViaje(); 
    }
    
    /**
     * Ejecuta la lógica de la Tormenta y actualiza la GUI para reflejar las zonas destruidas.
     */
    private void faseTormenta() {
        Tormenta tormenta = new Tormenta(mapaData);
        tormenta.ejecutarRondaDeTormenta();
        mapaGUI.actualizar(); // Refrescar para mostrar la zona destruida
    }

    /**
     * Se llama desde MapaGUI una vez que el jugador humano ha seleccionado su destino 
     * y el movimiento de bots ha sido procesado por Partida.
     * * @param zonaHumano La zona (1-9) elegida por el jugador.
     */
    public void faseMovimientoCompletada(int zonaHumano) {
        
        System.out.println("Fase de movimiento finalizada. Preparando Batalla...");
        
        // 3. Fase de Batalla/Resolución (Lógica futura)
        // Aquí iría la llamada a Partida para resolver combates
        // partida.resolverBatallas();
        
        // 4. Resetear la GUI para esperar la siguiente ronda
        mapaGUI.finalizarFaseViaje();
        mapaGUI.actualizar();
    }
    
    // Permite que MapaGUI acceda a los datos de Partida (ej. zona actual del jugador)
    public Partida getPartida() {
        return partida;
    }
}