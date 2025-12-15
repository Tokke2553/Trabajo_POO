package Objetos;

public abstract class Objeto implements Interfaz_Objetos{
	//Declaracion de variables
	private String nombre;
	private float vida;
	private float escudo;
	private float mejora_danio;
	private float mejora_precision;
	private int mejora_cargador;
	private float mejora_alcance;
	//Constructor de la clase
	public Objeto(String nombre,float vida, float escudo, float mejora_danio, float mejora_precision, int mejora_cargador, float mejora_alcance) {
		
		this.nombre = nombre;
		this.vida = vida;
		this.escudo = escudo;
		this.mejora_danio = mejora_danio;
		this.mejora_precision = mejora_precision;
		this.mejora_cargador = mejora_cargador;
		this.mejora_alcance= mejora_alcance;
		
	}
	//Getters
	public String getNombre() {
		return nombre;
	}
	
	public float getVida() {
		return vida;
	}

	public float getEscudo() {
		return escudo;
	}

	public float getMejora_danio() {
		return mejora_danio;
	}

	public float getMejora_precision() {
		return mejora_precision;
	}

	public int getMejora_cargador() {
		return mejora_cargador;
	}

	public float getMejora_alcance() {
		return mejora_alcance;
	}	
}


