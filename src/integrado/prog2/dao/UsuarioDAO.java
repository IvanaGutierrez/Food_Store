package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // 🔵 INSERTAR USUARIO
    public void insertar(Usuario u) {

        String sql = "INSERT INTO usuario(nombre, apellido, mail, celular, contrasenia, rol) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getMail());
            ps.setString(4, u.getCelular());
            ps.setString(5, u.getContrasenia());
            ps.setString(6, u.getRol().name());

            ps.executeUpdate();

            System.out.println("✅ Usuario insertado");

        } catch (Exception e) {
            System.out.println("❌ Error insertar usuario: " + e.getMessage());
        }
    }

    // 🔵 LISTAR USUARIOS
    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setMail(rs.getString("mail"));
                u.setCelular(rs.getString("celular"));
                u.setContrasenia(rs.getString("contrasenia"));

                // rol como texto simple
                u.setRol(Rol.valueOf(rs.getString("rol")));

                lista.add(u);
            }

        } catch (Exception e) {
            System.out.println("❌ Error listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    // 🔥 LOGIN (LO MÁS IMPORTANTE)
    public Usuario login(String mail, String contrasenia) {

        String sql = "SELECT * FROM usuario WHERE mail = ? AND contrasenia = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mail);
            ps.setString(2, contrasenia);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setMail(rs.getString("mail"));
                u.setCelular(rs.getString("celular"));
                u.setContrasenia(rs.getString("contrasenia"));
                u.setRol(Rol.valueOf(rs.getString("rol")));

                return u;
            }

        } catch (Exception e) {
            System.out.println("❌ Error login: " + e.getMessage());
        }

        return null;
    }

    public static void main(String[] args) {

        UsuarioDAO dao1 = new UsuarioDAO();

        Usuario u = new Usuario();

        u.setNombre("Juan");
        u.setApellido("Perez");
        u.setMail("cliente@mail.com");
        u.setCelular("123456");
        u.setContrasenia("1234");
        u.setRol(Rol.USUARIO);

        dao1.insertar(u);
    }
}