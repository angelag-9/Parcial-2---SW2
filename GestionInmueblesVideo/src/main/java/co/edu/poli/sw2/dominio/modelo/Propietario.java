package co.edu.poli.sw2.dominio.modelo;

/**
 * Representa al propietario de uno o varios inmuebles.
 */
public class Propietario {

    private String identificacion;
    private String nombre;
    private String correoElectronico;

    /**
     * Constructor de la clase Propietario.
     *
     * @param identificacion identificacion del propietario
     * @param nombre nombre completo
     * @param correoElectronico correo del propietario
     */
    public Propietario(String identificacion, String nombre,
                       String correoElectronico) {

        if (identificacion == null || identificacion.isBlank()
                || nombre == null || nombre.isBlank()
                || correoElectronico == null
                || correoElectronico.isBlank()) {
            throw new IllegalArgumentException(
                "Los datos del propietario son obligatorios.");
        }

        this.identificacion = identificacion.trim();
        this.nombre = nombre.trim();
        this.correoElectronico = correoElectronico.trim();
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException(
                "La identificacion es obligatoria.");
        }
        this.identificacion = identificacion.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        if (correoElectronico == null
                || correoElectronico.isBlank()) {
            throw new IllegalArgumentException(
                "El correo es obligatorio.");
        }
        this.correoElectronico = correoElectronico.trim();
    }

    @Override
    public String toString() {
        return nombre + " (" + identificacion + ")";
    }
}