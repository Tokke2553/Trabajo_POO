package Objetos;
import paquete.Jugador;

public class Escudo extends Objeto implements Interfaz_Objetos {

    public Escudo(float cantidadEscudo) {
    	
        super("escudo", 0, cantidadEscudo, 0, 0, 0, 0);
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.implementarEscudo(getEscudo());
    }

    @Override
    public String toString() {
        return getNombre() + " (+ " + getEscudo() + " de escudo)";
    }
}
