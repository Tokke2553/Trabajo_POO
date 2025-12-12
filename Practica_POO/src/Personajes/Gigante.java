package Personajes;

public class Gigante extends ClasePersonaje {
	
	public Gigante() {
		//Valores gigante para la superclase
		super("Gigante", 200, 50, 1.2, 0.65); 
		
		//Añadimos armas a la superclase
		addArma(new Machete());
        addArma(new LanzaCohete());
        addArma(new Fusil());
	
	}
	
}
