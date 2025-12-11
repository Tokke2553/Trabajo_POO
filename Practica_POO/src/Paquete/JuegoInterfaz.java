package Paquete;

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
        ventana.setResizable(false);

        JPanel panelFondo = new JPanel() {

            ImageIcon imagenIcon = new ImageIcon(JuegoInterfaz.class.getResource("fondo.jpg"));
            Image fondo;{
            	fondo = imagenIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            }
            
            @Override
            protected void paintComponent(Graphics g) { //Aqui me he ayudado de la IA porque no sabia como poner una imagen de fondo             
                    g.drawImage(fondo, 0, 0, this); 
            }
        };

        panelFondo.setLayout(null);
        ventana.setContentPane(panelFondo);

        JButton btnIniciar = new JButton("INICIAR PARTIDA");
        btnIniciar.setBounds(300, 200, 200, 50);
        btnIniciar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ventana, "Elige personaje");
            }
        });
        panelFondo.add(btnIniciar);

        JButton btnSalir = new JButton("SALIR AL ESCRITORIO");
        btnSalir.setBounds(300, 300, 200, 50);
        
        //Aqui he usado inteligencia artificial porque no sabia como hacer para que clicando un boton se saliera del programa.
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelFondo.add(btnSalir);
    }

    public void mostrar() {
        ventana.setVisible(true);
    }
}