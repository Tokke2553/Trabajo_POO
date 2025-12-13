package Personajes;

import Armas.*;

public class Gigante extends ClasePersonaje {
	
	public Gigante() {
		//Valores gigante para la superclase
		super("Gigante", 200.0f, 50.0f, 1.0f, 1.2f, 0.65f); 
	}
	
	public Arma[] getArmas() {
		
	        return new Arma[]{ new Francotirador(), new Escopeta(), new Arco() };
	}
	
	
}
