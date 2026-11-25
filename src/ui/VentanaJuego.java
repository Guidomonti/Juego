package ui;

import model.graph.Ubicacion;
import model.sim.MotorSimulador;
import model.sim.MotorSimulador.Fase;
import model.tree.NodoDialogo;
import model.tree.NodoDialogo.OpcionDialogo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaJuego extends JFrame {
    private final MotorSimulador motor;
    private final JLabel lblInfoSuperior = new JLabel();
    private final JTextArea txtDialogo = new JTextArea(4, 40);
    private final JPanel panelBotones = new JPanel();
    private final PanelEscena panelEscena = new PanelEscena();

    public VentanaJuego(MotorSimulador motor) {
        this.motor = motor;
        setTitle("Simulador de Citas - Prototipo");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        lblInfoSuperior.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        lblInfoSuperior.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblInfoSuperior, BorderLayout.NORTH);

        add(panelEscena, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(6, 6));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));

        txtDialogo.setEditable(false);
        txtDialogo.setLineWrap(true);
        txtDialogo.setWrapStyleWord(true);
        txtDialogo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panelInferior.add(new JScrollPane(txtDialogo), BorderLayout.CENTER);

        panelBotones.setLayout(new GridLayout(0, 1, 4, 4));
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);
        refrescarVista();
    }

    public void refrescarVista() {
        Ubicacion actual = motor.getUbicacionActual();
        lblInfoSuperior.setText("Lugar: " + actual.getNombre() + " | Turnos restantes: " + motor.getTurnosRestantes());
        panelEscena.repaint();
        panelBotones.removeAll();

        if (motor.getFaseActual() == Fase.FIN) {
            txtDialogo.setText("Terminó el día. Evaluando finales...");
        } else if (motor.getFaseActual() == Fase.EN_DIALOGO) {
            NodoDialogo nodo = motor.getNodoActual();
            txtDialogo.setText(nodo.getHablante() + ": " + nodo.getTexto());

            if (nodo.esHoja()) {
                JButton btnContinuar = new JButton("Continuar...");
                btnContinuar.addActionListener(e -> {
                    motor.terminarDialogo();
                    refrescarVista();
                });
                panelBotones.add(btnContinuar);
            } else {
                List<OpcionDialogo> opciones = nodo.getOpciones();
                for (int i = 0; i < opciones.size(); i++) {
                    int idx = i;
                    JButton btn = new JButton(opciones.get(i).texto());
                    btn.addActionListener(e -> {
                        motor.elegirOpcion(idx);
                        refrescarVista();
                    });
                    panelBotones.add(btn);
                }
            }
        } else {
            txtDialogo.setText("Estás en " + actual.getNombre() + ". ¿A dónde querés ir?");
            for (Ubicacion destino : motor.getDestinosDisponibles()) {
                JButton btn = new JButton("Ir a: " + destino.getNombre());
                btn.addActionListener(e -> {
                    motor.moverA(destino);
                    refrescarVista();
                });
                panelBotones.add(btn);
            }
        }

        panelBotones.revalidate();
        panelBotones.repaint();
    }

    // Panel donde colocar imagenes
    private class PanelEscena extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Ubicacion actual = motor.getUbicacionActual();
            java.awt.image.BufferedImage fondo = actual.getFondoSegunTurnos(motor.getTurnosRestantes());

            if (fondo != null) {
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Si todavía no tiene archivo asignado, dibuja el color de prueba
                g.setColor(actual.getColorPlaceholder());
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.drawString("[FONDO: " + actual.getNombre() + "]", 20, 30);
            }

            // Sprite personaje (placeholder hasta que agreguemos sus PNG)
            if (motor.getPersonajeActivo() != null) {
                g.setColor(new Color(40, 40, 60, 220));
                g.fillRoundRect(getWidth() / 2 - 75, 60, 150, 240, 20, 20);
                g.setColor(Color.WHITE);
                g.drawString(motor.getPersonajeActivo().getNombre(), getWidth() / 2 - 40, 180);
            }
        }
    }
}