package Objetos;
import paquete.Jugador;

public class Curas extends Objeto implements Interfaz_Objetos {

    public Curas(float cantidadVida) {
    	
        super("cura", cantidadVida, 0, 0, 0, 0, 0);
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.curar(getVida());
    }

    @Override
    public String toString() {
        return getNombre() + " (+ " + getVida() + " de vida)";
    }
}
