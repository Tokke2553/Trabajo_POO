package Paquete;

import java.util.*;
import javax.swing.JOptionPane;
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

    public void ejecutarCicloRonda() {
        System.out.println("\n=== INICIANDO RONDA " + numeroRonda + " ===");

        // 1. Fase de Tormenta
        this.zonaDestruidaRonda = partida.getTormenta().cambioArray();
        System.out.println("Tormenta: Zona destruida: " + zonaDestruidaRonda);

        // 2. Fase de Movimiento
        moverEquipos();

        // 3. Fase de Objetos (desde ronda 2)
        if (numeroRonda >= 2) {
            aplicarFaseObjetos();
        }

        // 4. Fase de Combate
        resolverCombateProgresivo();

        // 5. Actualizar lista global
        partida.actualizarJugadoresGlobales();

        System.out.println("Jugadores restantes: " + partida.getJugadores().size());

        // 6. Comprobar fin de partida
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

    // Mover equipos (bots aleatorios, jugador humano elige)
    private void moverEquipos() {
        Map<Integer, List<List<Jugador>>> equiposActuales = partida.imprimirEquiposPorZona();
        Map<Integer, List<List<Jugador>>> proximosEquiposPorZona = new HashMap<>();
        for (int i = 1; i <= 9; i++) proximosEquiposPorZona.put(i, new ArrayList<>());

        Random random = partida.getRandom();
        int zonaHumano = 5; // Forzar zona para humano en pruebas

        for (Map.Entry<Integer, List<List<Jugador>>> entry : equiposActuales.entrySet()) {
            for (List<Jugador> equipo : entry.getValue()) {
                if (equipo.isEmpty()) continue;

                int nuevaZona;
                Jugador lider = equipo.get(0);

                do {
                    if (lider.isBot()) {
                        nuevaZona = zonaHumano; // bots van a la zona del humano
                    } else {
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

        partida.setEquiposPorZona(proximosEquiposPorZona);
    }

    // Uso de objetos por jugadores vivos
    private void aplicarFaseObjetos() {
        for (Jugador j : partida.getJugadores()) {
            if (j.getObjeto() != null && j.estadoJugador()) {
                j.usarObjeto();
            }
        }
    }

    // Combate progresivo por zona
    private void resolverCombateProgresivo() {
        Map<Integer, List<List<Jugador>>> equiposPorZona = partida.imprimirEquiposPorZona();
        Random random = partida.getRandom();

        for (int zona = 1; zona <= 9; zona++) {
            List<List<Jugador>> listaEquipos = equiposPorZona.get(zona);
            if (listaEquipos == null || listaEquipos.isEmpty()) continue;

            // Limpiar jugadores muertos en cada equipo
            for (List<Jugador> equipo : listaEquipos) {
                equipo.removeIf(j -> !j.estadoJugador());
            }
            listaEquipos.removeIf(List::isEmpty);
            if (listaEquipos.size() < 2) continue;

            System.out.println("\nZona " + zona + ": " + listaEquipos.size() + " equipos");

            // --- Eliminación 50% ---
            List<Jugador> jugadoresEnZona = new ArrayList<>();
            for (List<Jugador> equipo : listaEquipos) {
                jugadoresEnZona.addAll(equipo);
            }

            int n = jugadoresEnZona.size();
            if (n >= 2) {
                int eliminados = n / 2;
                Collections.shuffle(jugadoresEnZona, random);
                System.out.println(" [Fase Progresiva] Jugadores en zona: " + n + ". Eliminando a " + eliminados);

                for (int i = 0; i < eliminados; i++) {
                    Jugador perdedor = jugadoresEnZona.get(i);
                    perdedor.recibirDanio(9999);
                    System.out.println("   -> Eliminado progresivo: " + perdedor.getNombre());
                }
            }

            // --- Combate por equipos ---
            List<List<Jugador>> listaEquiposVivos = new ArrayList<>();
            for (List<Jugador> equipo : listaEquipos) {
                equipo.removeIf(j -> !j.estadoJugador());
                if (!equipo.isEmpty()) listaEquiposVivos.add(equipo);
            }

            Collections.shuffle(listaEquiposVivos, random);
            List<List<Jugador>> ganadoresZona = new ArrayList<>();
            int i = 0;
            while (i < listaEquiposVivos.size() - 1) {
                List<Jugador> equipoA = listaEquiposVivos.get(i);
                List<Jugador> equipoB = listaEquiposVivos.get(i + 1);

                List<Jugador> ganador = partida.simularCombate(equipoA, equipoB);
                if (ganador != null) ganadoresZona.add(ganador);
                i += 2;
            }
            if (i < listaEquiposVivos.size()) {
                ganadoresZona.add(listaEquiposVivos.get(i));
            }

            // Actualizar zona con ganadores
            equiposPorZona.put(zona, ganadoresZona);

            // Mostrar ganadores de la zona
            System.out.print("Ganadores en zona " + zona + ": ");
            for (List<Jugador> eq : ganadoresZona) {
                for (Jugador j : eq) {
                    System.out.print(j.getNombre() + " ");
                }
            }
            System.out.println();
        }
    }
}
