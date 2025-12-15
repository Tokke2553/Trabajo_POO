package paquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegoInterfaz {

    private JFrame ventana;

    public JuegoInterfaz() {
        ventana = new JFrame("Mi Juego");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(true);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panelFondo = new JPanel() {
            
            private final ImageIcon imagenIcon = new ImageIcon(JuegoInterfaz.class.getResource("fondo.jpg"));

            @Override
            protected void paintComponent(Graphics g) {//Aqui me he ayudado de la IA porque no sabia como poner una imagen de fondo
                super.paintComponent(g);
                
                Image imagen = imagenIcon.getImage();
                    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelFondo.setLayout(new GridBagLayout()); 
        ventana.setContentPane(panelFondo);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 1, 0, 30)); 
        panelBotones.setOpaque(false);

        JButton btnIniciar = new JButton("INICIAR PARTIDA");
        btnIniciar.setPreferredSize(new Dimension(300, 75)); 
        btnIniciar.setBackground(Color.YELLOW);
        btnIniciar.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e) {
                
        		ventana.setVisible(false);//He utilizado inteligencia artificial para saber que esta es la manera de que se cierre la interfaz al abrir la siguiente
        		
        		SeleccionPersonaje seleccion = new SeleccionPersonaje();
                seleccion.mostrar();
                
            }
        });
        
        JButton btnSalir = new JButton("SALIR AL ESCRITORIO");
        btnSalir.setPreferredSize(new Dimension(300, 75)); 
        btnSalir.setBackground(Color.YELLOW);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panelBotones.add(btnIniciar);
        panelBotones.add(btnSalir);

        panelFondo.add(panelBotones);
    }

    public void mostrar() {
        ventana.setVisible(true);
    }
}