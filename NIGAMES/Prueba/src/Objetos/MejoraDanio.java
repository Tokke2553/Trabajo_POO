package Objetos;

import paquete.Jugador;

public class MejoraDanio extends Objeto implements Interfaz_Objetos {

    public MejoraDanio(float multiplicador) {
    	
        super("mejoraDanio", 0, 0, multiplicador, 0, 0, 0);
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.mejorarDanio(getMejora_danio());
    }

    @Override
    public String toString() {
        return getNombre() + " (x" + getMejora_danio() + " daño)";
    }
}
