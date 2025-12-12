package Personajes;

public abstract class ClasePersonaje {

	String personaje;
    int vida;
    int escudo;
    double multiDMG;
    double multiPRS;
    
    public ClasePersonaje(String personaje, int vida, int escudo, double multiDMG, double multiPRS) {
    	this.personaje = personaje;
        this.vida = vida;
        this.escudo = escudo;
        this.multiDMG = multiDMG;
        this.multiPRS = multiPRS;
        
    }

  
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
	
	
	
	

	

    
    
    

    
