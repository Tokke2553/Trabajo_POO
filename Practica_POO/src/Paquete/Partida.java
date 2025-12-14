package Paquete;
import Armas.*;
import Tormenta.*;
import Objetos.*;
import java.util.*;
import javax.swing.JOptionPane;
import Personajes.*;

public class Partida {


private List<Jugador> jugadores = new ArrayList<>();
private Map<Integer, List<List<Jugador>>> equiposPorZona = new HashMap<>();
private Random random = new Random();
private ArrayMapa mapa;
private EliminarZonas tormenta;

private String dificultadPartida;
private Multiplicador multiplicador;


private int ronda = 1;

public Partida(ArrayMapa mapa,String dificultad) {
    this.mapa = mapa;
    this.tormenta = new EliminarZonas(mapa);
    
    //Inicializar dificultad
    this.dificultadPartida = dificultad;
    this.multiplicador= new Multiplicador(dificultad);
    System.out.println("Partida creada en dificultad: " + dificultad.toUpperCase());
    // ----------------------------------
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

     }
}



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



	
	
	


public void iniciarBatalla(int jugadoresPorEquipo) {
    List<List<Jugador>> equipos = formarEquipos(jugadoresPorEquipo);
    inicializarEquipos(equipos);

    while (jugadores.size() > 1) {
        System.out.println("\n--- Ronda " + ronda + " ---");

        // Destruir zona aleatoria
        int zonaDestruida = tormenta.cambioArray();
        System.out.println("Zona destruida: " + zonaDestruida);

        // Elección de zona por jugadores vivos
        Map<Integer, List<List<Jugador>>> proximosEquiposPorZona = new HashMap<>();
        for (int i = 1; i <= 9; i++) proximosEquiposPorZona.put(i, new ArrayList<>());

        for (Map.Entry<Integer, List<List<Jugador>>> entry : equiposPorZona.entrySet()) {
            for (List<Jugador> equipo : entry.getValue()) {
                if (equipo.isEmpty()) continue;
                int nuevaZona;
                do {
                    if (equipo.get(0).isBot()) {
                        nuevaZona = random.nextInt(9) + 1;
                    } else {
                        String input = JOptionPane.showInputDialog(
                                null,
                                equipo.get(0).getNombre() + ", elige zona (1-9) evitando la destruida " + zonaDestruida + ":",
                                "Elección de zona", JOptionPane.QUESTION_MESSAGE
                        );
                        try {
                            nuevaZona = Integer.parseInt(input);
                        } catch (Exception e) {
                            nuevaZona = 1 + random.nextInt(9);
                        }
                    }
                } while (nuevaZona == zonaDestruida || nuevaZona < 1 || nuevaZona > 9);

                proximosEquiposPorZona.get(nuevaZona).add(equipo);
            }
        }

        equiposPorZona = proximosEquiposPorZona;

        // Combates por zona
        for (int zona = 1; zona <= 9; zona++) {
            List<List<Jugador>> listaEquipos = equiposPorZona.get(zona);
            listaEquipos.removeIf(e -> e.isEmpty());
            if (listaEquipos.isEmpty()) continue;

            System.out.println("\nZona " + zona + ": " + listaEquipos.size() + " equipos");

            // Aplicar objetos a partir de ronda > 1
            if (ronda > 1) {
                for (List<Jugador> eq : listaEquipos) {
                    for (Jugador j : eq) {
                        if (j.objeto != null) {
                            j.objeto.usar(j);
                            System.out.println(j.getNombre() + " usó " + j.objeto.getNombre());
                            j.objeto = null;
                        }
                    }
                }
            }

            // Combates entre equipos
            Collections.shuffle(listaEquipos, random);
            List<List<Jugador>> ganadoresZona = new ArrayList<>();
            int i = 0;
            while (i < listaEquipos.size() - 1) {
                List<Jugador> equipoA = listaEquipos.get(i);
                List<Jugador> equipoB = listaEquipos.get(i + 1);
                List<Jugador> ganador = simularCombate(equipoA, equipoB);
                if (ganador != null) ganadoresZona.add(ganador);
                i += 2;
            }
            if (i < listaEquipos.size()) ganadoresZona.add(listaEquipos.get(i));

            equiposPorZona.put(zona, ganadoresZona);
        }

        // Actualizar lista global de jugadores
        jugadores.clear();
        for (List<List<Jugador>> listaEquipos : equiposPorZona.values()) {
            for (List<Jugador> eq : listaEquipos) {
                jugadores.addAll(eq);
            }
        }

        System.out.println("Jugadores restantes: " + jugadores.size());
        ronda++;
    }

    if (!jugadores.isEmpty()) {
        System.out.println("¡Ganador: " + jugadores.get(0).getNombre() + "!");
        JOptionPane.showMessageDialog(null, "¡Ganador: " + jugadores.get(0).getNombre() + "!");
    } else {
        System.out.println("No hay ganadores.");
    }
}







//Simulación de combate entre dos equipos
private List<Jugador> simularCombate(List<Jugador> equipoA, List<Jugador> equipoB) {
    List<Jugador> todos = new ArrayList<>();
    todos.addAll(equipoA);
    todos.addAll(equipoB);
    int turno = 1;

    while (!equipoA.isEmpty() && !equipoB.isEmpty()) {
        Collections.shuffle(todos, random);
        for (Jugador atacante : new ArrayList<>(todos)) {
            if (!atacante.estadoJugador()) continue;
            List<Jugador> objetivo = equipoA.contains(atacante) ? equipoB : equipoA;
            if (objetivo.isEmpty()) break;
            Jugador defensor = objetivo.get(random.nextInt(objetivo.size()));
            atacante.atacar(defensor);
            if (!defensor.estadoJugador()) {
                objetivo.remove(defensor);
                todos.remove(defensor);
            }
        }
        todos.removeIf(j -> !j.estadoJugador());
    }

    if (!equipoA.isEmpty()) return equipoA;
    if (!equipoB.isEmpty()) return equipoB;
    return null;
}




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