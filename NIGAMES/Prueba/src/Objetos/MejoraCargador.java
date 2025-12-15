package Objetos;
import paquete.Jugador;

public class MejoraCargador extends Objeto implements Interfaz_Objetos {

    public MejoraCargador(int suma) {
        
    	super("mejoraCargador", 0, 0, 0, 0, suma, 0);
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.mejorarCargador(getMejora_cargador());
    }

    @Override
    public String toString() {
        return getNombre() + " (x" + getMejora_cargador() + " cargador)";
    }
}
