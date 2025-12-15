package Personajes;

public abstract class ClasePersonaje {
	
    protected int vidaMaxima;
    protected int escudoMaxima;
    protected double multiplicadorDanio;
    protected double multiplicadorPrecision;

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getEscudoMaxima() {
        return escudoMaxima;
    }

    public double getMultiplicadorDanio() {
        return multiplicadorDanio;
    }

    public double getMultiplicadorPrecision() {
        return multiplicadorPrecision;
    }

    public abstract String getPersonaje();
    
}