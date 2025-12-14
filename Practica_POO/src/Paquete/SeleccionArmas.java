package Paquete;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import Armas.*;
import Personajes.*;
public class SeleccionArmas {

    private JFrame ventanaArmas;
    private List<SeleccionPersonaje.Jugador> jugadores; 
    private String dificultad;
    private int jugadorActualContador = 0;

    private static final Map<Class<? extends ClasePersonaje>, Arma[]> ARMAS_POR_PERSONAJE = new HashMap<>();
    static {

        ARMAS_POR_PERSONAJE.put(Enano.class, new Arma[] {new Machete(), new Subfusil(), new Patito_Goma()});
        ARMAS_POR_PERSONAJE.put(Normal.class, new Arma[]{new LanzaCohete(), new Rifle(), new Fusil()});
        ARMAS_POR_PERSONAJE.put(Gigante.class, new Arma[]{new Francotirador(), new Escopeta(), new Arco()});
    }


    public SeleccionArmas(List<SeleccionPersonaje.Jugador> jugadores, String dificultad) {
        this.jugadores = jugadores;
        this.dificultad = dificultad;

        ventanaArmas = new JFrame("Selección de Armas - Dificultad: " + dificultad);
        ventanaArmas.setSize(800, 600);
        ventanaArmas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaArmas.setLocationRelativeTo(null);
        ventanaArmas.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        mostrarVentanaSeleccion();
    }
    
    private JPanel configurarPanelFondo() {
        JPanel panelFondo = new JPanel() {
            private final ImageIcon fondo = new ImageIcon(
                    SeleccionArmas.class.getResource("fondo.jpg")
            );

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new BorderLayout());
        return panelFondo;
    }


    private void mostrarVentanaSeleccion() {
        if (jugadorActualContador < jugadores.size()) {
            SeleccionPersonaje.Jugador jugadorActual = jugadores.get(jugadorActualContador);
            ClasePersonaje clase = jugadorActual.clase;
            
            if (clase == null) {
                JOptionPane.showMessageDialog(ventanaArmas, "Error: Clase de personaje no asignada.", "Error", JOptionPane.ERROR_MESSAGE);
                finalizarSeleccion();
                return;
            }

            Arma[] armasDisponibles = ARMAS_POR_PERSONAJE.get(clase.getClass());
            
            if (armasDisponibles == null) {
                JOptionPane.showMessageDialog(ventanaArmas, "Error: Personaje desconocido (" + clase.getPersonaje() + ").", "Error", JOptionPane.ERROR_MESSAGE);
                finalizarSeleccion();
                return;
            }            
            JPanel panelFondo = configurarPanelFondo();
            
            JPanel panelContenido = new JPanel(new GridLayout(armasDisponibles.length + 1, 1, 10, 10));
            panelContenido.setOpaque(false);
            panelContenido.setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300));

            JLabel labelTurno = new JLabel("Turno de: " + jugadorActual.nombre + " (Personaje: " + clase.getPersonaje() + ")", SwingConstants.CENTER);
            labelTurno.setForeground(Color.WHITE);
            labelTurno.setFont(new Font("Arial", Font.BOLD, 24));
            
            panelContenido.add(labelTurno);
            
            for (Arma arma : armasDisponibles) {
                JButton botonArma = new JButton(arma.getNombre());
                botonArma.setBackground(Color.YELLOW); 
                botonArma.setFont(new Font("Arial", Font.BOLD, 18));
                botonArma.addActionListener(e -> {seleccionarArma(jugadorActual, arma);
                });
                panelContenido.add(botonArma);
            }
            
            panelFondo.add(panelContenido, BorderLayout.CENTER);
            
            ventanaArmas.setContentPane(panelFondo);
            ventanaArmas.setTitle("Selección de Armas - Turno de: " + jugadorActual.nombre);
            ventanaArmas.revalidate(); 
            ventanaArmas.repaint();
            ventanaArmas.setVisible(true);
            
        } else {
            finalizarSeleccion();
        }
    }
    
    private void seleccionarArma(SeleccionPersonaje.Jugador jugador, Arma arma) {
     
    	jugador.arma = arma;
        JOptionPane.showMessageDialog(ventanaArmas, jugador.nombre + " ha elegido: " + arma.getNombre());
        
        jugadorActualContador++;
        mostrarVentanaSeleccion(); 
    }

    private void finalizarSeleccion() {
        ventanaArmas.setVisible(false);
        ventanaArmas.dispose();
        
        StringBuilder resumen = new StringBuilder("Partida iniciandose...\n");
        resumen.append("Dificultad del juego: ").append(dificultad).append("\n");
        resumen.append(" Resumen del Equipo \n");
        
        for (SeleccionPersonaje.Jugador j : jugadores) {
            resumen.append(j.toString()).append("\n");
        }
        
        JOptionPane.showMessageDialog(null, resumen.toString(), "Inicio del Juego", JOptionPane.INFORMATION_MESSAGE);
    }
}