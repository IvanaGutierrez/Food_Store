package integrado.prog2.service;

import integrado.prog2.dao.DetallePedidoDAO;
import integrado.prog2.dao.PedidoDAO;
import integrado.prog2.dao.ProductoDAO;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Pedido;
import exception.EntidadNoEncontradaException;

public class PedidoService {

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private DetallePedidoDAO detalleDAO = new DetallePedidoDAO();
    private ProductoService productoService = new ProductoService();

    public long crearPedido(Pedido pedido) {

        if (pedido.getUsuario() == null) {
            throw new EntidadNoEncontradaException(
                    "El pedido debe tener un usuario asociado"
            );
        }

        return pedidoDAO.insertar(pedido);
    }

    public double agregarProducto(Pedido pedido, long idProducto, int cantidad) {

        Producto producto =
                productoDAO.buscarPorId(idProducto);

        if (producto == null) {

            throw new EntidadNoEncontradaException(
                    "No existe producto con ID " + idProducto
            );
        }
        productoService.validarStock(
                producto,
                cantidad
        );

        productoService.descontarStock(
                producto,
                cantidad
        );

        DetallePedido detalle =
                new DetallePedido(
                        pedido,
                        producto,
                        cantidad
                );

        pedido.getDetalles().add(detalle);

        return detalle.getSubtotal();
    }
    public void finalizarPedido(Pedido pedido, double total ) {

        pedidoDAO.actualizarTotal(
                pedido.getId(),
                total
        );

        pedido.setTotal(total);
    }
}
