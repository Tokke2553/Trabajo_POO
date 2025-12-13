package Objetos;
import Paquete.Jugador;

public class MejoraPrecision extends Objetos implements Interfaz_Objetos{

	public MejoraPrecision() {			
		super("Mejora Precision",0,0,0,1.35f,0,0);
	}
public void usar(Jugador Jugador) {
		Jugador.mejorarCargador(getMejora_cargador());
	}
}
