package Personajes;

import Armas.*;

public class Gigante extends ClasePersonaje {
	
	public Gigante() {
		//Valores gigante para la superclase
		super("Gigante", 200, 50, 1, 1.2, 0.65); 
	}
	
	public Arma[] getArmas() {
		
	        return new Arma[]{ new Francotirador(), new Escopeta(), new Arco() };
	}
	
	
}
