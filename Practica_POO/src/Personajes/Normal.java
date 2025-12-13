package Personajes;

import Armas.*;

public class Normal extends ClasePersonaje {
	
	public Normal() {
		//Valores normal para la superclase
		super("Normal", 100, 100, 1.1f, 1.0f, 0.9f,1); 
	}
	
	 public Arma[] getArmas() {
		 	
		 return new Arma[]{ new LanzaCohete(), new Rifle(), new Fusil() };
	  }

}
