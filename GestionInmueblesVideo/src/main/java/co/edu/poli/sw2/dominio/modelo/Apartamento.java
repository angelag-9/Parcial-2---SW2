package co.edu.poli.sw2.dominio.modelo;

import java.time.LocalDate;

/**
 * Representa un apartamento.
 * Hereda los atributos comunes de Inmueble.
 */
public class Apartamento extends Inmueble {

    private int numeroPiso;

    /**
     * Constructor de la clase Apartamento.
     *
     * @param chip identificador del inmueble
     * @param fechaCompra fecha de compra
     * @param direccion direccion del apartamento
     * @param estado estado del apartamento
     * @param propietario propietario
     * @param numeroPiso piso donde se encuentra
     */
    public Apartamento(String chip, LocalDate fechaCompra,
                       String direccion, String estado,
                       Propietario propietario, int numeroPiso) {

        super(chip, fechaCompra, direccion, estado, propietario);

        if (numeroPiso < 0) {
            throw new IllegalArgumentException(
                "El numero de piso no puede ser negativo.");
        }

        this.numeroPiso = numeroPiso;
    }

    public int getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(int numeroPiso) {
        if (numeroPiso < 0) {
            throw new IllegalArgumentException(
                "El numero de piso no puede ser negativo.");
        }
        this.numeroPiso = numeroPiso;
    }

    @Override
    public String getTipo() {
        return "Apartamento";
    }
}