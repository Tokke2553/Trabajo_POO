package Armas;

public class Patito_Goma extends Arma {
	
    public Patito_Goma() {super("Patito de Goma", 90, 2f, 0.7f, 5);}
    
    public String toString() {
    	
        return getNombre() + " (Daño: " + getDanioBase()  + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    
    }


}