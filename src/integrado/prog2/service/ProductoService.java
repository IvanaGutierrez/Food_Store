package integrado.prog2.service;

import exception.EntidadNoEncontradaException;
import integrado.prog2.dao.ProductoDAO;
import integrado.prog2.entities.Producto;

public class ProductoService {

    private ProductoDAO productoDAO = new ProductoDAO();

    public Producto buscarProducto(long id) {

        Producto p = productoDAO.buscarPorId(id);

        if (p == null) {
            throw new EntidadNoEncontradaException(
                    "No existe producto con ID " + id
            );
        }

        return p;
    }
    public void validarStock(Producto p, int cantidad) {

        if (cantidad > p.getStock()) {

            throw new exception.StockInvalidoException(
                    "Stock insuficiente"
            );
        }
    }

    public void descontarStock(Producto producto, int cantidad) {

        validarStock(producto, cantidad);

        int nuevoStock =
                producto.getStock() - cantidad;

        productoDAO.actualizarStock(
                producto.getId(),
                nuevoStock
        );
    }
}
