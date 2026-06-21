package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void insertar(Producto p) {

        String sql = "INSERT INTO producto(nombre, precio, descripcion, stock, imagen, disponible, categoria_id) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setInt(6, p.isDisponible() ? 1 : 0);
            ps.setLong(7, p.getCategoria().getId());

            ps.executeUpdate();

            System.out.println("✅ Producto insertado");

        } catch (Exception e) {
            System.out.println("❌ Error producto: " + e.getMessage());
        }
    }

    public List<Producto> listar() {

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM producto";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Producto p = new Producto();

                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setStock(rs.getInt("stock"));
                p.setImagen(rs.getString("imagen"));
                p.setDisponible(rs.getInt("disponible") == 1);

                Categoria c = new Categoria();
                c.setId(rs.getLong("categoria_id"));

                p.setCategoria(c);

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("❌ Error listar productos: " + e.getMessage());
        }

        return lista;
    }
    public Producto buscarPorId(long id) {

        String sql = "SELECT * FROM producto WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Producto p = new Producto();

                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setImagen(rs.getString("imagen"));
                p.setDisponible(rs.getBoolean("disponible"));

                return p;
            }

        } catch (Exception e) {
            System.out.println("❌ Error buscar producto: " + e.getMessage());
        }

        return null;
    }
    public void actualizarStock(long idProducto, int nuevoStock) {

        String sql = "UPDATE producto SET stock = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setLong(2, idProducto);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error actualizar stock: " + e.getMessage());
        }
    }
}
