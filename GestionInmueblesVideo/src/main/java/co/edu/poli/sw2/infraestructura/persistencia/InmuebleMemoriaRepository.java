package co.edu.poli.sw2.infraestructura.persistencia;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import co.edu.poli.sw2.aplicacion.puerto.salida.InmuebleRepository;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Adaptador de persistencia que almacena los inmuebles
 * temporalmente en memoria.
 *
 * Implementa el puerto de salida InmuebleRepository.
 */
public class InmuebleMemoriaRepository implements InmuebleRepository {

    private final Map<String, Inmueble> inmuebles;

    /**
     * Inicializa el repositorio en memoria.
     */
    public InmuebleMemoriaRepository() {
        this.inmuebles = new LinkedHashMap<>();
    }

    /**
     * Guarda un nuevo inmueble.
     *
     * @param inmueble inmueble que se desea registrar
     */
    @Override
    public void guardar(Inmueble inmueble) {

        if (inmueble == null) {
            throw new IllegalArgumentException(
                "El inmueble no puede ser nulo.");
        }

        String chip = inmueble.getChip();

        if (inmuebles.containsKey(chip)) {
            throw new IllegalArgumentException(
                "Ya existe un inmueble con ese CHIP.");
        }

        inmuebles.put(chip, inmueble);
    }

    /**
     * Busca un inmueble utilizando su CHIP.
     *
     * @param chip identificador del inmueble
     * @return inmueble encontrado, si existe
     */
    @Override
    public Optional<Inmueble> buscarPorChip(String chip) {
        return Optional.ofNullable(inmuebles.get(chip));
    }

    /**
     * Obtiene todos los inmuebles registrados.
     *
     * @return lista de inmuebles
     */
    @Override
    public List<Inmueble> listar() {
        return new ArrayList<>(inmuebles.values());
    }

    /**
     * Actualiza un inmueble existente.
     *
     * @param inmueble inmueble con los nuevos datos
     */
    @Override
    public void actualizar(Inmueble inmueble) {

        if (inmueble == null) {
            throw new IllegalArgumentException(
                "El inmueble no puede ser nulo.");
        }

        String chip = inmueble.getChip();

        if (!inmuebles.containsKey(chip)) {
            throw new IllegalArgumentException(
                "No existe un inmueble con ese CHIP.");
        }

        inmuebles.put(chip, inmueble);
    }

    /**
     * Elimina un inmueble mediante su CHIP.
     *
     * @param chip identificador del inmueble
     */
    @Override
    public void eliminar(String chip) {

        if (inmuebles.remove(chip) == null) {
            throw new IllegalArgumentException(
                "No existe un inmueble con ese CHIP.");
        }
    }
}