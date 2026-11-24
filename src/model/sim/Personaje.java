package model.sim;

import model.tree.NodoDialogo;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class Personaje {
    private final String nombre;
    private int afinidad;
    private BufferedImage sprite;
    private final Queue<NodoDialogo> arbolesDialogo = new LinkedList<>();

    public Personaje(String nombre, int afinidadInicial) {
        this.nombre = nombre;
        this.afinidad = afinidadInicial;
    }

    public void agregarArbolDialogo(NodoDialogo raiz) {
        arbolesDialogo.add(raiz);
    }

    public boolean tieneDialogoPendiente() {
        return !arbolesDialogo.isEmpty();
    }

    public NodoDialogo obtenerSiguienteArbol() {
        return arbolesDialogo.poll();
    }

    public void modificarAfinidad(int delta) {
        this.afinidad += delta;
    }

    public String getNombre() { return nombre; }
    public int getAfinidad() { return afinidad; }
    public BufferedImage getSprite() { return sprite; }
    public void setSprite(BufferedImage sprite) { this.sprite = sprite; }
}