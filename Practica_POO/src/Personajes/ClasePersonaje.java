package Personajes;

public abstract class ClasePersonaje {

	//Variables de los personajes
	String personaje;
    int vida;
    int escudo;
    double multiDMG;
    double multiPRS;
    
    //Constructor de ClasePersonaje
    public ClasePersonaje(String personaje, int vida, int escudo, double multiDMG, double multiPRS) {
    	this.personaje = personaje;
        this.vida = vida;
        this.escudo = escudo;
        this.multiDMG = multiDMG;
        this.multiPRS = multiPRS;
        
    }

    //Getters estadisticas
    public String getPersonaje() {
		return personaje;
	}

	public int getVida() {
		return vida;
	}

	public int getEscudo() {
		return escudo;
	}

	public double getMultiDMG() {
		return multiDMG;
	}

	public double getMultiPRS() {
		return multiPRS;
	}
	
}
	
	
	
	

	

    
    
    

    
