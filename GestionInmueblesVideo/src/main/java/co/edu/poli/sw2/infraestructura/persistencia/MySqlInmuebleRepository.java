package co.edu.poli.sw2.infraestructura.persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.poli.sw2.aplicacion.puerto.salida.InmuebleRepository;
import co.edu.poli.sw2.dominio.modelo.*;

/**
 * Adaptador de persistencia MySQL.
 * Implementa las operaciones CRUD de inmuebles mediante JDBC.
 */
public class MySqlInmuebleRepository implements InmuebleRepository {

    /**
     * Guarda un inmueble en MySQL.
     *
     * @param inmueble inmueble que se desea registrar
     */
    @Override
    public void guardar(Inmueble inmueble) {

        String sql = """
            INSERT INTO inmueble
            (chip, fecha_compra, direccion, estado,
             propietario_id, tipo, numero_piso, cantidad_pisos,
             area_sotano, uso_sotano)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            colocarDatos(ps, inmueble);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al guardar el inmueble: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta un inmueble utilizando su CHIP.
     *
     * @param chip identificador del inmueble
     * @return inmueble encontrado, si existe
     */
    @Override
    public Optional<Inmueble> buscarPorChip(String chip) {

        String sql = """
            SELECT i.*, p.nombre, p.correo_electronico
            FROM inmueble i
            INNER JOIN propietario p
                ON i.propietario_id = p.identificacion
            WHERE i.chip = ?
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, chip);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(construirInmueble(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al consultar el inmueble: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta todos los inmuebles registrados.
     *
     * @return lista de inmuebles
     */
    @Override
    public List<Inmueble> listar() {

        List<Inmueble> inmuebles = new ArrayList<>();

        String sql = """
            SELECT i.*, p.nombre, p.correo_electronico
            FROM inmueble i
            INNER JOIN propietario p
                ON i.propietario_id = p.identificacion
            ORDER BY i.chip
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                inmuebles.add(construirInmueble(rs));
            }

            return inmuebles;

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al listar inmuebles: " + e.getMessage(), e);
        }
    }

    /**
     * Modifica un inmueble existente.
     *
     * @param inmueble inmueble con los datos nuevos
     */
    @Override
    public void actualizar(Inmueble inmueble) {

        String sql = """
            UPDATE inmueble
            SET fecha_compra = ?,
                direccion = ?,
                estado = ?,
                propietario_id = ?,
                tipo = ?,
                numero_piso = ?,
                cantidad_pisos = ?,
                area_sotano = ?,
                uso_sotano = ?
            WHERE chip = ?
            """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Reutilizamos los parametros desde la fecha.
            colocarDatosActualizacion(ps, inmueble);
            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new IllegalArgumentException(
                    "No existe un inmueble con ese CHIP.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al actualizar el inmueble: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un inmueble por CHIP.
     *
     * @param chip identificador del inmueble
     */
    @Override
    public void eliminar(String chip) {

        String sql = "DELETE FROM inmueble WHERE chip = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, chip);
            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new IllegalArgumentException(
                    "No existe un inmueble con ese CHIP.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al eliminar el inmueble: " + e.getMessage(), e);
        }
    }

    /**
     * Coloca los parametros para insertar un inmueble.
     */
    private void colocarDatos(
            PreparedStatement ps, Inmueble inmueble)
            throws SQLException {

        ps.setString(1, inmueble.getChip());
        ps.setDate(2, Date.valueOf(inmueble.getFechaCompra()));
        ps.setString(3, inmueble.getDireccion());
        ps.setString(4, inmueble.getEstado());
        ps.setString(5, inmueble.getPropietario().getIdentificacion());
        ps.setString(6, inmueble.getTipo());

        colocarDatosEspecificos(ps, inmueble, 7);
    }

    /**
     * Coloca los parametros para actualizar un inmueble.
     */
    private void colocarDatosActualizacion(
            PreparedStatement ps, Inmueble inmueble)
            throws SQLException {

        ps.setDate(1, Date.valueOf(inmueble.getFechaCompra()));
        ps.setString(2, inmueble.getDireccion());
        ps.setString(3, inmueble.getEstado());
        ps.setString(4, inmueble.getPropietario().getIdentificacion());
        ps.setString(5, inmueble.getTipo());

        colocarDatosEspecificos(ps, inmueble, 6);

        ps.setString(10, inmueble.getChip());
    }

    /**
     * Coloca los datos particulares de casas y apartamentos.
     */
    private void colocarDatosEspecificos(
            PreparedStatement ps, Inmueble inmueble, int inicio)
            throws SQLException {

        if (inmueble instanceof Apartamento a) {

            ps.setInt(inicio, a.getNumeroPiso());
            ps.setNull(inicio + 1, Types.INTEGER);
            ps.setNull(inicio + 2, Types.DECIMAL);
            ps.setNull(inicio + 3, Types.VARCHAR);

        } else if (inmueble instanceof Casa c) {

            ps.setNull(inicio, Types.INTEGER);
            ps.setInt(inicio + 1, c.getCantidadPisos());

            if (c.getSotano() != null) {
                ps.setDouble(inicio + 2, c.getSotano().getAreaM2());
                ps.setString(inicio + 3, c.getSotano().getTipoUso());
            } else {
                ps.setNull(inicio + 2, Types.DECIMAL);
                ps.setNull(inicio + 3, Types.VARCHAR);
            }
        } else {
            throw new IllegalArgumentException(
                "Tipo de inmueble no reconocido.");
        }
    }

    /**
     * Convierte una fila SQL en un objeto de dominio.
     */
    private Inmueble construirInmueble(ResultSet rs)
            throws SQLException {

        Propietario propietario = new Propietario(
            rs.getString("propietario_id"),
            rs.getString("nombre"),
            rs.getString("correo_electronico")
        );

        String chip = rs.getString("chip");
        java.time.LocalDate fecha =
            rs.getDate("fecha_compra").toLocalDate();
        String direccion = rs.getString("direccion");
        String estado = rs.getString("estado");
        String tipo = rs.getString("tipo");

        if ("Apartamento".equals(tipo)) {

            return new Apartamento(
                chip,
                fecha,
                direccion,
                estado,
                propietario,
                rs.getInt("numero_piso")
            );
        }

        if ("Casa".equals(tipo)) {

            Sotano sotano = null;
            double area = rs.getDouble("area_sotano");

            if (!rs.wasNull()) {
                sotano = new Sotano(
                    area,
                    rs.getString("uso_sotano")
                );
            }

            return new Casa(
                chip,
                fecha,
                direccion,
                estado,
                propietario,
                rs.getInt("cantidad_pisos"),
                sotano
            );
        }

        throw new SQLException(
            "Tipo de inmueble desconocido: " + tipo);
    }
}