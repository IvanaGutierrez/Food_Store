package integrado.prog2.service;

import integrado.prog2.entities.Producto;

import java.util.List;

public class MenuService {

    public void mostrarProductos(
            List<Producto> lista
    ) {

        for (Producto p : lista) {

            System.out.println(
                    "ID: " + p.getId()
                            + " | " + p.getNombre()
                            + " | $" + p.getPrecio()
                            + " | Stock: " + p.getStock()
            );
        }
    }
}
