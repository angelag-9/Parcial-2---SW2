package co.edu.poli.sw2.aplicacion.puerto.salida;

import java.util.List;
import java.util.Optional;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Puerto de salida que define las operaciones de
 * persistencia de inmuebles.
 *
 * Puede ser implementado por un repositorio en
 * memoria o por un repositorio MySQL.
 */
public interface InmuebleRepository {

    /**
     * Guarda un inmueble nuevo.
     *
     * @param inmueble inmueble a guardar
     */
    void guardar(Inmueble inmueble);

    /**
     * Busca un inmueble por su CHIP.
     *
     * @param chip identificador del inmueble
     * @return inmueble encontrado, si existe
     */
    Optional<Inmueble> buscarPorChip(String chip);

    /**
     * Obtiene todos los inmuebles.
     *
     * @return lista de inmuebles
     */
    List<Inmueble> listar();

    /**
     * Actualiza un inmueble existente.
     *
     * @param inmueble inmueble actualizado
     */
    void actualizar(Inmueble inmueble);

    /**
     * Elimina un inmueble por su CHIP.
     *
     * @param chip identificador del inmueble
     */
    void eliminar(String chip);
}