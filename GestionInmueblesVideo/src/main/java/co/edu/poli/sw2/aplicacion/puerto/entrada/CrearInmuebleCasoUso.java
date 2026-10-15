package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Puerto de entrada para registrar inmuebles.
 */
public interface CrearInmuebleCasoUso {

    /**
     * Registra un inmueble.
     *
     * @param inmueble inmueble que se desea registrar
     */
    void ejecutar(Inmueble inmueble);
}