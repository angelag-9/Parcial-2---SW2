package co.edu.poli.sw2.dominio.modelo;

import java.time.LocalDate;

/**
 * Clase abstracta que representa un inmueble.
 * Contiene los atributos comunes de casas y apartamentos.
 */
public abstract class Inmueble {

    private String chip;
    private LocalDate fechaCompra;
    private String direccion;
    private String estado;
    private Propietario propietario;

    /**
     * Constructor de la clase Inmueble.
     *
     * @param chip identificador unico del inmueble
     * @param fechaCompra fecha de compra
     * @param direccion direccion del inmueble
     * @param estado estado actual
     * @param propietario propietario del inmueble
     */
    public Inmueble(String chip, LocalDate fechaCompra,
                    String direccion, String estado,
                    Propietario propietario) {

        if (chip == null || chip.isBlank()) {
            throw new IllegalArgumentException(
                "El CHIP es obligatorio.");
        }

        if (fechaCompra == null) {
            throw new IllegalArgumentException(
                "La fecha de compra es obligatoria.");
        }

        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException(
                "La direccion es obligatoria.");
        }

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException(
                "El estado es obligatorio.");
        }

        if (propietario == null) {
            throw new IllegalArgumentException(
                "Debe seleccionar un propietario.");
        }

        this.chip = chip.trim();
        this.fechaCompra = fechaCompra;
        this.direccion = direccion.trim();
        this.estado = estado.trim();
        this.propietario = propietario;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        if (chip == null || chip.isBlank()) {
            throw new IllegalArgumentException(
                "El CHIP es obligatorio.");
        }
        this.chip = chip.trim();
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        if (fechaCompra == null) {
            throw new IllegalArgumentException(
                "La fecha de compra es obligatoria.");
        }
        this.fechaCompra = fechaCompra;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException(
                "La direccion es obligatoria.");
        }
        this.direccion = direccion.trim();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException(
                "El estado es obligatorio.");
        }
        this.estado = estado.trim();
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        if (propietario == null) {
            throw new IllegalArgumentException(
                "El propietario es obligatorio.");
        }
        this.propietario = propietario;
    }

    /**
     * Obtiene el tipo de inmueble.
     *
     * @return Casa o Apartamento
     */
    public abstract String getTipo();
}