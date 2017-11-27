package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class LineaPedido implements Serializable {

    static final long serialVersionUID = 564654L;
    
    @ManyToOne
    Prenda prenda;
    @Column(nullable = false)
    int cantidad;
    @Column(nullable = false)
    Long precio;

    // Constructores
    public LineaPedido() {
    }

    public LineaPedido(Prenda prenda, int cantidad) {
        this.prenda = prenda;
        this.cantidad = cantidad;
        this.precio = prenda.getPrecio();
    }
    
    public LineaPedido(Prenda prenda, int cantidad, Long precio) {
        this.prenda = prenda;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    
    // Cálculos
    public Long getSubtotal() {
        return cantidad * precio;
    }
    
    
    // getters y setters
    public Prenda getPrenda() {
        return prenda;
    }

    public void setPrenda(Prenda prenda) {
        this.prenda = prenda;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

}
