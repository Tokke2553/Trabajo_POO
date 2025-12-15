package Objetos;

import paquete.Jugador;

public class MejoraPrecision extends Objeto implements Interfaz_Objetos {

    public MejoraPrecision(float multiplicador) {
    	
        super("mejoraPrecision", 0, 0, 0, multiplicador, 0, 0);
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.mejorarPrecision(getMejora_precision());
    }

    @Override
    public String toString() {
        return getNombre() + " (x" + getMejora_precision() + " precisión)";
    }
}
