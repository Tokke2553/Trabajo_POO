package Personajes;

public class Normal extends ClasePersonaje {
	
	public Normal() {
		//Valores normal para la superclase
		super("Normal", 100, 100, 1.0, 0.9); 
		
		//Añadimos armas a la superclase
		addArma(new Arco());
        addArma(new Rifle());
        addArma(new Escopeta());
	
	}
	

}
