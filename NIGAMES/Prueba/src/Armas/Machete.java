package Armas;

public class Machete extends Arma {
    public Machete() {super("Machete", 150, 1.8f, 0.6f, 1);}

    public String toString() {
    	
        return getNombre() + " (Daño: " + getDanioBase()  + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    
    }


}