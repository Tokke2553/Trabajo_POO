package Paquete;

import javax.swing.*;
import Tormenta.*;
import java.awt.*;
import java.io.File;

public class MapaGUI {

    private JFrame ventana;
    private ArrayMapa mapaData;
    private Image imagenFondo;
    private LienzoMapa lienzo;
    private JButton btnRonda;

    // Nombres de las ciudades para el dibujo
    private static final String[] CIUDADES = {
        "", "VIKING FIORD", "QUARRY PEAK", "TEMPLE FALLS", 
        "BUBBLE BOG", "METRO MELTDOWN", "SHROOM SHORES", 
        "DUSTY DERELICT", "GATOR GLADE", "LAVA LOCKDOWN"
    };

    public MapaGUI(ArrayMapa mapa) {
        this.mapaData = mapa;
        cargarImagen();
        configurarVentana();
    }

    private void cargarImagen() {
        try {
            File file = new File("mapaF.jpg");
            if (file.exists()) {
                imagenFondo = new ImageIcon(file.getAbsolutePath()).getImage();
            } else {
                System.err.println("No se encontró mapaF.jpg en la raíz del proyecto.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarVentana() {
        ventana = new JFrame("Battle Royale Island - Mapa");
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        ventana.setUndecorated(false); // Cambiar a true si quieres sin bordes
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        // El lienzo es donde se dibuja el mapa y la tormenta
        lienzo = new LienzoMapa();
        ventana.add(lienzo, BorderLayout.CENTER);

        // Botón de Rondas
        btnRonda = new JButton("SIGUIENTE RONDA");
        btnRonda.setFont(new Font("Arial", Font.BOLD, 20));
        btnRonda.setBackground(Color.YELLOW);
        btnRonda.setPreferredSize(new Dimension(0, 60));
        
        // Aquí conectamos con tu lógica de ControladorJuego o Tormenta
        btnRonda.addActionListener(e -> {
            ejecutarLogicaRonda();
        });

        ventana.add(btnRonda, BorderLayout.SOUTH);
    }

    private void ejecutarLogicaRonda() {
        // Lógica simplificada de ronda (puedes instanciar tu ControladorJuego aquí)
        Tormenta tormenta = new Tormenta(mapaData);
        tormenta.ejecutarRondaDeTormenta();
        actualizar();
    }

    public void mostrar() {
        ventana.setVisible(true);
    }

    public void actualizar() {
        lienzo.repaint();
    }

    // Clase interna para el dibujo (necesario para manejar paintComponent sin que MapaGUI sea un JPanel)
    private class LienzoMapa extends JPanel {
        
        // Calibración de rejilla (proporciones)
        private final double PROP_EXTERNA = 0.358; // ~215/600
        private final double PROP_CENTRAL = 0.284; // ~170/600

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // 1. Dibujar Imagen de Fondo
            if (imagenFondo != null) {
                g2.drawImage(imagenFondo, 0, 0, w, h, this);
            }

            // 2. Calcular dimensiones dinámicas de celdas
            int[] xCoords = {0, (int)(w * PROP_EXTERNA), (int)(w * (PROP_EXTERNA + PROP_CENTRAL)), w};
            int[] yCoords = {0, (int)(h * PROP_EXTERNA), (int)(h * (PROP_EXTERNA + PROP_CENTRAL)), h};

            int[][] matriz = mapaData.array;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int x = xCoords[j];
                    int y = yCoords[i];
                    int cellW = xCoords[j+1] - x;
                    int cellH = yCoords[i+1] - y;

                    // Dibujar Rejilla
                    g2.setColor(new Color(0, 0, 0, 80));
                    g2.drawRect(x, y, cellW, cellH);

                    if (matriz[i][j] == 10) {
                        // Superponer Tormenta (Rojo semitransparente)
                        g2.setColor(new Color(255, 0, 0, 120));
                        g2.fillRect(x, y, cellW, cellH);
                        
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Arial", Font.BOLD, (int)(cellH * 0.1)));
                        g2.drawString("DESTRUIDA", x + (cellW/4), y + (cellH/2));
                    } else {
                        // Dibujar Nombres de Ciudades
                        int id = matriz[i][j];
                        if (id >= 1 && id <= 9) {
                            g2.setColor(Color.YELLOW);
                            g2.setFont(new Font("Arial", Font.BOLD, (int)(cellH * 0.08)));
                            g2.drawString(CIUDADES[id], x + 15, y + (int)(cellH * 0.15));
                        }
                    }
                }
            }
        }
    }
}