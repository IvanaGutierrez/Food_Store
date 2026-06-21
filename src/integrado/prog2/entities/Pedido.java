package integrado.prog2.entities;

import integrado.prog2.interfaces.Calculable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {

    private Usuario usuario;
    private LocalDate fecha;
    private String estado;
    private double total;

    private List<DetallePedido> detalles = new ArrayList<>();

    public Pedido() {
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    @Override
    public double calcularTotal() {

        double totalCalculado = 0;

        for (DetallePedido d : detalles) {
            totalCalculado += d.getSubtotal();
        }

        return totalCalculado;
    }


}
