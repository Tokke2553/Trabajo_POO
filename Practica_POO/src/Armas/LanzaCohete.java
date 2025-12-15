package Armas;

public class LanzaCohete extends Arma {
	public LanzaCohete() {
		super("LanzaCohete", 150, 0.6f, 0.4f,8);
		
	}
	public String toString() {
        return getNombre() + " (Daño: " + getDanio() + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
    }
}
