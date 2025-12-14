package Personajes;

import Armas.*;

public class Normal extends ClasePersonaje {
	
	public Normal() {
		//Valores normal para la superclase
		super("Normal", 100.0f,100.0f, 100.0f, 1.1f, 1.0f, 0.9f,20); 
	}
	
	 public Arma[] getArmas() {
		 	
		 return new Arma[]{ new LanzaCohete(), new Rifle(), new Fusil() };
	  }

}
