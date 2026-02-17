package es.fplumara.dam1.prestamos.service;

import es.fplumara.dam1.prestamos.csv.CSVMaterialImporter;
import es.fplumara.dam1.prestamos.csv.RegistroMaterialCsv;
import es.fplumara.dam1.prestamos.exception.DuplicadoException;
import es.fplumara.dam1.prestamos.exception.MaterialNoDisponibleException;
import es.fplumara.dam1.prestamos.exception.NoEncontradoException;
import es.fplumara.dam1.prestamos.model.EstadoMaterial;
import es.fplumara.dam1.prestamos.model.Material;

import java.util.ArrayList;
import java.util.List;

public class MaterialService {
    private List<Material> materiales = new ArrayList<>();

    public void registrarMaterial(Material m){
        if (m == null){
            throw new IllegalArgumentException("NO PUEDE SER NULL");
        }
        if (m.getId() == null || m.getId().isEmpty()){
            throw new IllegalArgumentException("El id no puede ser null o vacio");
        }
        for (Material mat : materiales) {
            if (mat.getId().equals(m.getId())){
                throw new DuplicadoException("Ya existe un material con el mismo id");
            }
        }
        materiales.add(m);
    }

    public void darDeBaja(String idMaterial){
        Material encontrado= null;
        for (Material mat : materiales) {
            if (mat.getId().equals(idMaterial)){
                encontrado=mat;
                break;
            }
        }
        if (encontrado == null){
            throw new NoEncontradoException("Material no encontrado");
        }
        if (encontrado.getEstado() == EstadoMaterial.BAJA){
            throw new MaterialNoDisponibleException("El material ya esta de BAJA");
        }
        encontrado.setEstado(EstadoMaterial.BAJA);
    }
    public List<Material> listar(){
        return materiales;
    }
}
