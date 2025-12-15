package paquete;

import java.util.Random;

public class ZonaSegura {
	
    private int centroX, centroY;
    private int radio;
    private Random rand = new Random();
    private int tamañoMaximo;

    public ZonaSegura(int tamañoMaximo) {
    	
        this.tamañoMaximo = tamañoMaximo;
        this.radio = tamañoMaximo / 2;
        this.centroX = tamañoMaximo / 2;
        this.centroY = tamañoMaximo / 2;
    }

    public void reducirZona() {
    	
        if (radio > 2) {
            
        	radio = Math.max(2, radio - 2);
            
        	centroX = Math.max(radio, Math.min(tamañoMaximo - radio, centroX + rand.nextInt(5) - 2));
            centroY = Math.max(radio, Math.min(tamañoMaximo - radio, centroY + rand.nextInt(5) - 2));
        
        }
    
    }

    public boolean estaDentro(int x, int y) {
    
    	double distancia = Math.sqrt(Math.pow(x - centroX, 2) + Math.pow(y - centroY, 2));
        return distancia <= radio;
    }

    public int getRadio() {
    	return radio; 
    }
    
    public int getCentroX() {
    	return centroX;
    }
    
    public int getCentroY() {
    	return centroY;
    }
    
    
}
