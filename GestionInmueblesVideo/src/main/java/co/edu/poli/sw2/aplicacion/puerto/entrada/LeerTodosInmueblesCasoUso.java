package co.edu.poli.sw2.aplicacion.puerto.entrada;

import java.util.List;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Puerto de entrada para consultar todos los inmuebles.
 */
public interface LeerTodosInmueblesCasoUso {

    /**
     * Obtiene todos los inmuebles registrados.
     *
     * @return lista de inmuebles
     */
    List<Inmueble> ejecutar();
}