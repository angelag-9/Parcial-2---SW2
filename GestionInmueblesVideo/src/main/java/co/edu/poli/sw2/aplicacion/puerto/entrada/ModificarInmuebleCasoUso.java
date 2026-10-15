package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Puerto de entrada para actualizar inmuebles.
 */
public interface ModificarInmuebleCasoUso {

    /**
     * Actualiza los datos de un inmueble existente.
     *
     * @param inmueble inmueble con los datos actualizados
     */
    void ejecutar(Inmueble inmueble);
}