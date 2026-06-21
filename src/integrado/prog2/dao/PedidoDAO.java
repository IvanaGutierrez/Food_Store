package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PedidoDAO {

    public long insertar(Pedido p) {

        String sql = "INSERT INTO pedido(usuario_id, fecha, estado, total) VALUES(?,?,?,?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, p.getUsuario().getId());
            ps.setString(2, p.getFecha().toString());
            ps.setString(3, p.getEstado());
            ps.setDouble(4, p.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                long idGenerado = rs.getLong(1);

                System.out.println("✅ Pedido insertado con ID: " + idGenerado);

                return idGenerado;
            }

        } catch (Exception e) {
            System.out.println("❌ Error pedido: " + e.getMessage());
        }

        return -1;
    }
    public void listarPedidos () {

        String sql = "SELECT p.id, u.nombre, p.fecha, p.estado, p.total " +
                "FROM pedido p " +
                "JOIN usuario u ON p.usuario_id = u.id";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("📦 LISTA DE PEDIDOS:");

            while (rs.next()) {

                System.out.println(
                        "ID: " + rs.getLong("id") +
                                " | Usuario: " + rs.getString("nombre") +
                                " | Fecha: " + rs.getString("fecha") +
                                " | Estado: " + rs.getString("estado") +
                                " | Total: $" + rs.getDouble("total")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error listar pedidos: " + e.getMessage());
        }
    }
    public void actualizarTotal(long idPedido, double total) {

        String sql =
                "UPDATE pedido " +
                        "SET total = ? " +
                        "WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, total);
            ps.setLong(2, idPedido);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("❌ Error actualizar total: "
                    + e.getMessage());
        }
    }
    public void listarPorUsuario(long idUsuario) {

        String sql =
                "SELECT * FROM pedido WHERE usuario_id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setLong(1, idUsuario);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                        "Pedido: " +
                                rs.getLong("id")
                                + " | Fecha: "
                                + rs.getString("fecha")
                                + " | Total: $"
                                + rs.getDouble("total")
                );
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

}