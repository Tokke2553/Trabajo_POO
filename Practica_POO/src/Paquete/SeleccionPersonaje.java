package Paquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SeleccionPersonaje {

    private JFrame ventana;
    private int numJugadores = 0; 
    private List<Jugador> jugadores; 
    private int jugadorActualIndex = 0; 

    private static class Jugador {
        String nombre;
        String personaje;

        public Jugador(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return "Nombre: " + nombre + ", Personaje: " + personaje;
        }
    }

    public SeleccionPersonaje() {
        ventana = new JFrame("Selecciona tu personaje");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        jugadores = new ArrayList<>();
        
        configurarInterfaz(); 
        
        mostrar();
        
        iniciarProcesoDeSeleccion();
        
        if (!jugadores.isEmpty()) {
            mostrarInfoJugadorActual(); 
        }
    }
    
    // --- LÓGICA DE JUEGO ---

    private void iniciarProcesoDeSeleccion() {
        if (preguntarModoJuego()) {
            pedirNombresJugadores();
        } else {
            ventana.dispose(); 
            System.exit(0);
        }
    }
    
    private boolean preguntarModoJuego() {
        String[] modos = {"Solo (1)", "Duo (2)", "Squad (4)"};
        JComboBox<String> modoSelector = new JComboBox<>(modos);
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Selecciona el modo de juego:"));
        panel.add(modoSelector);

        int resultado = JOptionPane.showConfirmDialog(
            ventana, 
            panel, 
            "Modo de Juego", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            int seleccion = modoSelector.getSelectedIndex();
            
            switch (seleccion) {
                case 0: 
                    numJugadores = 1;
                    break;
                case 1: 
                    numJugadores = 2;
                    break;
                case 2: 
                    numJugadores = 4;
                    break;
                default:
                    return false; 
            }
            return true;
        } else {
            return false;
        }
    }


    private void pedirNombresJugadores() {
        for (int i = 0; i < numJugadores; i++) {
            String nombre = JOptionPane.showInputDialog(
                    ventana, 
                    "Introduce el nombre del Jugador " + (i + 1) + ":",
                    "Nombre de Jugador",
                    JOptionPane.PLAIN_MESSAGE
            );
            
            if (nombre == null || nombre.trim().isEmpty()) {
                nombre = "Jugador " + (i + 1);
            }
            
            jugadores.add(new Jugador(nombre));
        }
    }
    
    private void mostrarInfoJugadorActual() {
        ventana.setTitle("Selección de Personaje - Turno de: " + jugadores.get(jugadorActualIndex).nombre);
    }
    
    private void personajeSeleccionado(String personaje) {
        jugadores.get(jugadorActualIndex).personaje = personaje;
        
        JOptionPane.showMessageDialog(ventana, "¡" + jugadores.get(jugadorActualIndex).nombre + " ha seleccionado a: " + personaje + "!");

        jugadorActualIndex++;

        if (jugadorActualIndex < numJugadores) {
            mostrarInfoJugadorActual();
        } else {
            finalizarSeleccion();
        }
    }

    private void finalizarSeleccion() {
        ventana.setVisible(false);
        ventana.dispose(); 
        
        StringBuilder resumen = new StringBuilder("¡Selección de personajes completada!\n\n");
        for (Jugador j : jugadores) {
            resumen.append(j.toString()).append("\n");
        }
        
        JOptionPane.showMessageDialog(null, resumen.toString(), "Resumen del Equipo", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    private void configurarInterfaz() {
        
        JPanel panelFondo = new JPanel() {
            private final ImageIcon fondo = new ImageIcon(
                    SeleccionPersonaje.class.getResource("fondo.jpg")
            );

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new BorderLayout());
        ventana.setContentPane(panelFondo);
        
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 3, 50, 0)); 
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); 

        JPanel panelColumna1 = new JPanel(new BorderLayout(0, 10));
        panelColumna1.setOpaque(false);
        
        ImageIcon img1 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje2.jpg"));
        Image escala1 = img1.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
        JButton boton1 = new JButton(new ImageIcon(escala1));
        boton1.setBorderPainted(false);
        boton1.setContentAreaFilled(false);
        boton1.setFocusPainted(false);
        boton1.addActionListener(e -> personajeSeleccionado("ENANO")); 

        JButton btnCaract1 = new JButton("CARACTERISTICAS ENANO");
        btnCaract1.setPreferredSize(new Dimension(150, 75));
        btnCaract1.setBackground(Color.YELLOW);
        btnCaract1.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventana, "ENANO:\n \n- Vida: 75HP\n- Escudo: 100SP\n- Multiplicador daño: 0.9X\n- Multiplicador precisión: 1.3X\n \n - ARMAS: Francotirador, Patito de goma, Subfusil");
        });
        
        panelColumna1.add(boton1, BorderLayout.CENTER);
        panelColumna1.add(btnCaract1, BorderLayout.SOUTH);
        
        JPanel panelColumna2 = new JPanel(new BorderLayout(0, 10));
        panelColumna2.setOpaque(false);

        ImageIcon img2 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje1.jpg"));
        Image escala2 = img2.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
        JButton boton2 = new JButton(new ImageIcon(escala2));
        boton2.setBorderPainted(false);
        boton2.setContentAreaFilled(false);
        boton2.setFocusPainted(false);
        boton2.addActionListener(e -> personajeSeleccionado("NORMAL"));

        JButton btnCaract2 = new JButton("CARACTERISTICAS NORMAL");
        btnCaract2.setPreferredSize(new Dimension(150, 75));
        btnCaract2.setBackground(Color.YELLOW);
        btnCaract2.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventana, "NORMAL:\n \n- Vida: 100HP\n- Escudo: 100SP\n- Multiplicador daño: 1X\n- Multiplicador precisión: 0.9X\n \n - ARMAS: Arco, Rifle, Escopeta");
        });

        panelColumna2.add(boton2, BorderLayout.CENTER);
        panelColumna2.add(btnCaract2, BorderLayout.SOUTH);

        JPanel panelColumna3 = new JPanel(new BorderLayout(0, 10));
        panelColumna3.setOpaque(false);

        ImageIcon img3 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje3.jpg"));
        Image escala3 = img3.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
        JButton boton3 = new JButton(new ImageIcon(escala3));
        boton3.setBorderPainted(false);
        boton3.setContentAreaFilled(false);
        boton3.setFocusPainted(false);
        boton3.addActionListener(e -> personajeSeleccionado("GIGANTE"));

        JButton btnCaract3 = new JButton("CARACTERISTICAS GIGANTE");
        btnCaract3.setPreferredSize(new Dimension(150, 75));
        btnCaract3.setBackground(Color.YELLOW);
        btnCaract3.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventana, "GIGANTE:\n \n- Vida: 200HP\n- Escudo: 50SP\n- Multiplicador daño: 1.2X\n- Multiplicador precisión: 0.65X\n \n - ARMAS: Machete, Lanzacohetes, Fusil");
        });
        
        panelColumna3.add(boton3, BorderLayout.CENTER);
        panelColumna3.add(btnCaract3, BorderLayout.SOUTH);


        panelPrincipal.add(panelColumna1);
        panelPrincipal.add(panelColumna2);
        panelPrincipal.add(panelColumna3);

        panelFondo.add(panelPrincipal, BorderLayout.CENTER);
    }

    public void mostrar() {
        ventana.setVisible(true);
    }
}