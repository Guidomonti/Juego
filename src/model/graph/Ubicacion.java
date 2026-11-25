package model.graph;

import model.sim.Personaje;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class Ubicacion {
    private final String nombre;
    private final Color colorPlaceholder;
    private BufferedImage fondoDia;
    private BufferedImage fondoAtardecer;
    private BufferedImage fondoNoche;
    private Personaje personajePresente;

    public Ubicacion(String nombre, Color colorPlaceholder) {
        this.nombre = nombre;
        this.colorPlaceholder = colorPlaceholder;
    }

    // Carga las 3 variantes de horario si existen en resources/fondos/
    public void cargarFondos(String nombreArchivo) {
        this.fondoDia = leerImagen("/resources/fondos/dia/" + nombreArchivo);
        this.fondoAtardecer = leerImagen("/resources/fondos/atardecer/" + nombreArchivo);
        this.fondoNoche = leerImagen("/resources/fondos/noche/" + nombreArchivo);
    }

    private BufferedImage leerImagen(String ruta) {
        try (InputStream is = getClass().getResourceAsStream(ruta)) {
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar imagen: " + ruta);
        }
        return null;
    }

    // Devuelve la imagen según los turnos restantes (6-5: día, 4-3: atardecer, 2-0: noche)
    public BufferedImage getFondoSegunTurnos(int turnosRestantes) {
        if (turnosRestantes >= 5 && fondoDia != null) return fondoDia;
        if (turnosRestantes >= 3 && fondoAtardecer != null) return fondoAtardecer;
        if (fondoNoche != null) return fondoNoche;
        return fondoDia; // Fallback si falta alguna variante
    }

    public String getNombre() { return nombre; }
    public Color getColorPlaceholder() { return colorPlaceholder; }
    public Personaje getPersonajePresente() { return personajePresente; }
    public void setPersonajePresente(Personaje personajePresente) { this.personajePresente = personajePresente; }

    @Override
    public String toString() {
        return nombre;
    }
}