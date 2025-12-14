package Objetos;
import Paquete.Jugador;
public class Curas extends Objetos implements Interfaz_Objetos {

	public Curas () {
		super("Vendas",100,0,0,0,0,0);
	}
	public void usar(Jugador Jugador) {
		 Jugador.curar(getVida()); //Se usa la funcion implementada en jugador y se coge el valor del getter de vida de nuetro objeto
	}
}
