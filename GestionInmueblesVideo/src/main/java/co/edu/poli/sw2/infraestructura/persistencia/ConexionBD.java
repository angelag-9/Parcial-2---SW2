package co.edu.poli.sw2.infraestructura.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Establece la conexion entre Java y MySQL
 * utilizando JDBC.
 */
public class ConexionBD {

    private static final String URL =
        "jdbc:mysql://localhost:3306/gestion_inmuebles";

    private static final String USUARIO =
        System.getenv().getOrDefault("DB_USER", "root");

    private static final String CLAVE =
        System.getenv().getOrDefault("DB_PASSWORD", "");

    /**
     * Obtiene una conexion a la base de datos.
     *
     * @return conexion JDBC
     * @throws SQLException si no se puede conectar
     */
    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
            URL, USUARIO, CLAVE
        );
    }
}