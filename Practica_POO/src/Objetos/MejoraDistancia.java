package Objetos;
import Paquete.Jugador;
public class MejoraDistancia extends Objetos implements Interfaz_Objetos {

	public MejoraDistancia() {
		super("Mejora Distancia",0,0,0,0,0,2f);
	}
public void usar(Jugador Jugador) {
		Jugador.mejorarDistancia(getMejora_alcance());
	}
}
