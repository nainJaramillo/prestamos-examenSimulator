package es.fplumara.dam1.prestamos.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Material implements Identificable {

    protected String id;
    protected EstadoMaterial estado = EstadoMaterial.DISPONIBLE;
    protected Set<String> etiquetas = new HashSet<>();

    public Material(String id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public EstadoMaterial getEstado() {
        return estado;
    }

    public void setEstado(EstadoMaterial estado) {
        this.estado = estado;
    }

    public Set<String> getEtiquetas() {
        return etiquetas;
    }

    public void addEtiqueta(String etiqueta) {
        this.etiquetas.add(etiqueta);
    }

    public abstract String getTipo();
}