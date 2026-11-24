package model.tree;

import java.util.ArrayList;
import java.util.List;

public class NodoDialogo {
    private final String id;
    private final String hablante;
    private final String texto;
    private final List<OpcionDialogo> opciones = new ArrayList<>();

    public NodoDialogo(String id, String hablante, String texto) {
        this.id = id;
        this.hablante = hablante;
        this.texto = texto;
    }

    public void agregarOpcion(String textoOpcion, int deltaAfinidad, NodoDialogo siguiente) {
        opciones.add(new OpcionDialogo(textoOpcion, deltaAfinidad, siguiente));
    }

    public boolean esHoja() {
        return opciones.isEmpty();
    }

    public String getId() { return id; }
    public String getHablante() { return hablante; }
    public String getTexto() { return texto; }
    public List<OpcionDialogo> getOpciones() { return opciones; }

    public record OpcionDialogo(String texto, int deltaAfinidad, NodoDialogo siguiente) {}
}