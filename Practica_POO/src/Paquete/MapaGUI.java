package Paquete;

import javax.swing.*;
import Tormenta.*;

import java.awt.*;

// Importación adicional necesaria para usar rutas de archivo más robustas si fuera necesario
import java.io.File; 

public class MapaGUI extends JPanel {

    private array mapaObjeto; 
    private final int TAMAÑO_CELDA = 200;
    private final int TAMAÑO_TOTAL = 3 * TAMAÑO_CELDA;
    
    
    private Image imagenFondo; 

    //
    public MapaGUI(array mapa) {
        this.mapaObjeto = mapa;
        this.setPreferredSize(new Dimension(TAMAÑO_TOTAL, TAMAÑO_TOTAL));

        // --- CARGA DE LA IMAGEN CON LA RUTA ABSOLUTA ---
        try {
            // Usamos la ruta absoluta proporcionada por el usuario.
            // Creamos un objeto File para asegurarnos de que el path sea válido para el sistema operativo.
            File imageFile = new File("C:\\Users\\kajem\\Downloads\\mapaF.jpg");
            
            // Verificación: Asegúrate de que el archivo exista antes de intentar cargarlo
            if (imageFile.exists()) {
                 // Creamos un ImageIcon a partir de la ruta del archivo y obtenemos el objeto Image
                 imagenFondo = new ImageIcon(imageFile.getAbsolutePath()).getImage();
            } else {
                 System.err.println("Error: Archivo de imagen no encontrado en la ruta especificada: " + imageFile.getAbsolutePath());
                 imagenFondo = null;
            }

        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            imagenFondo = null; 
        }
    }

    // Método principal de dibujo (se llama automáticamente)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int[][] mapa = mapaObjeto.array;
        
        // 1. DIBUJAR LA IMAGEN DE FONDO
        if (imagenFondo != null) {
            // Dibuja la imagen cubriendo todo el panel (300x300)
            g.drawImage(imagenFondo, 0, 0, TAMAÑO_TOTAL, TAMAÑO_TOTAL, this);
        } else {
            // Dibujar un color de fondo si la imagen falla
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, TAMAÑO_TOTAL, TAMAÑO_TOTAL);
        }

        // 2. DIBUJAR LA DESTRUCCIÓN Y LA REJILLA ENCIMA DE LA IMAGEN
        for (int i = 0; i < 3; i++) { // Filas
            for (int j = 0; j < 3; j++) { // Columnas
                
                int x = j * TAMAÑO_CELDA;
                int y = i * TAMAÑO_CELDA;
                
                if (mapa[i][j] == 10) {
                    // Si está destruida, superponer un color semi-transparente
                    // para mostrar la destrucción pero aún ver la imagen de fondo.
                    
                    // Color Rojo semi-transparente (150 de 255 es la opacidad)
                    Color colorDestruccion = new Color(150, 0, 0, 150); 
                    g.setColor(colorDestruccion);
                    g.fillRect(x, y, TAMAÑO_CELDA, TAMAÑO_CELDA);
                    
                    // Dibujar texto de destrucción
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 16));
                    g.drawString("DESTRUIDA", x + 15, y + 55);
                    
                } else {
                    // Opcional: Dibujar el valor de la ciudad (para debug)
                    g.setColor(Color.YELLOW);
                    g.setFont(new Font("Arial", Font.BOLD, 18));
                    String valor = String.valueOf(mapa[i][j]);
                    int valor2 =mapa[i][j];
                    if(valor2 == 1) {
                    	g.drawString("VIKING FIDRF", x + 20, y + 20);
                    }
                    if(valor2 == 2) {
                    	g.drawString("QUARRY PEAK ", x + 20, y + 20);
                    }
                    if(valor2 == 3) {
                    	g.drawString("TEMPEL FALSS ", x + 20, y + 20);
                    }
                    if(valor2 == 4) {
                    	g.drawString("BUBBLE BOG ", x + 20, y + 20);
                    }
                    if(valor2 == 5) {
                    	g.drawString("METRO MELLDOWN ", x + 20, y + 20);
                    }
                    if(valor2 == 6) {
                    	g.drawString("SHROOM SHORES ", x + 20, y + 20);
                    }
                    if(valor2 == 7) {
                    	g.drawString("DUSTY DERELIC ", x + 20, y + 20);
                    }
                    if(valor2 == 8) {
                    	g.drawString("GATOR GLADE ", x + 20, y + 20);
                    }
                    if(valor2 == 9) {
                    	g.drawString("LAVA LOCKDOWN ", x + 20, y + 20);
                    }
                }

                // Dibujar la REJILLA (Líneas divisorias)
                g.setColor(Color.BLACK);
                g.drawRect(x, y, TAMAÑO_CELDA, TAMAÑO_CELDA);
            }
        }
    }
    
    /**
     * Llama a paintComponent para redibujar la matriz.
     */
    public void actualizarMapa() {
        this.repaint();
    }
}