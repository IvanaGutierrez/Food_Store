package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.DetallePedido;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import integrado.prog2.config.ConexionDB;

public class DetallePedidoDAO {

    public void insertar(DetallePedido d) {

        String sql = "INSERT INTO detalle_pedido(pedido_id, producto_id, cantidad, subtotal) VALUES(?,?,?,?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, d.getPedido().getId());
            ps.setLong(2, d.getProducto().getId());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getSubtotal());

            ps.executeUpdate();

            System.out.println("✅ Detalle de pedido insertado");

        } catch (Exception e) {
            System.out.println("❌ Error detalle pedido: " + e.getMessage());
        }
    }
    public void listarPorPedido(long idPedido) {

        String sql = "SELECT dp.id, pr.nombre, dp.cantidad, dp.subtotal " +
                "FROM detalle_pedido dp " +
                "JOIN producto pr ON dp.producto_id = pr.id " +
                "WHERE dp.pedido_id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idPedido);

            ResultSet rs = ps.executeQuery();

            System.out.println("🧾 DETALLE DEL PEDIDO " + idPedido);

            while (rs.next()) {
                System.out.println(
                        "Producto: " + rs.getString("nombre") +
                                " | Cantidad: " + rs.getInt("cantidad") +
                                " | Subtotal: $" + rs.getDouble("subtotal")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error detalle pedido: " + e.getMessage());
        }
    }

}