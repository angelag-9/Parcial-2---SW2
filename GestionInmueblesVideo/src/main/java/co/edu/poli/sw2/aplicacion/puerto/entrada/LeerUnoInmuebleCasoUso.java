package co.edu.poli.sw2.aplicacion.puerto.entrada;

import java.util.Optional;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Puerto de entrada para consultar un inmueble por CHIP.
 */
public interface LeerUnoInmuebleCasoUso {

    /**
     * Busca un inmueble utilizando su CHIP.
     *
     * @param chip identificador del inmueble
     * @return inmueble encontrado, si existe
     */
    Optional<Inmueble> ejecutar(String chip);
}