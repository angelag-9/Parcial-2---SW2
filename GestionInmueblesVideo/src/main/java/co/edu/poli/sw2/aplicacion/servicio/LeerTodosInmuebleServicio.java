 package co.edu.poli.sw2.aplicacion.servicio;

import java.util.List;

import co.edu.poli.sw2.aplicacion.puerto.entrada.LeerTodosInmueblesCasoUso;
import co.edu.poli.sw2.aplicacion.puerto.salida.InmuebleRepository;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Servicio para consultar todos los inmuebles.
 */
public class LeerTodosInmuebleServicio
        implements LeerTodosInmueblesCasoUso {

    private final InmuebleRepository repository;

    /**
     * Constructor del servicio.
     *
     * @param repository repositorio de inmuebles
     */
    public LeerTodosInmuebleServicio(InmuebleRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtiene todos los inmuebles registrados.
     *
     * @return lista de inmuebles
     */
    @Override
    public List<Inmueble> ejecutar() {
        return repository.listar();
    }
}