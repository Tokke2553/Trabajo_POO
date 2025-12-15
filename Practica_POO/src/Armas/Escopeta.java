package Armas;

public class Escopeta extends Arma{
	public Escopeta() {
		super("Escopeta", 60, 1.4f, 0.6f,10);
	}
		public String toString() {
	        return getNombre() + " (Daño: " + getDanio() + ", Precisión: " + getPrecision() + ", Alcance: " + getDistancia() + ", Tamaño de cargador: " + getCargador() + ")";  
	    }
	}

