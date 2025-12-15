package paquete;

import Tormenta.ArrayMapa;
import Personajes.*;
import Armas.*; 
import Objetos.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Partida {
    
    private ArrayMapa mapa;
    private String dificultad;
    private List<JugadorCombate> jugadores;
    private Random rand = new Random();

    public Partida(ArrayMapa mapa, String dificultad) {
        this.mapa = mapa;
        this.dificultad = dificultad;
        this.jugadores = new ArrayList<>();
    }

    public void crearJugador(String nombre, ClasePersonaje clase, Arma arma) {
        int x = rand.nextInt(mapa.getTamano());
        int y = rand.nextInt(mapa.getTamano());
        jugadores.add(new JugadorCombate(nombre, clase, arma, x, y, false));
    }

    public void crearBots(int cantidad) {
        String[] nombresBots = {"Bot"};
        ClasePersonaje[] clases = {new Enano(), new Normal(), new Gigante()};
        Arma[] armas = {new Francotirador(), new Rifle(), new Escopeta(), new Machete(), new Subfusil(), new Patito_Goma(), new Arco(), new LanzaCohete()};

        for (int i = 0; i < cantidad; i++) {
            ClasePersonaje clase = clases[rand.nextInt(clases.length)];
            Arma arma = armas[rand.nextInt(armas.length)];
            String nombre = nombresBots[rand.nextInt(nombresBots.length)] + i;
            int x = rand.nextInt(mapa.getTamano());
            int y = rand.nextInt(mapa.getTamano());
            jugadores.add(new JugadorCombate(nombre, clase, arma, x, y, true));
        }
    }

    public List<JugadorCombate> getJugadores() {
        return new ArrayList<>(jugadores);
    }
}
