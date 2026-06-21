package integrado.prog2;

import integrado.prog2.dao.DetallePedidoDAO;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;


public class TestDetallePedido {

    public static void main(String[] args) {

        DetallePedidoDAO detalleDAO = new DetallePedidoDAO();

        // 🔵 Pedido existente en la base
        Pedido p = new Pedido();
        p.setId(1L);

        // 🔵 Producto existente en la base
        Producto prod = new Producto();
        prod.setId(1L);

        // 🔵 Crear detalle
        DetallePedido d = new DetallePedido();
        d.setPedido(p);
        d.setProducto(prod);
        d.setCantidad(2);
        d.setSubtotal(prod.getPrecio() * 2); // o ya calculado

        // 🔥 insertar en BD
        detalleDAO.insertar(d);

        System.out.println("✅ PRUEBA DETALLE PEDIDO OK");
    }
}