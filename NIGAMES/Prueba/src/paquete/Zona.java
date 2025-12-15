package paquete;

	import java.util.ArrayList;
	import java.util.List;
	import Objetos.*;
	public class Zona {
		
	    private int centroX, centroY;
	    private int radio;
	    private List<JugadorCombate> jugadores;

	    public Zona(int centroX, int centroY, int radio) {
	    	
	        this.centroX = centroX;
	        this.centroY = centroY;
	        this.radio = radio;
	        this.jugadores = new ArrayList<>();
	    
	    }

	    public boolean estaDentro(JugadorCombate j) {
	    
	    	double distancia = Math.sqrt(Math.pow(j.x - centroX, 2) + Math.pow(j.y - centroY, 2));
	        return distancia <= radio;
	    }

	    public void agregarJugador(JugadorCombate j) {
	        if (!jugadores.contains(j)) jugadores.add(j);
	    }

	    public void removerJugador(JugadorCombate j) {
	        jugadores.remove(j);
	    }

	    public List<JugadorCombate> getJugadores() {
	        return jugadores;
	    }

	    public int getCentroX() {
	    	return centroX; 
	    }
	    
	    public int getCentroY() {
	    	return centroY; 
	    }
	    
	    public int getRadio() {
	    	return radio; 
	    }

	    public void reducirRadio(int cantidad) {
	        radio = Math.max(2, radio - cantidad);
	    }
	
}


