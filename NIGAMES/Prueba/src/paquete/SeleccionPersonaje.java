package paquete;

import Personajes.*;
import Armas.Arma;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SeleccionPersonaje {

    private JFrame ventana;
    private int numJugadores = 0;
    private int numeroBots = 0;
    private List<Jugador> jugadores;
    private int jugadorActualIndex = 0;
    private String dificultadSeleccionada;

    public static class Jugador {
    	
        String nombre;
        ClasePersonaje clase;
        Arma arma;

        public Jugador(String nombre) {
            this.nombre = nombre;
        
        }

        @Override
        public String toString() {
        
        	String nombreArma = (arma != null) ? arma.getNombre() : "Sin arma";
            String nombreClase = (clase != null) ? clase.getPersonaje() : "Sin clase";
            return "Nombre: " + nombre + ", Personaje: " + nombreClase + ", Arma: " + nombreArma;
        
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
    
    }

    private void iniciarProcesoDeSeleccion() {
    
    	if (preguntarNumeroBots() && preguntarDificultad() && preguntarModoJuego()) {
            pedirNombresJugadores();
        
            if (!jugadores.isEmpty()) {
                mostrarInfoJugadorActual();
            }
        
    	} else {
        
    		System.exit(0);
        }
    
    }

    private boolean preguntarNumeroBots() {
    
    	String input = JOptionPane.showInputDialog(ventana, "Número de bots (1-100):", "Bots", JOptionPane.QUESTION_MESSAGE);
        
    	if (input == null) return false;
        
    		try {
    			numeroBots = Integer.parseInt(input.trim());
            
    			if (numeroBots >= 1 && numeroBots <= 100) {
    				JOptionPane.showMessageDialog(ventana, "Jugarás contra " + numeroBots + " bots.", "OK", JOptionPane.INFORMATION_MESSAGE);
    				return true;
    			
    			} else {
                
    				JOptionPane.showMessageDialog(ventana, "Entre 1 y 100.", "Error", JOptionPane.ERROR_MESSAGE);
    				return preguntarNumeroBots();
    			}
    			
    		} catch (NumberFormatException e) {
            
    			JOptionPane.showMessageDialog(ventana, "Número inválido.", "Error", JOptionPane.ERROR_MESSAGE);
    			return preguntarNumeroBots();
        
    		}
    
    }

    private boolean preguntarDificultad() {
    
    	String[] dif = {"facil", "normal", "dificil"};
        
    	JComboBox<String> cb = new JComboBox<>(dif);
        JPanel p = new JPanel();
        
        p.add(new JLabel("Dificultad:"));
        p.add(cb);
        
        int r = JOptionPane.showConfirmDialog(ventana, p, "Dificultad", JOptionPane.OK_CANCEL_OPTION);
        
        if (r == JOptionPane.OK_OPTION) {
        
        	dificultadSeleccionada = (String) cb.getSelectedItem();
            return true;
        }
        
        return false;
    }

    private boolean preguntarModoJuego() {
        
    	String[] modos = {"Solo (1)", "Duo (2)", "Squad (4)"};
        JComboBox<String> cb = new JComboBox<>(modos);
        JPanel p = new JPanel();
        
        p.add(new JLabel("Modo (humanos):"));
        p.add(cb);
        
        int r = JOptionPane.showConfirmDialog(ventana, p, "Modo", JOptionPane.OK_CANCEL_OPTION);
        
        if (r == JOptionPane.OK_OPTION) {
        	
            numJugadores = cb.getSelectedIndex() == 0 ? 1 : cb.getSelectedIndex() == 1 ? 2 : 4;
            return true;
        }
        
        return false;
    }

    private void pedirNombresJugadores() {
    	
        for (int i = 0; i < numJugadores; i++) {
        	
        String nombre = JOptionPane.showInputDialog(ventana, "Nombre Jugador " + (i + 1) + ":");
        
        	if (nombre == null || nombre.trim().isEmpty()) {
        		
        		nombre = "Jugador" + (i + 1);
        	}
            
            jugadores.add(new Jugador(nombre));
        }
    }

    private void mostrarInfoJugadorActual() {
        
    	ventana.setTitle("Selecciona personaje - " + jugadores.get(jugadorActualIndex).nombre);
    }

    private void personajeSeleccionado(ClasePersonaje clase) {
        
    	jugadores.get(jugadorActualIndex).clase = clase;
        JOptionPane.showMessageDialog(ventana, jugadores.get(jugadorActualIndex).nombre + " eligió: " + clase.getPersonaje());
        jugadorActualIndex++;
        
        if (jugadorActualIndex < numJugadores) {
            mostrarInfoJugadorActual();
        
        } else {
        
        	finalizarSeleccion();
        }
    }

    private void finalizarSeleccion() {
        
    	ventana.dispose();
        new SeleccionArmas(jugadores, dificultadSeleccionada, numeroBots);
    
    }

    private void configurarInterfaz() {
    
    	JPanel fondo = new JPanel() {
        
    		private final ImageIcon img = new ImageIcon(SeleccionPersonaje.class.getResource("fondo.jpg"));
            
    		@Override 
    		protected void paintComponent(Graphics g) {
    			super.paintComponent(g);
                
    			if (img.getImage() != null) {
                
                	g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
            
                }
            
    		}
        
    		};
    
    		fondo.setLayout(new BorderLayout());
    		ventana.setContentPane(fondo);

    		JPanel panel = new JPanel(new GridLayout(1, 3, 30, 0));
    		panel.setOpaque(false);
    		panel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));

    		panel.add(crearPanel("personaje2.jpg", new Enano(), "ENANO:\n75HP, 100SP\nDaño: 0.9X\nPrecisión: 1.3X"));
    		panel.add(crearPanel("personaje1.jpg", new Normal(), "NORMAL:\n100HP, 100SP\nDaño: 1X\nPrecisión: 1.0X"));
    		panel.add(crearPanel("personaje3.jpg", new Gigante(), "GIGANTE:\n200HP, 50SP\nDaño: 1.2X\nPrecisión: 0.80X"));

    		fondo.add(panel, BorderLayout.CENTER);
    }

    private JPanel crearPanel(String imgPath, ClasePersonaje clase, String info) {
    	
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setOpaque(false);

        ImageIcon icon = new ImageIcon(SeleccionPersonaje.class.getResource(imgPath));
        JButton btn;
        
        if (icon.getImage() != null) {
        
        	Image scaled = icon.getImage().getScaledInstance(220, 350, Image.SCALE_SMOOTH);
            btn = new JButton(new ImageIcon(scaled));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
        
        } else {
        
        	btn = new JButton("Seleccionar\n" + clase.getPersonaje());
            btn.setFont(new Font("Arial", Font.BOLD, 14));
        }
        
        btn.addActionListener(e -> personajeSeleccionado(clase));
        p.add(btn, BorderLayout.CENTER);

        JButton infoBtn = new JButton("Info");
        infoBtn.setBackground(Color.YELLOW);
        infoBtn.addActionListener(e -> JOptionPane.showMessageDialog(ventana, info));
        p.add(infoBtn, BorderLayout.SOUTH);

        return p;
        
    }

    public void mostrar() {
        ventana.setVisible(true);
    }

    
}