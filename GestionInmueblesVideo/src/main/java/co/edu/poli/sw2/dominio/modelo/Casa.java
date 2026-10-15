package co.edu.poli.sw2.dominio.modelo;

import java.time.LocalDate;

/**
 * Representa una casa.
 * Puede contener un sotano de manera opcional.
 */
public class Casa extends Inmueble {

    private int cantidadPisos;
    private Sotano sotano;

    /**
     * Constructor de la clase Casa.
     *
     * @param chip identificador del inmueble
     * @param fechaCompra fecha de compra
     * @param direccion direccion de la casa
     * @param estado estado de la casa
     * @param propietario propietario
     * @param cantidadPisos cantidad de pisos
     * @param sotano sotano opcional, puede ser null
     */
    public Casa(String chip, LocalDate fechaCompra,
                String direccion, String estado,
                Propietario propietario, int cantidadPisos,
                Sotano sotano) {

        super(chip, fechaCompra, direccion, estado, propietario);

        if (cantidadPisos <= 0) {
            throw new IllegalArgumentException(
                "La casa debe tener al menos un piso.");
        }

        this.cantidadPisos = cantidadPisos;
        this.sotano = sotano;
    }

    public int getCantidadPisos() {
        return cantidadPisos;
    }

    public void setCantidadPisos(int cantidadPisos) {
        if (cantidadPisos <= 0) {
            throw new IllegalArgumentException(
                "La casa debe tener al menos un piso.");
        }
        this.cantidadPisos = cantidadPisos;
    }

    public Sotano getSotano() {
        return sotano;
    }

    public void setSotano(Sotano sotano) {
        this.sotano = sotano;
    }

    @Override
    public String getTipo() {
        return "Casa";
    }
}