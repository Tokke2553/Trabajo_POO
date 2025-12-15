package Armas;

public class Francotirador extends Arma {
	
    public Francotirador() {super("Francotirador", 80, 1f, 0.8f, 3);}
    
    public String toString() {
    	
        return getNombre() + " (Daño: " + getDanioBase()  + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    
    }
   
    
}