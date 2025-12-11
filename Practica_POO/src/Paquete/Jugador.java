package Paquete;
import Armas.*;

import Armas.Arma;

public class Jugador {
	
	    private String nombre;
	    private int vida;

	    public Jugador(String nombre, int vida) {
	        this.nombre = nombre;
	        this.vida = vida;
	    }

	    public int getVida() {
	        return vida;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void recibirDanio(int cantidad) {
	        this.vida -= cantidad;

	        if (this.vida < 0) {
	            this.vida = 0;
	        }

	        System.out.println(nombre + " ha recibido " + cantidad + " de daño. Vida restante: " + vida);
	    }
	    
	    public void atacar(Arma arma, Jugador enemigo) {
	        int danioTotal = (int)(arma.getDanio() * arma.getMultiplicador());
	        enemigo.recibirDanio(danioTotal);

	        System.out.println(nombre + " atacó a " + enemigo.getNombre() +
	                           " con " + arma.getNombre() + " causando " + danioTotal + " de daño.");
	    }
	   
}

