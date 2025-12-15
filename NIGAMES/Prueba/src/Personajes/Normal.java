package Personajes;

public class Normal extends ClasePersonaje {
    public Normal() {
    	
        this.vidaMaxima = 100;
        this.escudoMaxima = 100;
        this.multiplicadorDanio = 1.0;
        this.multiplicadorPrecision = 1;
    }

    @Override
    public String getPersonaje() {
        return "Normal";
    }
}