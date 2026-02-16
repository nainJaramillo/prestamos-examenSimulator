package es.fplumara.dam1.prestamos.model;

public class Portatil extends Material{

    private int ramGB;

    public Portatil(String id, int ramGB) {
        super(id);
        this.ramGB = ramGB;
    }

    public int getRamGB() {
        return ramGB;
    }

    public void setRamGB(int ramGB) {
        this.ramGB = ramGB;
    }

    @Override
    public String getTipo() {
        return "PORTATIL";
    }
}
