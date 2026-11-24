package model.sim;

import model.graph.GrafoMapa;
import model.graph.Ubicacion;
import model.tree.NodoDialogo;
import model.tree.NodoDialogo.OpcionDialogo;

import java.util.List;

public class MotorSimulador {
    public enum Fase { EXPLORANDO, EN_DIALOGO, FIN }

    private final GrafoMapa mapa;
    private Ubicacion ubicacionActual;
    private NodoDialogo nodoActual;
    private Personaje personajeActivo;
    private Fase faseActual = Fase.EXPLORANDO;
    private int turnosRestantes = 6;

    public MotorSimulador(GrafoMapa mapa, Ubicacion inicio) {
        this.mapa = mapa;
        this.ubicacionActual = inicio;
        evaluarEscenaEnUbicacion();
    }

    public void moverA(Ubicacion destino) {
        if (faseActual != Fase.EXPLORANDO) return;
        this.ubicacionActual = destino;
        this.turnosRestantes--;
        if (turnosRestantes <= 0) {
            faseActual = Fase.FIN;
            return;
        }
        evaluarEscenaEnUbicacion();
    }

    private void evaluarEscenaEnUbicacion() {
        Personaje p = ubicacionActual.getPersonajePresente();
        if (p != null && p.tieneDialogoPendiente()) {
            this.personajeActivo = p;
            this.nodoActual = p.obtenerSiguienteArbol();
            this.faseActual = Fase.EN_DIALOGO;
        } else {
            this.personajeActivo = null;
            this.nodoActual = null;
            this.faseActual = Fase.EXPLORANDO;
        }
    }

    public void elegirOpcion(int indice) {
        if (faseActual != Fase.EN_DIALOGO || nodoActual == null) return;
        OpcionDialogo elegida = nodoActual.getOpciones().get(indice);
        personajeActivo.modificarAfinidad(elegida.deltaAfinidad());

        if (elegida.siguiente() == null || elegida.siguiente().esHoja()) {
            // Si el siguiente nodo es hoja, mostramos su texto final y cerramos el diálogo
            this.nodoActual = elegida.siguiente();
            if (this.nodoActual == null) {
                terminarDialogo();
            }
        } else {
            this.nodoActual = elegida.siguiente();
        }
    }

    public void terminarDialogo() {
        this.nodoActual = null;
        this.faseActual = Fase.EXPLORANDO;
    }

    public List<Ubicacion> getDestinosDisponibles() {
        return mapa.vecinosDe(ubicacionActual);
    }

    public Ubicacion getUbicacionActual() { return ubicacionActual; }
    public NodoDialogo getNodoActual() { return nodoActual; }
    public Personaje getPersonajeActivo() { return personajeActivo; }
    public Fase getFaseActual() { return faseActual; }
    public int getTurnosRestantes() { return turnosRestantes; }
}