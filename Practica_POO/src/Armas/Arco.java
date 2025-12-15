package Armas;

public class Arco extends Arma{
	public Arco() {
		super("Arco", 20, 0.5f, 0.5f,10);
		
	}

	    public String toString() {
	        return getNombre() 
	             + " (Daño: " + getDanio() 
	             + ", Precisión: " + getPrecision() 
	             + ", Alcance: " + getDistancia() 
	             + ", Tamaño de cargador: " + getCargador() + ")";  
	    }
	

}
