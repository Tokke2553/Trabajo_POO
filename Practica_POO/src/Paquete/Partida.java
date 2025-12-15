package Paquete;

import Armas.*;
import Tormenta.*;
import Objetos.*;
import java.util.*;
import Personajes.*;

public class Partida {
    private List<Jugador> jugadores = new ArrayList<>();
    private Map<Integer, List<List<Jugador>>> equiposPorZona = new HashMap<>();
    private Random random = new Random();
    private ArrayMapa mapa;
    private EliminarZonas tormenta;

    private String dificultadPartida;
    private Multiplicador multiplicador;

    public Partida(ArrayMapa mapa, String dificultad) {
        this.mapa = mapa;
        this.tormenta = new EliminarZonas(mapa);

        this.dificultadPartida = dificultad;
        this.multiplicador = new Multiplicador(dificultad);
        System.out.println("Partida creada en dificultad: " + dificultad.toUpperCase());

        for (int i = 1; i <= 9; i++) {
            equiposPorZona.put(i, new ArrayList<>());
        }
    }

    public void crearJugador(String nombre, ClasePersonaje tipo) {
        Jugador j = new Jugador(nombre, tipo, PoolArmas.armaPara(tipo), Jugador.obtenerObjetoAleatorio(), false);
        jugadores.add(j);
        System.out.println("Jugador creado: " + nombre + " como " + tipo.getPersonaje() + " y el arma " + j.getArma());

    }

    public void crearBots(int cantidad) {
        for (int i = 0; i < cantidad && i < 100; i++) {
            ClasePersonaje tipo = ClasePersonaje.obtenerClases()[random.nextInt(3)];
            float factorDificultad = this.multiplicador.getMultiplicador();
            Jugador bot = new Jugador("Bot_" + (i + 1), tipo, PoolArmas.armaPara(tipo), Jugador.obtenerObjetoAleatorio(), true);
            bot.setDificultadAtaque(factorDificultad);
            jugadores.add(bot);
            System.out.println("Creando " + bot.getNombre() + ". Multiplicador de daño del bot: x" + factorDificultad+ " y el arma: " + bot.getArma());
        }
    }

    public List<List<Jugador>> formarEquipos(int jugadoresPorEquipo) {
        List<List<Jugador>> equipos = new ArrayList<>();
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

    public void inicializarEquipos(List<List<Jugador>> todosLosEquipos) {
        for (int i = 1; i <= 9; i++) {
            equiposPorZona.put(i, new ArrayList<>());
        }
        for (List<Jugador> equipo : todosLosEquipos) {
            if (!equipo.isEmpty()) {
                int zonaAsignada = random.nextInt(9) + 1;
                equiposPorZona.get(zonaAsignada).add(new ArrayList<>(equipo));

                String nombres = "";
                for (Jugador j : equipo) nombres += j.getNombre() + " ";
                System.out.println("Equipo (" + nombres.trim() + ") asignado a Zona " + zonaAsignada);
            }
        }
    }

    public Map<Integer, List<List<Jugador>>> imprimirEquiposPorZona() {
        System.out.println("\n=== Jugadores por zona ===");
        for (int zona : equiposPorZona.keySet()) {
            System.out.println("Zona " + zona + ":");
            for (List<Jugador> equipo : equiposPorZona.get(zona)) {
                for (Jugador j : equipo) {
                    System.out.println(" - " + j.getNombre());
                }
            }
        }
        System.out.println("=========================");
        return equiposPorZona;
    }

    public void ejecutarRonda() {
        // 1. Tormenta
        System.out.println("\n=== Tormenta de esta ronda ===");
        for (int i = 0; i < 2; i++) {
            tormenta.cambioArray();
        }
        mapa.printArray();
        tormenta.ciudadesSeguras();

        // 2. Mostrar equipos antes de combate
        imprimirEquiposPorZona();

        // 3. Combates
        System.out.println("\n=== Combates de esta ronda ===");
        for (int zona : equiposPorZona.keySet()) {
            List<List<Jugador>> equiposEnZona = equiposPorZona.get(zona);
            if (equiposEnZona.isEmpty()) continue;

            // Combinar todos los jugadores de la zona
            List<Jugador> todosEnZona = new ArrayList<>();
            for (List<Jugador> equipo : equiposEnZona) {
                todosEnZona.addAll(equipo);
            }

            // Combate hasta que quede un solo jugador
            while (todosEnZona.size() > 1) {
                Collections.shuffle(todosEnZona, random);

                for (Jugador atacante : new ArrayList<>(todosEnZona)) {
                    if (!atacante.estadoJugador()) continue;

                    List<Jugador> posiblesObjetivos = new ArrayList<>(todosEnZona);
                    posiblesObjetivos.remove(atacante);
                    if (posiblesObjetivos.isEmpty()) break;

                    Jugador defensor = posiblesObjetivos.get(random.nextInt(posiblesObjetivos.size()));
                    atacante.atacar(defensor);

                    if (!defensor.estadoJugador()) {
                        System.out.println(defensor.getNombre() + " ha muerto!");
                    }
                }

                todosEnZona.removeIf(j -> !j.estadoJugador());
            }

            // Reorganizar zona con el ganador restante
            equiposEnZona.clear();
            if (!todosEnZona.isEmpty()) {
                List<Jugador> ganador = new ArrayList<>();
                ganador.add(todosEnZona.get(0));
                equiposEnZona.add(ganador);
                System.out.println("Ganador en Zona " + zona + ": " + todosEnZona.get(0).getNombre());
            }
        }

        // 4. Actualizar lista global de jugadores
        actualizarJugadoresGlobales();
    }

    public void actualizarJugadoresGlobales() {
        jugadores.clear();
        for (List<List<Jugador>> listaEquipos : equiposPorZona.values()) {
            for (List<Jugador> eq : listaEquipos) {
                eq.removeIf(j -> !j.estadoJugador());
                jugadores.addAll(eq);
            }
        }
    }
    
    public List<Jugador> simularCombate(List<Jugador> equipoA, List<Jugador> equipoB) {
        Random rand = this.random;
        List<Jugador> todos = new ArrayList<>();
        todos.addAll(equipoA);
        todos.addAll(equipoB);

        while (!equipoA.isEmpty() && !equipoB.isEmpty()) {
            equipoA.removeIf(j -> !j.estadoJugador());
            equipoB.removeIf(j -> !j.estadoJugador());

            if (equipoA.isEmpty() || equipoB.isEmpty()) break;

            Collections.shuffle(todos, rand);

            for (Jugador atacante : new ArrayList<>(todos)) {
                if (!atacante.estadoJugador()) continue;
                List<Jugador> objetivo = equipoA.contains(atacante) ? equipoB : equipoA;
                if (objetivo.isEmpty()) break;
                Jugador defensor = objetivo.get(rand.nextInt(objetivo.size()));
                atacante.atacar(defensor);
            }
        }

        if (!equipoA.isEmpty()) return equipoA;
        if (!equipoB.isEmpty()) return equipoB;
        return null;
    }
    
    public void setEquiposPorZona(Map<Integer, List<List<Jugador>>> nuevosEquiposPorZona) {
        this.equiposPorZona = nuevosEquiposPorZona;
    }
    public Random getRandom() {
        return random;
    }
    public List<Jugador> getJugadores() { 
    	return jugadores;
    	}
    public ArrayMapa getMapa() { 
    	return mapa; 
    	}
    public EliminarZonas getTormenta() { 
    	return tormenta; 
    	}
}
