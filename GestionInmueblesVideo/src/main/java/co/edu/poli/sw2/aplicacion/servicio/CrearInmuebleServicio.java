package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.CrearInmuebleCasoUso;
import co.edu.poli.sw2.aplicacion.puerto.salida.InmuebleRepository;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Servicio encargado de registrar nuevos inmuebles.
 * Implementa el caso de uso de creacion.
 */
public class CrearInmuebleServicio implements CrearInmuebleCasoUso {

    private final InmuebleRepository repository;

    /**
     * Constructor que recibe el repositorio.
     *
     * @param repository puerto de salida de persistencia
     */
    public CrearInmuebleServicio(InmuebleRepository repository) {
        this.repository = repository;
    }

    /**
     * Ejecuta el registro de un inmueble.
     *
     * @param inmueble inmueble que se desea crear
     */
    @Override
    public void ejecutar(Inmueble inmueble) {

        if (inmueble == null) {
            throw new IllegalArgumentException(
                "El inmueble no puede ser nulo.");
        }

        if (repository.buscarPorChip(inmueble.getChip()).isPresent()) {
            throw new IllegalArgumentException(
                "Ya existe un inmueble con ese CHIP.");
        }

        repository.guardar(inmueble);
    }
}