package Armas;

public class Rifle extends Arma{
	public Rifle() {
		super("Rifle", 35, 1.1f, 0.9f, 30);
		
	}
	public String toString() {
        return getNombre() + " (Daño: " + getDanio() + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    }
}
