package integrado.prog2.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL =
            "jdbc:sqlite:C:/Users/Ivana Gutierrez/Documents/TrabajoIntegradorNaveda/Food_Store_TPI_base_de_datos.db";// tu archivo .db

    public static Connection conectar() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL);
            //System.out.println("✅ CONEXIÓN EXITOSA A LA BASE DE DATOS");
        } catch (SQLException e) {
            System.out.println("❌ ERROR DE CONEXIÓN");
            System.out.println(e.getMessage());
        }

        return conn;
    }
}