package Personajes;
import Armas.*;

public class Enano extends ClasePersonaje {
	
	public Enano() {
		//Valores enano para la superclase
		super("Enano", 75, 100, 0.9, 1.3); 
	}
		public Arma[] getArmas() {
	        return new Arma[]{ new Machete(), new Subfusil(), new Patito_Goma() };
	    }
	
	
}
	
