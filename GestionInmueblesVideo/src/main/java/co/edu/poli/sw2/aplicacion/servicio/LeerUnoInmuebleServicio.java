package co.edu.poli.sw2.aplicacion.servicio;

import java.util.Optional;

import co.edu.poli.sw2.aplicacion.puerto.entrada.LeerUnoInmuebleCasoUso;
import co.edu.poli.sw2.aplicacion.puerto.salida.InmuebleRepository;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Servicio para consultar un inmueble mediante su CHIP.
 */
public class LeerUnoInmuebleServicio
        implements LeerUnoInmuebleCasoUso {

    private final InmuebleRepository repository;

    /**
     * Constructor del servicio.
     *
     * @param repository repositorio de inmuebles
     */
    public LeerUnoInmuebleServicio(InmuebleRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca un inmueble por CHIP.
     *
     * @param chip identificador del inmueble
     * @return inmueble encontrado, si existe
     */
    @Override
    public Optional<Inmueble> ejecutar(String chip) {

        if (chip == null || chip.isBlank()) {
            throw new IllegalArgumentException(
                "Debe ingresar un CHIP.");
        }

        return repository.buscarPorChip(chip.trim());
    }
}