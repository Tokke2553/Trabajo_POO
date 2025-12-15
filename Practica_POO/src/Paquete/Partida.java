package Paquete;
import Armas.*;
import Tormenta.*;
import Objetos.*;
import java.util.*;
import Personajes.*;
import javax.swing.JOptionPane;

public class Partida {
    private List<Jugador> jugadores = new ArrayList<>();
    // Estructura original del usuario: Map<Zona, List<Equipo>>
    private Map<Integer, List<List<Jugador>>> equiposPorZona = new HashMap<>();
    private Random random = new Random();
    private ArrayMapa mapa;
    private EliminarZonas tormenta;

    private String dificultadPartida;
    private Multiplicador multiplicador;


    public Partida(ArrayMapa mapa,String dificultad) {
        this.mapa = mapa;
        this.tormenta = new EliminarZonas(mapa);
        
        //Inicializar dificultad
        this.dificultadPartida = dificultad;
        this.multiplicador= new Multiplicador(dificultad);
        System.out.println("Partida creada en dificultad: " + dificultad.toUpperCase());
        
        // Inicializar zonas
        for (int i = 1; i <= 9; i++) {
            equiposPorZona.put(i, new ArrayList<>());
        }
    }

    public void crearJugador(String nombre, ClasePersonaje tipo) {
        Jugador j = new Jugador(nombre, tipo, PoolArmas.armaPara(tipo), Jugador.obtenerObjetoAleatorio(), false );
        jugadores.add(j);
    }

    public void crearBots(int cantidad) {
        for(int i=0; i < cantidad && i < 100; i++) {
            ClasePersonaje tipo = ClasePersonaje.obtenerClases()[random.nextInt(3)];
            float factorDificultad = this.multiplicador.getMultiplicador();
            System.out.println("Creando " + cantidad + " bots. Multiplicador de daño del bot: x" + factorDificultad);
            Jugador bot = new Jugador("Bot_" + (i + 1), tipo,PoolArmas.armaPara(tipo),Jugador.obtenerObjetoAleatorio(),true);
            bot.setDificultadAtaque(factorDificultad);
            jugadores.add(bot); //
         }
    }

    // Método para formar equipos - Sin cambios
    private List<List<Jugador>> formarEquipos(int jugadoresPorEquipo) {
        List<List<Jugador>>equipos = new ArrayList<>();
        List<Jugador> copiaJugadores = new ArrayList<>(jugadores);
        Collections.shuffle(copiaJugadores, random);

        for (int i = 0; i < copiaJugadores.size(); i += jugadoresPorEquipo) {
            List<Jugador> equipo = new ArrayList<>();
            for (int j = i; j < i + jugadoresPorEquipo && j < copiaJugadores.size(); j++) {
                equipo.add(copiaJugadores.get(j));
            }
            equipos.add(equipo);
        }
        return equipos;
    }

    // Método para inicializar equipos en zonas - Sin cambios
    public void inicializarEquipos(List<List<Jugador>> todosLosEquipos) {
        for (int i = 1; i <= 9; i++) {
            equiposPorZona.put(i, new ArrayList<>());
        } 
        for (List<Jugador> equipo : todosLosEquipos) {
            if (!equipo.isEmpty()) {
                int zonaAsignada = random.nextInt(9) + 1;
                equiposPorZona.get(zonaAsignada).add(equipo);

                // Mostrar en consola
                String nombres = "";
                for (Jugador j : equipo) nombres += j.getNombre() + " ";
                System.out.println("Equipo (" + nombres.trim() + ") asignado a Zona " + zonaAsignada);
            }
        }
    }
    
    // MÉTODO ELIMINADO: public void iniciarBatalla(int jugadoresPorEquipo) {}
    
    
    // Simulación de combate entre dos equipos - Sin cambios
    // NOTA: Este método es ahora public para que GestionRonda lo pueda usar.
    public List<Jugador> simularCombate(List<Jugador> equipoA, List<Jugador> equipoB) {
        List<Jugador> todos = new ArrayList<>();
        todos.addAll(equipoA);
        todos.addAll(equipoB);
        int turno = 1; // Variable no usada, pero mantenida por fidelidad.

        while (!equipoA.isEmpty() && !equipoB.isEmpty()) {
            // Eliminar jugadores muertos antes de barajar
            equipoA.removeIf(j -> !j.estadoJugador());
            equipoB.removeIf(j -> !j.estadoJugador());
            
            if(equipoA.isEmpty() || equipoB.isEmpty()) break;

            todos.clear();
            todos.addAll(equipoA);
            todos.addAll(equipoB);
            
            Collections.shuffle(todos, random);
            
            for (Jugador atacante : new ArrayList<>(todos)) {
                if (!atacante.estadoJugador()) continue;
                List<Jugador> objetivo = equipoA.contains(atacante) ? equipoB : equipoA;
                if (objetivo.isEmpty()) break;
                Jugador defensor = objetivo.get(random.nextInt(objetivo.size()));
                atacante.atacar(defensor);
                if (!defensor.estadoJugador()) {
                    objetivo.remove(defensor);
                    // No remover de 'todos' aquí, se limpia al final del bucle interno
                }
            }
            // Limpieza al final del turno
            todos.removeIf(j -> !j.estadoJugador()); 
        }

        if (!equipoA.isEmpty()) return equipoA;
        if (!equipoB.isEmpty()) return equipoB;
        return null;
    }
    
    // --- NUEVOS MÉTODOS PÚBLICOS PARA GESTIONRONDA ---

    /**
     * Reconstruye la lista global 'jugadores' a partir de los equipos que sobrevivieron.
     * Esto limpia a los jugadores que murieron en combate o por eliminación progresiva.
     */
    public void actualizarJugadoresGlobales() {
        jugadores.clear();
        for (List<List<Jugador>> listaEquipos : equiposPorZona.values()) {
            for (List<Jugador> eq : listaEquipos) {
                 // Quitar cualquier jugador que haya muerto en la ronda actual
                eq.removeIf(j -> !j.estadoJugador());
                jugadores.addAll(eq);
            }
        }
    }
    
    // Getters necesarios para que GestionRonda acceda a los datos de Partida
    
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayMapa getMapa() {
        return mapa;
    }
    
    public EliminarZonas getTormenta() {
        return tormenta;
    }

    public Random getRandom() {
        return random;
    }

    public Map<Integer, List<List<Jugador>>> getEquiposPorZona() {
        return equiposPorZona;
    }
    
    // Setter necesario para actualizar las zonas después de la fase de movimiento
    public void setEquiposPorZona(Map<Integer, List<List<Jugador>>> nuevosEquiposPorZona) {
        this.equiposPorZona = nuevosEquiposPorZona;
    }

    // Método auxiliar (mantenido del original)
    private int jugadoresTotales() {
        int total = 0;
        for (List<List<Jugador>> listaEquipos : equiposPorZona.values()) {
            for (List<Jugador> equipo : listaEquipos) {
                total += equipo.size();
            }
        }
        return total;
    }
}