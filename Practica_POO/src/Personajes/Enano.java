package Personajes;

import Armas.*;

public class Enano extends ClasePersonaje {
	
	public Enano() { 
		//Valores enano para la superclase
		super("Enano", 75, 100, 1.2f, 0.9f, 1.3f,1); 
	}
	
		public Arma[] getArmas() {
			
	        return new Arma[]{ new Machete(), new Subfusil(), new Patito_Goma() };
	    }
	
	
}
	
