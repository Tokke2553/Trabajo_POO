package Armas;

public class Arma {
	private String nombre;
	private int danio;
	private float distancia;
	private float multiplicador;
	private int cargador;
	
	public Arma(String nombre, int danio, float distancia, float multiplicador, int cargador) {
		this.nombre= nombre;
		this.danio = danio;
		this.distancia = distancia;
		this.multiplicador = multiplicador;
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
	
	public float getMultiplicador() {
		return multiplicador;
		
	}
	public int getCargador() {
		return cargador;
		
		
	}
}
