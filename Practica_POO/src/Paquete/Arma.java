package Paquete;

public class Arma {
	private String name;
	private int damage;
	
	public Arma(String name, int damage) {
		this.name= name;
		this.damage = damage;
		
		
	}
	
	public String getName() {
		return name;
		
	}
	public int getDamage() {
		return damage;
		
	}
}
