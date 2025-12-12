package Personajes;

public class Enano extends ClasePersonaje {
	
	public Enano() {
		//Valores enano para la superclase
		super("Enano", 75, 100, 0.9, 1.3); 
		
		//Añadimos armas a la superclase
		addArma(new Francotirador());
        addArma(new Patito_Goma());
        addArma(new Subfusil());
	
	}
	
}
	
