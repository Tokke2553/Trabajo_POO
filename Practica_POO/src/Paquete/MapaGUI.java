package Paquete;

import javax.swing.*;
import Tormenta.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

public class MapaGUI {

    private JFrame ventana;
    private ArrayMapa mapaData;
    private Image imagenFondo;
    private LienzoMapa lienzo;
    private JButton btnRonda;
    
    //Variables Lugares para jugadores
    private int zonaJugadorHumano = -1; 
    private boolean esperandoViaje = false; // Bloquear botón 
    private Random random = new Random();
    
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
        try {//como no recogia la imagen de fondo a pesar de tener bien la ruta
            // La IA recomendo usar la funcion URL , para intentar generalo en el Paquete
            java.net.URL url = getClass().getResource("mapaF.jpg"); 

            if (url != null) {
                imagenFondo = new ImageIcon(url).getImage();
                System.out.println("Imagen cargada" + url);
            } else {
                // Metodo con file por si el primero diese error
                File file = new File("mapaF.jpg"); 
                if (file.exists()) {
                    imagenFondo = new ImageIcon(file.getAbsolutePath()).getImage();
                    System.err.println("Imagen cargada");
                } else {
                    System.err.println("No se ha cargado la imagen");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarVentana() {
        ventana = new JFrame("Battle Royale Island - Mapa");
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        
        lienzo = new LienzoMapa();
        
        // --- DETECCIÓN DE CLIC PARA VIAJAR ---
        lienzo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (esperandoViaje) {
                    procesarClickViaje(e.getX(), e.getY());
                }
            }
        });

        ventana.add(lienzo, BorderLayout.CENTER);

        btnRonda = new JButton("SIGUIENTE RONDA (TORMENTA)");
        btnRonda.setFont(new Font("Arial", Font.BOLD, 20));
        btnRonda.setBackground(Color.YELLOW);
        btnRonda.setPreferredSize(new Dimension(0, 60));
        
        btnRonda.addActionListener(e -> ejecutarLogicaRonda());

        ventana.add(btnRonda, BorderLayout.SOUTH);
    }

    private void ejecutarLogicaRonda() {
        // 1. Ejecutar Tormenta
        Tormenta tormenta = new Tormenta(mapaData);
        tormenta.ejecutarRondaDeTormenta();
        
        // 2. Cambiar fase: Ahora el humano DEBE elegir zona
        esperandoViaje = true;
        btnRonda.setEnabled(false);
        btnRonda.setText("HAZ CLICK EN UNA ZONA PARA VIAJAR");
        actualizar();
    }

    private void procesarClickViaje(int mouseX, int mouseY) {
        int w = lienzo.getWidth();
        int h = lienzo.getHeight();

        // Proporciones dinámicas
        double propExt = 0.358;
        double propCent = 0.284;

        int[] xLimits = {0, (int)(w * propExt), (int)(w * (propExt + propCent)), w};
        int[] yLimits = {0, (int)(h * propExt), (int)(h * (propExt + propCent)), h};

        // Identificar Fila (i) y Columna (j)
        int col = -1, fila = -1;
        for (int i = 0; i < 3; i++) {
            if (mouseX >= xLimits[i] && mouseX < xLimits[i+1]) col = i;
            if (mouseY >= yLimits[i] && mouseY < yLimits[i+1]) fila = i;
        }

        if (fila != -1 && col != -1) {
            // Verificar si la zona está destruida
            if (mapaData.array[fila][col] == 10) {
                JOptionPane.showMessageDialog(ventana, "¡Esa zona está destruida! Elige otra.");
                return;
            }

            // --- VIAJE DEL HUMANO ---
            this.zonaJugadorHumano = (fila * 3) + col + 1;
            System.out.println("Jugador Humano viaja a Zona: " + zonaJugadorHumano + " (" + CIUDADES[zonaJugadorHumano] + ")");

            // --- VIAJE DE LOS BOTS (Automático) ---
            // Aquí llamarías a la lógica de tu clase Partida para mover a todos los bot = true
            viajarBotsAleatoriamente();

            // Finalizar fase de viaje
            esperandoViaje = false;
            btnRonda.setEnabled(true);
            btnRonda.setText("SIGUIENTE RONDA (TORMENTA)");
            actualizar();
        }
    }

    private void viajarBotsAleatoriamente() {
        // Lógica para elegir zonas no destruidas para los bots
        System.out.println("Bots moviéndose a zonas aleatorias seguras...");
        // En tu clase Partida, iterarías la lista de jugadores:
        // si bot == true -> nuevo_destino = random entre zonas != 10
    }

    public void mostrar() {
        ventana.setVisible(true);
    }

    public void actualizar() {
        lienzo.repaint();
    }

    private class LienzoMapa extends JPanel {
        private final double PROP_EXTERNA = 0.358;
        private final double PROP_CENTRAL = 0.284;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            if (imagenFondo != null) g2.drawImage(imagenFondo, 0, 0, w, h, this);

            int[] xCoords = {0, (int)(w * PROP_EXTERNA), (int)(w * (PROP_EXTERNA + PROP_CENTRAL)), w};
            int[] yCoords = {0, (int)(h * PROP_EXTERNA), (int)(h * (PROP_EXTERNA + PROP_CENTRAL)), h};

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int x = xCoords[j];
                    int y = yCoords[i];
                    int cellW = xCoords[j+1] - x;
                    int cellH = yCoords[i+1] - y;
                    int idZona = (i * 3) + j + 1;

                    // Dibujar Rejilla
                    g2.setColor(new Color(0, 0, 0, 80));
                    g2.drawRect(x, y, cellW, cellH);

                    if (mapaData.array[i][j] == 10) {
                        g2.setColor(new Color(255, 0, 0, 120));
                        g2.fillRect(x, y, cellW, cellH);
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Arial", Font.BOLD, (int)(cellH * 0.1)));
                        g2.drawString("DESTRUIDA", x + (cellW/4), y + (cellH/2));
                    } else {
                        // Dibujar Nombres
                        g2.setColor(Color.YELLOW);
                        g2.setFont(new Font("Arial", Font.BOLD, (int)(cellH * 0.08)));
                        g2.drawString(CIUDADES[idZona], x + 15, y + (int)(cellH * 0.15));
                        
                        // --- MARCADOR DEL JUGADOR ---
                        if (idZona == zonaJugadorHumano) {
                            g2.setColor(Color.CYAN);
                            g2.setStroke(new BasicStroke(5));
                            g2.drawOval(x + cellW/2 - 20, y + cellH/2 - 20, 40, 40);
                            g2.drawString("TU", x + cellW/2 - 10, y + cellH/2 + 40);
                        }
                    }
                }
            }
        }
    }
}