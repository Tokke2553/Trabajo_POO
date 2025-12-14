package Objetos;

public abstract class Objetos implements Interfaz_Objetos{
	
	private String nombre;
	private float vida;
	private float escudo;
	private float mejora_daño;
	private float mejora_precision;
	private float mejora_cargador;
	private float mejora_alcance;
	
	public Objetos(String nombre,float vida, float escudo, float mejora_daño, float mejora_precision, float mejora_cargador, float mejora_alcance) {
		
		this.nombre = nombre;
		this.vida = vida;
		this.escudo = escudo;
		this.mejora_daño = mejora_daño;
		this.mejora_precision = mejora_precision;
		this.mejora_cargador = mejora_cargador;
		this.mejora_alcance= mejora_alcance;
		}

	public String getNombre() {
		return nombre;
	}
	
	public float getVida() {
		return vida;
	}

	public float getEscudo() {
		return escudo;
	}

	public float getMejora_daño() {
		return mejora_daño;
	}

	public float getMejora_precision() {
		return mejora_precision;
	}

	public float getMejora_cargador() {
		return mejora_cargador;
	}

	public float getMejora_alcance() {
		return mejora_alcance;
	}	
}
