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
        
        //Este panel contendrá a los demás y lo he añadido para que sea más sencillo centrar todo
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 3, 50, 0)); 
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); 

        //Este panel actua como columa de enano y contiene el botón (imagen) y las características
        JPanel panelColumna1 = new JPanel(new BorderLayout(0, 10));
        panelColumna1.setOpaque(false);
        
        ImageIcon img1 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje2.jpg"));
        Image escala1 = img1.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
        JButton boton1 = new JButton(new ImageIcon(escala1));
        boton1.setBorderPainted(false);
        boton1.setContentAreaFilled(false);
        boton1.setFocusPainted(false);
        boton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 JOptionPane.showMessageDialog(ventana, "¡Personaje seleccionado: ENANO!");
            }
        });

        JButton btnCaract1 = new JButton("CARACTERISTICAS ENANO");
        btnCaract1.setPreferredSize(new Dimension(150, 75));
        btnCaract1.setBackground(Color.YELLOW);
        btnCaract1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ventana, "ENANO:\n- Velocidad: Alta\n- Resistencia: Baja\n- Habilidad Especial: Esquiva");
            }
        });
        
        panelColumna1.add(boton1, BorderLayout.CENTER);
        panelColumna1.add(btnCaract1, BorderLayout.SOUTH);
        
        //Este panel actua como columa de normal y contiene el botón (imagen) y las características
        JPanel panelColumna2 = new JPanel(new BorderLayout(0, 10));
        panelColumna2.setOpaque(false);

        ImageIcon img2 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje1.jpg"));
        Image escala2 = img2.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
        JButton boton2 = new JButton(new ImageIcon(escala2));
        boton2.setBorderPainted(false);
        boton2.setContentAreaFilled(false);
        boton2.setFocusPainted(false);
        boton2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 JOptionPane.showMessageDialog(ventana, "¡Personaje seleccionado: NORMAL!");
            }
        });

        JButton btnCaract2 = new JButton("CARACTERISTICAS NORMAL");
        btnCaract2.setPreferredSize(new Dimension(150, 75));
        btnCaract2.setBackground(Color.YELLOW);
        btnCaract2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ventana, "NORMAL:\n- Velocidad: Media\n- Resistencia: Media\n- Habilidad Especial: Equilibrio");
            }
        });

        panelColumna2.add(boton2, BorderLayout.CENTER);
        panelColumna2.add(btnCaract2, BorderLayout.SOUTH);

        //Este panel actua como columa de gigante y contiene el botón (imagen) y las características
        JPanel panelColumna3 = new JPanel(new BorderLayout(0, 10));
        panelColumna3.setOpaque(false);

        ImageIcon img3 = new ImageIcon(SeleccionPersonaje.class.getResource("personaje3.jpg"));
        Image escala3 = img3.getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH);
        JButton boton3 = new JButton(new ImageIcon(escala3));
        boton3.setBorderPainted(false);
        boton3.setContentAreaFilled(false);
        boton3.setFocusPainted(false);
        boton3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 JOptionPane.showMessageDialog(ventana, "¡Personaje seleccionado: GIGANTE!");
            }
        });

        JButton btnCaract3 = new JButton("CARACTERISTICAS GIGANTE");
        btnCaract3.setPreferredSize(new Dimension(150, 75));
        btnCaract3.setBackground(Color.YELLOW);
        btnCaract3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ventana, "GIGANTE:\n- Velocidad: Baja\n- Resistencia: Alta\n- Habilidad Especial: Carga");
            }
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