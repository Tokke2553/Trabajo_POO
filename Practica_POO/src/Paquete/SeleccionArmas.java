package Paquete;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SeleccionArmas {

    private JFrame ventanaArmas;
    private List<SeleccionPersonaje.Jugador> jugadores; 
    private String dificultad;
    private int jugadorActualContador = 0;

    private static final Map<String, String[]> ARMAS_POR_PERSONAJE = new HashMap<>();
    static {
        ARMAS_POR_PERSONAJE.put("ENANO", new String[]{"Francotirador", "Patito de goma", "Subfusil"});
        ARMAS_POR_PERSONAJE.put("NORMAL", new String[]{"Arco", "Rifle", "Escopeta"});
        ARMAS_POR_PERSONAJE.put("GIGANTE", new String[]{"Machete", "Lanzacohetes", "Fusil"});
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
            String personaje = jugadorActual.personaje;
            String[] armasDisponibles = ARMAS_POR_PERSONAJE.get(personaje);
            
            if (armasDisponibles == null) {
                JOptionPane.showMessageDialog(ventanaArmas, "Error: Personaje desconocido (" + personaje + ").", "Error", JOptionPane.ERROR_MESSAGE);
                finalizarSeleccion();
                return;
            }
            
            JPanel panelFondo = configurarPanelFondo();
            
            JPanel panelContenido = new JPanel(new GridLayout(armasDisponibles.length + 1, 1, 10, 10));
            panelContenido.setOpaque(false);
            panelContenido.setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300));

            JLabel labelTurno = new JLabel("Turno de: " + jugadorActual.nombre + " (Personaje: " + personaje + ")", SwingConstants.CENTER);
            labelTurno.setForeground(Color.WHITE);
            labelTurno.setFont(new Font("Arial", Font.BOLD, 24));
            
            panelContenido.add(labelTurno);
            
            for (String arma : armasDisponibles) {
                JButton botonArma = new JButton(arma);
                botonArma.setBackground(Color.YELLOW); 
                botonArma.setFont(new Font("Arial", Font.BOLD, 18));
                botonArma.addActionListener(e -> {
                    seleccionarArma(jugadorActual, arma);
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
    
    private void seleccionarArma(SeleccionPersonaje.Jugador jugador, String arma) {
        jugador.arma = arma;
        
        JOptionPane.showMessageDialog(ventanaArmas, jugador.nombre + " ha elegido: " + arma);
        
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