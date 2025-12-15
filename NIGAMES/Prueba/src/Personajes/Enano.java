package Personajes;

public class Enano extends ClasePersonaje {
    public Enano() {
    	
        this.vidaMaxima = 75;
        this.escudoMaxima = 100;
        this.multiplicadorDanio = 0.9;
        this.multiplicadorPrecision = 1.3;
    }

    @Override
    public String getPersonaje() {
        return "Enano";
    }
}