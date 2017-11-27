package cl.duoc.dej.tienda.entity;

import entity.Pedido;
import entity.LineaPedido;
import entity.Prenda;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PedidoTest {
    
    Pedido pedido;
    
    @Before
    public void setup() {
        pedido = new Pedido();
        List<LineaPedido> lineasPedido = new ArrayList<>();
        lineasPedido.add(new LineaPedido(new Prenda(1L), 1, 100L));
        lineasPedido.add(new LineaPedido(new Prenda(2L), 2, 50L));
        pedido.setLineasPedido(lineasPedido);
    }
    
    @Test
    public void totalTest() {
        assert pedido.getTotal() == 200L:"El cálculo del TOTAL no es el esperado";
        assert pedido.getLineasPedido().get(0).getSubtotal() == 100L:"El cálculo del subtotal no es el esperado";
        assert pedido.getLineasPedido().get(1).getSubtotal() == 100L:"El cálculo del subtotal no es el esperado";
        assert pedido.getLineasPedido().size() == 2:"Tienen que existir 2 prendas en las líneas de pedido";
        pedido.quitarPrenda(1L);
        assert pedido.getLineasPedido().size() == 1:"Tiene que existir 1 prenda en las líneas de pedido";
        assert pedido.getTotal() == 100L:"El cálculo del TOTAL no es el esperado";
        
        
    }
}
