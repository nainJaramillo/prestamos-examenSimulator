package es.fplumara.dam1.prestamos.service;


import es.fplumara.dam1.prestamos.model.Material;
import es.fplumara.dam1.prestamos.repository.MaterialRepositoryImpl;
import es.fplumara.dam1.prestamos.repository.PrestamoRepositoryImpl;

import java.time.LocalDate;

public class PrestamoService {
    private MaterialService materialService;
    private PrestamoRepositoryImpl prestamoRepository;

    public PrestamoService(MaterialService materialService, PrestamoRepositoryImpl prestamoRepository) {
        this.materialService = materialService;
        this.prestamoRepository = prestamoRepository;
    }

    public PrestamoService(MaterialRepositoryImpl materialRepository) {
    }

    public void crearPrestamo(String idMaterial, String profesor, LocalDate fecha){
        if (idMaterial == null || idMaterial.isEmpty()){
            throw new IllegalArgumentException("invalido");
        }
    }

    public void devolerMaterial(long l) {
    }
}