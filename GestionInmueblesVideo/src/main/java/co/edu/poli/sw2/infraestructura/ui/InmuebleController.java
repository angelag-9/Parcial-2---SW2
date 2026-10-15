package co.edu.poli.sw2.infraestructura.ui;

import java.util.List;
import java.util.Optional;

import co.edu.poli.sw2.aplicacion.puerto.entrada.*;
import co.edu.poli.sw2.dominio.modelo.Inmueble;

/**
 * Adaptador de entrada que conecta JavaFX
 * con los casos de uso de gestion de inmuebles.
 */
public class InmuebleController {

    private final CrearInmuebleCasoUso crear;
    private final LeerUnoInmuebleCasoUso consultar;
    private final LeerTodosInmueblesCasoUso listar;
    private final ModificarInmuebleCasoUso modificar;
    private final EliminarInmuebleCasoUso eliminar;

    /**
     * Recibe los casos de uso por inyeccion de dependencias.
     */
    public InmuebleController(
            CrearInmuebleCasoUso crear,
            LeerUnoInmuebleCasoUso consultar,
            LeerTodosInmueblesCasoUso listar,
            ModificarInmuebleCasoUso modificar,
            EliminarInmuebleCasoUso eliminar) {

        this.crear = crear;
        this.consultar = consultar;
        this.listar = listar;
        this.modificar = modificar;
        this.eliminar = eliminar;
    }

    /** Registra un inmueble nuevo. */
    public void crear(Inmueble inmueble) {
        crear.ejecutar(inmueble);
    }

    /** Busca un inmueble mediante su CHIP. */
    public Optional<Inmueble> consultar(String chip) {
        return consultar.ejecutar(chip);
    }

    /** Devuelve todos los inmuebles registrados. */
    public List<Inmueble> listar() {
        return listar.ejecutar();
    }

    /** Actualiza un inmueble existente. */
    public void modificar(Inmueble inmueble) {
        modificar.ejecutar(inmueble);
    }

    /** Elimina un inmueble mediante su CHIP. */
    public void eliminar(String chip) {
        eliminar.ejecutar(chip);
    }
}