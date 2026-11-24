package model.graph;

import model.sim.Personaje;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Ubicacion {
    private final String nombre;
    private final Color colorPlaceholder;
    private BufferedImage imagenFondo;
    private Personaje personajePresente;

    public Ubicacion(String nombre, Color colorPlaceholder) {
        this.nombre = nombre;
        this.colorPlaceholder = colorPlaceholder;
    }

    public String getNombre() { return nombre; }
    public Color getColorPlaceholder() { return colorPlaceholder; }
    public BufferedImage getImagenFondo() { return imagenFondo; }
    public void setImagenFondo(BufferedImage imagenFondo) { this.imagenFondo = imagenFondo; }

    public Personaje getPersonajePresente() { return personajePresente; }
    public void setPersonajePresente(Personaje personajePresente) { this.personajePresente = personajePresente; }

    @Override
    public String toString() {
        return nombre;
    }
}