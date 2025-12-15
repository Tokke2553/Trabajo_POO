package paquete;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import Armas.*;
import Personajes.*;

public class SeleccionArmas {

    private List<SeleccionPersonaje.Jugador> jugadores;
    private String dificultad;
    private int numeroBots;
    private int indiceJugador = 0;

    private static final Map <Class<? extends ClasePersonaje>, Arma[]> ARMAS = new HashMap<>();
    
    static {
    	
        ARMAS.put(Enano.class, new Arma[]{new Francotirador(), new Patito_Goma(), new Subfusil()});
        ARMAS.put(Normal.class, new Arma[]{new Arco(), new Rifle(), new Escopeta()});
        ARMAS.put(Gigante.class, new Arma[]{new Machete(), new LanzaCohete(), new Fusil()});
    
    }

    public SeleccionArmas(List<SeleccionPersonaje.Jugador> jugadores, String dificultad, int numeroBots) {
    
    	this.jugadores = jugadores;
        this.dificultad = dificultad;
        this.numeroBots = numeroBots;
        mostrarSeleccion();
    
    }

    private void mostrarSeleccion() {
    
    	if (indiceJugador >= jugadores.size()) {
        
    		finalizarSeleccion();
            return;
        }

        SeleccionPersonaje.Jugador j = jugadores.get(indiceJugador);
        Arma[] armas = ARMAS.get(j.clase.getClass());
        
        if (armas == null) {
        
        	JOptionPane.showMessageDialog(null, "Personaje no soportado: " + j.clase.getPersonaje());
            return;
        }

        Object seleccion = JOptionPane.showInputDialog(
            null,
            "Selecciona arma para " + j.nombre + " (" + j.clase.getPersonaje() + "):",
            "Arma - " + j.nombre,
            JOptionPane.QUESTION_MESSAGE,
            null,
            armas,
            armas[0]
        );

        if (seleccion != null) {
        	
            j.arma = (Arma) seleccion;
            indiceJugador++;
            mostrarSeleccion(); 
   
        } else {
        
        	System.exit(0);
        }
    
    }

    private void finalizarSeleccion() {
    
    	for (Frame f : Frame.getFrames()) {
            f.dispose();
        
    	}

        System.out.println("\n=== RESUMEN DE LA PARTIDA ===");
        System.out.println("Dificultad: " + dificultad);
        System.out.println("Bots: " + numeroBots);
        System.out.println("Jugadores humanos:");
        
        for (SeleccionPersonaje.Jugador j : jugadores) {
        	
            System.out.println("  - " + j.nombre + " (" + j.clase.getPersonaje() + ") con " + j.arma.getNombre());
        }

        Tormenta.ArrayMapa mapa = new Tormenta.ArrayMapa();
        mapa.ArrayInicializador();

        Partida partida = new Partida(mapa, dificultad);

        for (SeleccionPersonaje.Jugador j : jugadores) {
            
        	partida.crearJugador(j.nombre, j.clase, j.arma);
        }

        partida.crearBots(numeroBots);

        System.out.println("\n¡INICIANDO BATALLA ROYALE!");
        BattleRoyale batalla = new BattleRoyale(partida.getJugadores());

        // ← CLAVE: Ejecutar en hilo separado
        new Thread(() -> {
            batalla.iniciarBatalla();
        }).start();
        
    }


}