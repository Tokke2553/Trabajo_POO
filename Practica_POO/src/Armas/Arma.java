package Armas;

public class Arma {
	private String nombre;
	private int danio;
	private float distancia;
	private float precision;
	private int cargador;
	
	public Arma(String nombre, int danio, float distancia, float precision, int cargador) {
		this.nombre= nombre;
		this.danio = danio;
		this.distancia = distancia;
		this.precision = precision;
		this.cargador = cargador;
	}
	
	
	public String getNombre() {
		return nombre;
		
	}
	public int getDanio() {
		return danio;
		
	}
	
	public float getDistancia() {
		return distancia;
		
	}
	
	public float getPrecision() {
		return precision;
		
	}
	public int getCargador() {
		return cargador;
	}	
		
	public float calcularDanio() {
		if (cargador <= 0) {
			return 0;
			
		}
		cargador--;
		return (danio*precision);
	}
		
	
}
