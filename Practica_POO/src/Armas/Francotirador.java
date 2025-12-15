package Armas;

public class Francotirador extends Arma{
	public Francotirador() {
		super("Francotirador", 100, 1f, 0.8f,10);
	}
	public String toString() {
        return getNombre() + " (Daño: " + getDanio() + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    }
}
