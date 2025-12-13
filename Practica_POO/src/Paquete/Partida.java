package Paquete;
import Armas.*;
import Tormenta.*;
import Objetos.*;
import java.util.*;
import Personajes.*;
import Tormenta.EliminarZonas;
import Tormenta.array;
public class Partida {


private List<Jugador> jugadores = new ArrayList<>();
private Map<Integer, List<Jugador>> jugadorPorZona = new HashMap<>();
private Random random = new Random();
private array mapa;
private EliminarZonas tormenta;



public Partida(array mapa) {
    this.mapa = mapa;
    this.tormenta = new EliminarZonas(mapa);

    for (int i = 1; i <= 9; i++) {
        jugadorPorZona.put(i, new ArrayList<>());
    }
}


public void crearJugador(String nombre, ClasePersonaje tipo) {
	Jugador j = new Jugador(nombre, tipo.getVida(), tipo.getEscudo(), PoolArmas.armaPara(tipo), Jugador.obtenerObjetoAleatorio(), false );
	 jugadores.add(j);
}



public void crearBots(int cantidad) {
	for(int i=0; i < cantidad && i < 100; i++) {
		
		ClasePersonaje tipo = ClasePersonaje.obtenerClases()[random.nextInt(3)];



         Jugador bot = new Jugador("Bot_" + (i + 1), tipo.getVida(),tipo.getEscudo(),PoolArmas.armaPara(tipo),Jugador.obtenerObjetoAleatorio(),true);
         jugadores.add(bot);
     }
	}
	
	
	


public void iniciarBatalla() {
	System.out.println("Batalla");
	while(jugadores.size()> 1) {
		
		
		
		
	}
	
	
}


private int jugadoresTotales() {
    int total = 0;
    for (List<Jugador> lista : jugadorPorZona.values()) {
        total += lista.size();
    }
    return total;
}
}
