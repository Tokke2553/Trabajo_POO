package Armas;

public class Subfusil extends Arma {
	
    public Subfusil() {super("Subfusil", 18, 1.4f, 0.65f, 30);}
    
    public String toString() {
    
    	return getNombre() + " (Daño: " + getDanioBase()  + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    
    }


}
