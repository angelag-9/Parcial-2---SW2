package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.EliminarInmuebleCasoUso;
import co.edu.poli.sw2.aplicacion.puerto.salida.InmuebleRepository;

/**
 * Servicio encargado de eliminar inmuebles.
 */
public class EliminarInmuebleServicio
        implements EliminarInmuebleCasoUso {

    private final InmuebleRepository repository;

    /**
     * Constructor del servicio.
     *
     * @param repository repositorio de inmuebles
     */
    public EliminarInmuebleServicio(InmuebleRepository repository) {
        this.repository = repository;
    }

    /**
     * Elimina un inmueble mediante su CHIP.
     *
     * @param chip identificador del inmueble
     */
    @Override
    public void ejecutar(String chip) {

        if (chip == null || chip.isBlank()) {
            throw new IllegalArgumentException(
                "Debe ingresar un CHIP.");
        }

        String chipBuscado = chip.trim();

        if (repository.buscarPorChip(chipBuscado).isEmpty()) {
            throw new IllegalArgumentException(
                "No existe un inmueble con ese CHIP.");
        }

        repository.eliminar(chipBuscado);
    }
}