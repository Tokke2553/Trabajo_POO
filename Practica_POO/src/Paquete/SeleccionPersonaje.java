package Paquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeleccionPersonaje {

    private JFrame ventana;

    public SeleccionPersonaje() {
        ventana = new JFrame("Selecciona tu personaje");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // PANEL DE FONDO CON IMAGEN
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

        // PANEL CREADO PARA QUE SEA MAS SENCILLO CENTRAR LOS BOTONES
        JPanel panel = new JPanel(new GridLayout(1, 3, 50, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

        ImageIcon img1 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje2.jpg"));
        Image escala1 = img1.getImage().getScaledInstance(350, 550, Image.SCALE_SMOOTH);
        JButton boton1 = new JButton(new ImageIcon(escala1));
        boton1.setPreferredSize(new Dimension(150, 250));
        boton1.setBorderPainted(false);
        boton1.setContentAreaFilled(false);
        boton1.setFocusPainted(false);
        boton1.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e) {
        		 JOptionPane.showMessageDialog(ventana, "Elegiste ENANO");
                
            }
        });
        ImageIcon img2 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje1.jpg"));
        Image escala2 = img2.getImage().getScaledInstance(350, 550, Image.SCALE_SMOOTH);
        JButton boton2 = new JButton(new ImageIcon(escala2));
        boton2.setPreferredSize(new Dimension(150, 250));
        boton2.setBorderPainted(false);
        boton2.setContentAreaFilled(false);
        boton2.setFocusPainted(false);
        boton2.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e) {
        		 JOptionPane.showMessageDialog(ventana, "Elegiste NORMAL");
                
            }
        });
        ImageIcon img3 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje3.jpg"));
        Image escala3 = img3.getImage().getScaledInstance(350, 550, Image.SCALE_SMOOTH);
        JButton boton3 = new JButton(new ImageIcon(escala3));
        boton3.setPreferredSize(new Dimension(150, 250));
        boton3.setBorderPainted(false);
        boton3.setContentAreaFilled(false);
        boton3.setFocusPainted(false);
        boton3.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent e) {
        		 JOptionPane.showMessageDialog(ventana, "Elegiste GIGANTE");
                
            }
        });
        panel.add(boton1);
        panel.add(boton2);
        panel.add(boton3);

        panelFondo.add(panel, BorderLayout.CENTER);
    }

    public void mostrar() {
        ventana.setVisible(true);
    }
}
