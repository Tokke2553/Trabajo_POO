package Armas;

public class Patito_Goma extends Arma{
	public Patito_Goma() {
		super("Patito_Goma", 20, 2f, 0.7f,15);
		
	}
	public String toString() {
        return getNombre() 
             + " (Daño: " + getDanio() 
             + ", Precisión: " + getPrecision() 
             + ", Alcance: " + getDistancia() 
             + ", Tamaño de cargador: " + getCargador() + ")";  
    }
}
