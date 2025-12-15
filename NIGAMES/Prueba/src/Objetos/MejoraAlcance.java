package Objetos;
import paquete.Jugador;

public class MejoraAlcance extends Objeto implements Interfaz_Objetos {

    public MejoraAlcance(float multiplicador) {
        
    	super("mejoraDistancia", 0, 0, 0, 0, 0, multiplicador);
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.mejorarAlcance(getMejora_alcance());
    }

    @Override
    public String toString() {
        return getNombre() + " (x" + getMejora_alcance() + " alcance)";
    }
}
