package Personajes;

public class Gigante extends ClasePersonaje {
    public Gigante() {
    	
        this.vidaMaxima = 200;
        this.escudoMaxima = 50;
        this.multiplicadorDanio = 1.2;
        this.multiplicadorPrecision = 0.80;
    }

    @Override
    public String getPersonaje() {
        return "Gigante";
    }
}