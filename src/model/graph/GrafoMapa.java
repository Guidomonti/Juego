package model.graph;

import java.util.*;

public class GrafoMapa {
    private final Map<Ubicacion, List<Ubicacion>> adyacencia = new LinkedHashMap<>();

    public void agregarUbicacion(Ubicacion ubicacion) {
        adyacencia.putIfAbsent(ubicacion, new ArrayList<>());
    }

    public void conectar(Ubicacion origen, Ubicacion destino) {
        agregarUbicacion(origen);
        agregarUbicacion(destino);
        if (!adyacencia.get(origen).contains(destino)) {
            adyacencia.get(origen).add(destino);
        }
        if (!adyacencia.get(destino).contains(origen)) {
            adyacencia.get(destino).add(origen);
        }
    }

    public List<Ubicacion> vecinosDe(Ubicacion ubicacion) {
        return adyacencia.getOrDefault(ubicacion, List.of());
    }

    public Set<Ubicacion> getUbicaciones() {
        return adyacencia.keySet();
    }
}