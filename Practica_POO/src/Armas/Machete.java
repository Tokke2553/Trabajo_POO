package Armas;

public class Machete extends Arma{
	public Machete() {
		super("Machete", 80, 1.8f, 0.6f,100);
		
	}
	public String toString() {
        return getNombre() 
             + " (Daño: " + getDanio() 
             + ", Precisión: " + getPrecision() 
             + ", Alcance: " + getDistancia() 
             + ", Tamaño de cargador: " + getCargador() + ")";  
    }
}
