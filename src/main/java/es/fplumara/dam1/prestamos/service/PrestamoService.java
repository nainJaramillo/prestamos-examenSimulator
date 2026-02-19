package es.fplumara.dam1.prestamos.service;


import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.model.Prestamo;
import es.fplumara.dam1.prestamos.repository.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.PrestamoRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class PrestamoService {

    public PrestamoService(MaterialRepositoryImpl materialRepository) {
    }

    public PrestamoService(MaterialRepositoryImpl materialRepository, PrestamoRepositoryImpl prestamoRepository) {
    }
     Repository<Material> materialRepository;
        Repository<Prestamo> prestamoRepository;

    public Prestamo crearPrestamo(String idMaterial, String profesor, LocalDate fecha){
        Optional<Material> variable = materialRepository.findById(idMaterial);
        Material material = variable.get();
        if(idMaterial==null || profesor==null || fecha==null){
            throw new IllegalArgumentException("los valores no pueden ser nulos");
        }
        if(variable.isEmpty()){
            throw new NoEncontradoException("material no encontrado");
        } else if(!material.getEstado().equals(EstadoMaterial.DISPONIBLE)){
            throw new MaterialNoDisponibleException("el material no esta disponible");
        } else{
            Prestamo prestamoNuevo = new Prestamo(UUID.randomUUID().toString(),idMaterial,profesor,fecha);
            prestamoRepository.save(prestamoNuevo);
            material.setEstado(EstadoMaterial.PRESTADO);
            materialRepository.save(material);
        }
        return null;
    }
    public void devolverMaterial(String idMaterial) {
        Optional<Material> variable = materialRepository.findById(idMaterial);
        Material material = variable.get();
        if (idMaterial == null) {
            throw new IllegalArgumentException("el material no puede estar vacío");
        }
        if (variable.isEmpty()) {
            throw new NoEncontradoException("el material no puede estar vacío");
        }
        if (!material.getEstado().equals(EstadoMaterial.PRESTADO)) {
            throw new MaterialNoDisponibleException("El material solicitado no esta prestado");
        } else {
            material.setEstado(EstadoMaterial.DISPONIBLE);
            materialRepository.save(material);
        }
    }
}