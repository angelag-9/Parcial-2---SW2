package co.edu.poli.sw2.aplicacion.puerto.entrada;

/**
 * Puerto de entrada para eliminar inmuebles.
 */
public interface EliminarInmuebleCasoUso {

    /**
     * Elimina un inmueble utilizando su CHIP.
     *
     * @param chip identificador del inmueble
     */
    void ejecutar(String chip);
}