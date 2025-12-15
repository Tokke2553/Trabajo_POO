package Armas;

public class LanzaCohete extends Arma {
	
    public LanzaCohete() {super("Lanzacohetes", 180, 0.6f, 0.3f, 1);}
    
    public String toString() {
    
    	return getNombre() + " (Daño: " + getDanioBase()  + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    
    }


}