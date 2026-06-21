package integrado.prog2;

import integrado.prog2.dao.*;
import integrado.prog2.entities.*;
import integrado.prog2.enums.Rol;
import integrado.prog2.service.MenuService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import exception.StockInvalidoException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static PedidoService pedidoService = new PedidoService();

    static UsuarioDAO usuarioDAO = new UsuarioDAO();
    static ProductoDAO productoDAO = new ProductoDAO();
    static PedidoDAO pedidoDAO = new PedidoDAO();
    static DetallePedidoDAO detalleDAO = new DetallePedidoDAO();
    static ProductoService productoService = new ProductoService();
    static UsuarioService usuarioService = new UsuarioService();
    static MenuService menuService = new MenuService();

    static Usuario usuarioLogueado;

    public static void main(String[] args) {
        int opcion;

        do {

            System.out.println("\n=== FOOD STORE ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1 -> {

                    login();

                    if (usuarioLogueado.getRol() == Rol.ADMIN) {
                        menuAdmin();
                    } else {
                        menuCliente();
                    }
                }

                case 2 -> registrarUsuario();

                case 0 -> System.out.println("👋 Hasta luego");

                default -> System.out.println("Opción inválida");
                case 3 -> {

                    pedidoDAO.listarPorUsuario(
                            usuarioLogueado.getId()
                    );
                }
            }

        } while (opcion != 0);
    }


    // ================= LOGIN =================
    public static void login() {

        System.out.println("=== LOGIN ===");

        System.out.print("Mail: ");
        String mail = sc.nextLine();

        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        usuarioLogueado = usuarioDAO.login(mail, pass);

        if (usuarioLogueado == null) {
            System.out.println("Login incorrecto");
            System.exit(0);
        }

        System.out.println("BIENVENIDO: " + usuarioLogueado.getNombre()
                + " - Rol: " + usuarioLogueado.getRol());
    }

    public static void registrarUsuario() {

        Usuario u = new Usuario();

        System.out.println("\n=== REGISTRO ===");

        System.out.print("Nombre: ");
        u.setNombre(sc.nextLine());

        System.out.print("Apellido: ");
        u.setApellido(sc.nextLine());

        System.out.print("Mail: ");
        u.setMail(sc.nextLine());

        System.out.print("Celular: ");
        u.setCelular(sc.nextLine());

        System.out.print("Contraseña: ");
        u.setContrasenia(sc.nextLine());

        u.setRol(Rol.USUARIO);

        usuarioService.registrarUsuario(u);

        System.out.println("USUARIO REGISTRADO CORRECTAMENTE");
    }

    // ================= MENU ADMIN =================
    public static void menuAdmin() {

        int op;

        do {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Ver productos");
            System.out.println("2. Crear producto");
            System.out.println("3. Ver pedidos");
            System.out.println("4. Ver usuarios");
            System.out.println("0. Salir");

            op = Integer.parseInt(sc.nextLine());

            switch (op) {

                case 1 -> {
                    menuService.mostrarProductos(
                            productoDAO.listar()
                    );
                }

                case 2 -> {
                    Producto p = new Producto();

                    System.out.print("Nombre: ");
                    p.setNombre(sc.nextLine());

                    System.out.print("Precio: ");
                    p.setPrecio(Double.parseDouble(sc.nextLine()));

                    System.out.print("Stock: ");
                    p.setStock(Integer.parseInt(sc.nextLine()));

                    System.out.print("Descripcion: ");
                    p.setDescripcion(sc.nextLine());

                    System.out.print("Imagen: ");
                    p.setImagen(sc.nextLine());

                    p.setDisponible(true);

                    Categoria c = new Categoria();
                    c.setId(1L); // simplificado

                    p.setCategoria(c);

                    productoDAO.insertar(p);
                }

                case 3 -> {

                    pedidoDAO.listarPedidos();

                    System.out.println("\n ¿Querés ver el detalle de un pedido? (si/no)");
                    String resp = sc.nextLine();

                    if (resp.equalsIgnoreCase("si")) {

                        System.out.print("ID pedido: ");
                        long id = Long.parseLong(sc.nextLine());

                        detalleDAO.listarPorPedido(id);
                    }
                }
                case 4 -> {

                    List<Usuario> usuarios =
                            usuarioDAO.listar();

                    for (Usuario u : usuarios) {

                        System.out.println(
                                u.getNombre()
                                        + " - "
                                        + u.getRol()
                        );
                    }
                }

            }

        } while (op != 0);
    }

    // ================= MENU CLIENTE =================
    public static void menuCliente() {

        int op;

        do {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Ver productos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Mis pedidos");
            System.out.println("0. Salir");

            op = Integer.parseInt(sc.nextLine());

            switch (op) {

                case 1 -> {
                    List<Producto> lista = productoDAO.listar();
                    for (Producto p : lista) {
                        System.out.println(p.getNombre() + " - $" + p.getPrecio());
                    }
                }

                case 2 -> {

                    // 1. crear pedido
                    Pedido pedido = new Pedido();
                    pedido.setUsuario(usuarioLogueado);
                    pedido.setFecha(LocalDate.now());
                    pedido.setEstado("PENDIENTE");
                    pedido.setTotal(0);

                    double totalPedido = 0;

                    String seguir = "no";

                    do {

                        System.out.println("\n=== PRODUCTOS DISPONIBLES ===");

                        menuService.mostrarProductos(
                                productoDAO.listar()
                        );

                        System.out.print("ID producto: ");
                        long idProd = Long.parseLong(sc.nextLine());

                        System.out.print("Cantidad: ");
                        int cant;

                        try {

                            cant =
                                    Integer.parseInt(
                                            sc.nextLine()
                                    );

                        } catch (NumberFormatException e) {

                            System.out.println(
                                    "Debe ingresar un número"
                            );

                            continue;
                        }

                        try {

                            totalPedido += pedidoService.agregarProducto(
                                    pedido,
                                    idProd,
                                    cant
                            );

                        } catch (StockInvalidoException e) {

                            System.out.println(e.getMessage());
                            continue;

                        } catch (exception.EntidadNoEncontradaException e) {

                            System.out.println(e.getMessage());
                            continue;
                        }

                        System.out.print("¿Agregar otro producto? (si/no): ");
                        seguir = sc.nextLine();

                    } while (seguir.equalsIgnoreCase("si"));

                    pedido.setTotal(totalPedido);

                    long idPedido =
                            pedidoService.crearPedido(pedido);

                    pedido.setId(idPedido);
                    for (DetallePedido d : pedido.getDetalles()) {

                        d.setPedido(pedido);

                        detalleDAO.insertar(d);
                    }
                    pedidoService.finalizarPedido(
                            pedido,
                            totalPedido
                    );

                    System.out.println("\n====================");
                    System.out.println("RESUMEN DEL PEDIDO");
                    System.out.println("====================");

                    for (DetallePedido d : pedido.getDetalles()) {

                        System.out.println(
                                d.getProducto().getNombre()
                                        + " x "
                                        + d.getCantidad()
                                        + " = $"
                                        + d.getSubtotal()
                        );
                    }

                    System.out.printf("\nTOTAL: $%.2f\n", totalPedido);
                }
                case 3 -> {

                    pedidoDAO.listarPorUsuario(
                            usuarioLogueado.getId()
                    );
                }

            }
        }while (op != 0) ;

    }
}


