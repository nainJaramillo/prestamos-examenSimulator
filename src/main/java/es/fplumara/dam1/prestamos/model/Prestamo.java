package es.fplumara.dam1.prestamos.model;

import java.time.LocalDate;

public class Prestamo {
    Long id;
    Long idMaterial;
    String profesor;
    LocalDate fecha;

    public Prestamo(Long id, Long idMaterial, String profesor, LocalDate fecha) {
        this.id = id;
        this.idMaterial = idMaterial;
        this.profesor = profesor;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
