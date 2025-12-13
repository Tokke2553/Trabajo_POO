package Personajes;

import Armas.*;

public class Normal extends ClasePersonaje {
	
	public Normal() {
		//Valores normal para la superclase
		super("Normal", 100, 100, 1.1, 1.0, 0.9); 
	}
	
	 public Arma[] getArmas() {
		 	
		 return new Arma[]{ new LanzaCohete(), new Rifle(), new Fusil() };
	  }

}
