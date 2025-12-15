package paquete;
import Objetos.*;
import Personajes.ClasePersonaje;
import Armas.Arma;

public class JugadorCombate {

    public String nombre;
    public ClasePersonaje clase;
    public Arma arma;
    public int x, y;
    public int vida;
    public int escudo;
    public boolean vivo;
    public boolean esBot;

    // Asociación opcional con Jugador para mejoras e inventario
    public Jugador jugadorBase;

    public JugadorCombate(String nombre, ClasePersonaje clase, Arma arma, int x, int y, boolean esBot) {
        this.nombre = nombre;
        this.clase = clase;
        this.arma = arma;
        this.x = x;
        this.y = y;
        this.vida = clase.getVidaMaxima();
        this.escudo = clase.getEscudoMaxima();
        this.vivo = true;
        this.esBot = esBot;
        this.jugadorBase = new Jugador(nombre, clase.getVidaMaxima()); // Creamos un Jugador asociado
    }

    public void recibirDanio(int danio) {
        if (escudo > 0) {
            if (escudo >= danio) {
                escudo -= danio;
                danio = 0;
            } else {
                danio -= escudo;
                escudo = 0;
            }
        }
        vida -= danio;
        if (vida <= 0) {
            vivo = false;
            vida = 0;
        }
    }
}
