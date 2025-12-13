package Paquete;
import Armas.*;
import Objetos.*;
import java.util.*;
import Personajes.*;
public class Partida {


private List<Jugador> jugadores = new ArrayList<>();
private Random random = new Random();


public Partida(List<Jugador> jugadores) {
    this.jugadores = jugadores;
}
public void crearJugador(String nombre, ClasePersonaje tipo) {
	jugadores.add(new Jugador(nombre, tipo.getVida(), tipo.getEscudo(), PoolArmas.armaPara(tipo), Jugador.obtenerObjetoAleatorio(), false ));
	
}
public void crearBots(int cantidad) {
	for(int i=0; i < cantidad && i < 100; i++) {
		
		 ClasePersonaje tipo = ClasePersonaje.values()[random.nextInt(3)];


         jugadores.add(new Jugador("Bot_" + (i + 1), tipo.getVida(),tipo.getEscudo(),PoolArmas.armaPara(tipo),Jugador.obtenerObjetoAleatorio(),true));

     }
	}
	
	
	


public void iniciarBatalla() {
	System.out.println("Batalla");
	while(jugadores.size()> 1) {
		
		
		
		
	}
	
	
}
}
