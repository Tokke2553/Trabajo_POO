package paquete;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VentanaMapa extends JFrame {

    private final int TAMANO_CELDA = 15;
    private final int TAMANO_MAPA = 50;

    private List<JugadorCombate> jugadores = new CopyOnWriteArrayList<>();
    private int centroX = 25;
    private int centroY = 25;
    private int radio = 20;
    private boolean batallaTerminada = false;
    private String mensajeGanador = "";

    private MapaPanel mapaPanel;

    public VentanaMapa() {
        setTitle("Battle Royale - Mapa en vivo");
        setSize(TAMANO_MAPA * TAMANO_CELDA + 50, TAMANO_MAPA * TAMANO_CELDA + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mapaPanel = new MapaPanel();
        add(mapaPanel, BorderLayout.CENTER);

        // Repintar cada segundo para ver los cambios
        new Timer(1000, e -> mapaPanel.repaint()).start();

        setVisible(true);
    }

    public void actualizarEstado(List<JugadorCombate> jugadores, int centroX, int centroY, int radio) {
        this.jugadores.clear();
        this.jugadores.addAll(jugadores);
        this.centroX = centroX;
        this.centroY = centroY;
        this.radio = radio;
    }

    public void finalizarBatalla(String mensaje) {
        this.batallaTerminada = true;
        this.mensajeGanador = mensaje;
        mapaPanel.repaint();
    }

    private class MapaPanel extends JPanel {

        private ImageIcon fondo;

        public MapaPanel() {
            // Intentamos cargar la imagen de forma segura
            java.net.URL imgURL = getClass().getResource("mapaF.jpg");
            if (imgURL != null) {
                fondo = new ImageIcon(imgURL);
            } else {
                fondo = null; // No existe la imagen → usaremos color de fondo
                System.err.println("Advertencia: No se encontró mapa.jpg en el paquete paquete");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // === FONDO ===
            if (fondo != null) {
                g2d.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            } else {
                // Fondo verde oscuro si no hay imagen
                g2d.setColor(new Color(20, 50, 20));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            int offsetX = (getWidth() - TAMANO_MAPA * TAMANO_CELDA) / 2;
            int offsetY = (getHeight() - TAMANO_MAPA * TAMANO_CELDA) / 2;

            // === TORMENTA (zona peligrosa) ===
            g2d.setColor(new Color(100, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // === ZONA SEGURA (círculo) ===
            int diametro = radio * 2 * TAMANO_CELDA;
            int zonaX = offsetX + (centroX - radio) * TAMANO_CELDA;
            int zonaY = offsetY + (centroY - radio) * TAMANO_CELDA;

            g2d.setColor(new Color(0, 100, 0, 100));
            g2d.fillOval(zonaX, zonaY, diametro, diametro);

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawOval(zonaX, zonaY, diametro, diametro);

            // === JUGADORES ===
            for (JugadorCombate j : jugadores) {
                if (!j.vivo) continue;

                int px = offsetX + j.x * TAMANO_CELDA;
                int py = offsetY + j.y * TAMANO_CELDA;

                g2d.setColor(j.esBot ? Color.RED : Color.CYAN);
                g2d.fillOval(px - 8, py - 8, 16, 16);

                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(px - 8, py - 8, 16, 16);

                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString(j.nombre, px - 20, py - 15);
            }

            // === MENSAJE FINAL ===
            if (batallaTerminada) {
                g2d.setColor(new Color(0, 0, 0, 180));
                g2d.fillRect(0, getHeight() / 2 - 50, getWidth(), 100);

                g2d.setColor(Color.YELLOW);
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                FontMetrics fm = g2d.getFontMetrics();
                int textoX = (getWidth() - fm.stringWidth(mensajeGanador)) / 2;
                g2d.drawString(mensajeGanador, textoX, getHeight() / 2 + 15);
            }
        }
    }
}