package app;

import model.graph.GrafoMapa;
import model.graph.Ubicacion;
import model.sim.MotorSimulador;
import model.sim.Personaje;
import model.tree.NodoDialogo;
import ui.VentanaJuego;

import javax.swing.SwingUtilities;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        // 1. Crear ubicaciones (nodos del grafo)
        Ubicacion cafeteria = new Ubicacion("Cafetería", new Color(92, 58, 33));
        Ubicacion patio = new Ubicacion("Patio", new Color(46, 107, 79));
        Ubicacion biblioteca = new Ubicacion("Biblioteca", new Color(45, 68, 102));
        Ubicacion aula = new Ubicacion("Aula", new Color(110, 82, 42));

        // 2. Conectar ubicaciones (aristas no dirigidas)
        GrafoMapa mapa = new GrafoMapa();
        mapa.conectar(cafeteria, patio);
        mapa.conectar(cafeteria, biblioteca);
        mapa.conectar(patio, aula);
        mapa.conectar(biblioteca, aula);

        // 3. Crear personaje y árbol de diálogo (nodos y ramas)
        Personaje valen = new Personaje("Valen", 0);

        NodoDialogo hojaBuena = new NodoDialogo("h1", "Valen", "Me copa ese plan. Nos vemos ahí después de cursar.");
        NodoDialogo hojaNeutral = new NodoDialogo("h2", "Valen", "Dale, vemos cómo venimos de tiempo.");
        NodoDialogo hojaFria = new NodoDialogo("h3", "Valen", "Eh... hoy ando medio corrido, mejor otro día.");

        NodoDialogo ramaCafe = new NodoDialogo("r1", "Valen", "¿Tomás algo caliente o preferís dar una vuelta por el patio?");
        ramaCafe.agregarOpcion("Invitar un café y charlar tranquilos", 2, hojaBuena);
        ramaCafe.agregarOpcion("Ir al patio a tomar aire", 1, hojaNeutral);

        NodoDialogo raizSaludo = new NodoDialogo("root", "Valen", "¡Ey! No te había visto entrar. ¿Qué hacés por acá?");
        raizSaludo.agregarOpcion("Sonreír y acercarte a saludar", 1, ramaCafe);
        raizSaludo.agregarOpcion("Tirar un chiste seco sobre el parcial", -1, hojaFria);

        valen.agregarArbolDialogo(raizSaludo);
        cafeteria.setPersonajePresente(valen);

        // 4. Iniciar motor y ventana Swing
        MotorSimulador motor = new MotorSimulador(mapa, cafeteria);
        SwingUtilities.invokeLater(() -> {
            VentanaJuego ventana = new VentanaJuego(motor);
            ventana.setVisible(true);
        });
    }
}