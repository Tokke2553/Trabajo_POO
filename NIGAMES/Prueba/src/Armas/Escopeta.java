package Armas;

public class Escopeta extends Arma {
	
    public Escopeta() {super("Escopeta", 50, 1.4f, 0.6f,7);}
    
    public String toString() {
    	
        return getNombre() + " (Daño: " + getDanioBase()  + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    
    }
    
    
}
