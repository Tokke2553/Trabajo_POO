package Armas;

public class Rifle extends Arma {
	
    public Rifle() {super("Rifle", 25, 1.1f, 0.9f, 10);}
    
    public String toString() {
    
    	return getNombre() + " (Daño: " + getDanioBase()  + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    
    }


}