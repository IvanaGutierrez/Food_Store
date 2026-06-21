package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    // 🔵 INSERTAR
    public void insertar(Categoria c) {

        String sql = "INSERT INTO categoria(nombre, descripcion) VALUES(?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());

            ps.executeUpdate();

            System.out.println("✅ Categoria insertada");

        } catch (Exception e) {
            System.out.println("❌ Error insertar: " + e.getMessage());
        }
    }

    // 🔵 LISTAR
    public List<Categoria> listar() {

        List<Categoria> lista = new ArrayList<>();

        String sql = "SELECT * FROM categoria";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Categoria c = new Categoria();

                // ⚠️ id viene de Base
                c.setId(rs.getLong("id"));

                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));

                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("❌ Error listar: " + e.getMessage());
        }

        return lista;
    }
    public static void main(String[] args) {

        CategoriaDAO dao = new CategoriaDAO();

        Categoria c = new Categoria("Bebidas", "Gaseosas y jugos");
        dao.insertar(c);

        System.out.println("📋 LISTA:");

        for (Categoria cat : dao.listar()) {
            System.out.println(
                    cat.getId() + " - " +
                            cat.getNombre() + " - " +
                            cat.getDescripcion()
            );
        }
    }
}
