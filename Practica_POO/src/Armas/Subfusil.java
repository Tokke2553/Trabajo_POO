package Armas;

public class Subfusil extends Arma{
	public Subfusil() {
		super("Subfusil", 18, 1.4f, 0.7f, 40);
		
	}
	public String toString() {
        return getNombre() + " (Daño: " + getDanio() + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    }
}
