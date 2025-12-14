package Paquete;

import java.util.*;
import javax.swing.JOptionPane;
import Tormenta.*; // Asumiendo que esta es la clase de la tormenta
import Personajes.*;

public class GestionRonda {
    private Partida partida;
    private int numeroRonda;
    private int zonaDestruidaRonda;

    public GestionRonda(Partida partida) {
        this.partida = partida;
        this.numeroRonda = 1;
        System.out.println("Controlador de Rondas inicializado.");
    }

    /**
     * Ejecuta el flujo completo de una ronda: Tormenta, Movimiento, Objetos, Combate.
     */
    public void ejecutarCicloRonda() {
        System.out.println("\n=== INICIANDO RONDA " + numeroRonda + " ===");

        // 1. Fase de Tormenta (Destruir una zona y actualizar el mapa)
        this.zonaDestruidaRonda = partida.getTormenta().cambioArray();
        System.out.println("Tormenta: Zona destruida: " + zonaDestruidaRonda);
        
        // 2. Fase de Movimiento (Elección de zona por bots y jugador humano)
        moverEquipos();

        // 3. Fase de Objetos (Solo a partir de la ronda 2)
        if (numeroRonda >= 2) {
            aplicarFaseObjetos();
        }

        // 4. Fase de Combate (Tanto la eliminación del 50% como los combates por equipo)
        resolverCombateProgresivo();

        // 5. Preparación para la siguiente: Limpiar jugadores muertos de la lista global
        partida.actualizarJugadoresGlobales();
        
        System.out.println("Jugadores restantes: " + partida.getJugadores().size());
        
        // 6. Verificar si la partida terminó
        if (partida.getJugadores().size() <= 1) {
             Jugador ganador = partida.getJugadores().isEmpty() ? null : partida.getJugadores().get(0);
             if (ganador != null) {
                System.out.println("¡Ganador: " + ganador.getNombre() + "!");
                JOptionPane.showMessageDialog(null, "¡Ganador: " + ganador.getNombre() + "!");
             } else {
                 System.out.println("No hay ganadores.");
             }
        }

        numeroRonda++;
    }
    
    /**
     * Maneja el movimiento de bots y solicita la zona al jugador humano (como en tu lógica original).
     */
    private void moverEquipos() {
        Map<Integer, List<List<Jugador>>> equiposActuales = partida.getEquiposPorZona();
        Map<Integer, List<List<Jugador>>> proximosEquiposPorZona = new HashMap<>();
        
        for (int i = 1; i <= 9; i++) proximosEquiposPorZona.put(i, new ArrayList<>());
        
        Random random = partida.getRandom();

        for (Map.Entry<Integer, List<List<Jugador>>> entry : equiposActuales.entrySet()) {
            for (List<Jugador> equipo : entry.getValue()) {
                if (equipo.isEmpty()) continue;
                int nuevaZona;
                
                // Asumiendo que el primer jugador del equipo decide por el equipo
                Jugador lider = equipo.get(0); 
                
                do {
                    if (lider.isBot()) {
                        nuevaZona = random.nextInt(9) + 1;
                    } else {
                        // Lógica para pedir zona al jugador humano
                        String input = JOptionPane.showInputDialog(
                                null,
                                lider.getNombre() + ", elige zona (1-9) evitando la destruida " + zonaDestruidaRonda + ":",
                                "Elección de zona (Ronda " + numeroRonda + ")", JOptionPane.QUESTION_MESSAGE
                        );
                        try {
                            nuevaZona = Integer.parseInt(input);
                        } catch (Exception e) {
                            nuevaZona = 1 + random.nextInt(9);
                        }
                    }
                } while (nuevaZona == zonaDestruidaRonda || nuevaZona < 1 || nuevaZona > 9);

                proximosEquiposPorZona.get(nuevaZona).add(equipo);
            }
        }
        
        // Actualizar el mapa de zonas en la partida
        partida.setEquiposPorZona(proximosEquiposPorZona); 
    }


    /**
     * Simula el uso de objetos para todos los jugadores vivos.
     */
    private void aplicarFaseObjetos() {
        for (Jugador j : partida.getJugadores()) {
            if (j.objeto != null && j.estadoJugador()) {
                j.objeto.usar(j);
                System.out.println(j.getNombre() + " usó su objeto: " + j.objeto.getNombre());
                j.objeto = null; // Se consume el objeto
            }
        }
    }

    /**
     * Implementa la lógica de eliminación del 50% por zona, 
     * seguida por los combates por equipos (tu lógica original).
     */
    private void resolverCombateProgresivo() {
        Map<Integer, List<List<Jugador>>> equiposPorZona = partida.getEquiposPorZona();
        Random random = partida.getRandom();

        for (int zona = 1; zona <= 9; zona++) {
            List<List<Jugador>> listaEquipos = equiposPorZona.get(zona);
            
            // 1. Limpiar equipos vacíos y verificar si hay que luchar
            listaEquipos.removeIf(List::isEmpty);
            if (listaEquipos.size() < 2) continue;

            System.out.println("\nZona " + zona + ": " + listaEquipos.size() + " equipos");

            // --- 1.a Fase de Eliminación 50% (La nueva regla) ---
            List<Jugador> jugadoresEnZona = new ArrayList<>();
            for(List<Jugador> equipo : listaEquipos) {
                for(Jugador j : equipo) {
                     if(j.estadoJugador()) jugadoresEnZona.add(j);
                }
            }

            int n = jugadoresEnZona.size();
            if (n >= 2) {
                int eliminadosEnEstaRonda = (int) Math.floor(n / 2.0);
                
                System.out.println(" [Fase Progresiva] Jugadores en zona: " + n + ". Eliminando a " + eliminadosEnEstaRonda);
                Collections.shuffle(jugadoresEnZona, random);
                
                // La mitad perdedora es eliminada instantáneamente
                for (int i = 0; i < eliminadosEnEstaRonda; i++) {
                    Jugador perdedor = jugadoresEnZona.get(i);
                    // Dano letal (simulando que perdieron el duelo progresivo)
                    perdedor.recibirDanio(9999); 
                    System.out.println("   -> Eliminado progresivo: " + perdedor.getNombre());
                }
            }


            // --- 1.b Fase de Combate por Equipos (Tu lógica original) ---
            
            // Recargar listaEquipos, ya que jugadores en su interior pueden haber muerto
            List<List<Jugador>> listaEquiposVivos = new ArrayList<>();
            for(List<Jugador> equipo : listaEquipos) {
                equipo.removeIf(j -> !j.estadoJugador()); // Quitar muertos
                if (!equipo.isEmpty()) listaEquiposVivos.add(equipo);
            }
            
            Collections.shuffle(listaEquiposVivos, random);
            List<List<Jugador>> ganadoresZona = new ArrayList<>();
            int i = 0;
            
            // Mientras haya al menos 2 equipos para luchar
            while (i < listaEquiposVivos.size() - 1) {
                List<Jugador> equipoA = listaEquiposVivos.get(i);
                List<Jugador> equipoB = listaEquiposVivos.get(i + 1);
                
                // Usa el método de combate de la clase Partida
                List<Jugador> ganador = partida.simularCombate(equipoA, equipoB); 
                if (ganador != null) ganadoresZona.add(ganador);
                i += 2;
            }
            
            // Si queda un equipo impar, pasa a la siguiente ronda
            if (i < listaEquiposVivos.size()) ganadoresZona.add(listaEquiposVivos.get(i));

            // El mapa de equipos para la siguiente ronda solo contiene a los ganadores/supervivientes
            equiposPorZona.put(zona, ganadoresZona);
        }
    }
}