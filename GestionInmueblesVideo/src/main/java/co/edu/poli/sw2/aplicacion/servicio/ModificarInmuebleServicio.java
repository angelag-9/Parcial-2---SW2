package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ModificarInmuebleCasoUso;
import co.edu.poli.sw2.aplicacion.puerto.salida.InmuebleRepository;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Servicio encargado de modificar inmuebles existentes.
 */
public class ModificarInmuebleServicio
        implements ModificarInmuebleCasoUso {

    private final InmuebleRepository repository;

    /**
     * Constructor del servicio.
     *
     * @param repository repositorio de inmuebles
     */
    public ModificarInmuebleServicio(InmuebleRepository repository) {
        this.repository = repository;
    }

    /**
     * Actualiza un inmueble registrado.
     *
     * @param inmueble inmueble con los nuevos datos
     */
    @Override
    public void ejecutar(Inmueble inmueble) {

        if (inmueble == null) {
            throw new IllegalArgumentException(
                "El inmueble no puede ser nulo.");
        }

        if (repository.buscarPorChip(inmueble.getChip()).isEmpty()) {
            throw new IllegalArgumentException(
                "No existe un inmueble con ese CHIP.");
        }

        repository.actualizar(inmueble);
    }
}