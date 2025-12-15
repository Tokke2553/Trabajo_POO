package Armas;

public class Fusil extends Arma{
	public Fusil() {
		super("Fusil", 20, 0.7f, 0.9f,30);
		
	}
	public String toString() {
        return getNombre() 
             + " (Daño: " + getDanio() 
             + ", Precisión: " + getPrecision() 
             + ", Alcance: " + getDistancia() 
             + ", Tamaño de cargador: " + getCargador() + ")";  
    }
}
