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



public Partida(ArrayMapa mapa) {
    this.mapa = mapa;
    this.tormenta = new EliminarZonas(mapa);

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



         Jugador bot = new Jugador("Bot_" + (i + 1), tipo,PoolArmas.armaPara(tipo),Jugador.obtenerObjetoAleatorio(),true);
         jugadores.add(bot);
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



	
	
	


public void iniciarBatalla() {
	System.out.println("Batalla");
	while(jugadores.size()> 1) {
		
		
		
		
	}
	
	
}


private int jugadoresTotales() {
    int total = 0;
    for (List<List<Jugador>> lista : equiposPorZona.values()) {
        total += lista.size();
    }
    return total;
}
}
