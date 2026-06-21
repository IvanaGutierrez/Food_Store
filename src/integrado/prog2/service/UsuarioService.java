package integrado.prog2.service;

import integrado.prog2.dao.UsuarioDAO;
import integrado.prog2.entities.Usuario;
import exception.EntidadNoEncontradaException;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void registrarUsuario(Usuario u) {

        if (u.getNombre() == null || u.getNombre().isBlank()) {
            throw new EntidadNoEncontradaException(
                    "Debe ingresar un nombre"
            );
        }

        usuarioDAO.insertar(u);

    }
}
