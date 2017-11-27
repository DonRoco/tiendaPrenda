package service;

import entity.Cliente;
import entity.LineaPedido;
import entity.Pedido;
import entity.Prenda;
import entity.Vendedor;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PedidoBuilder implements Serializable {

    static final long serialVersionUID = 52L;
    
    Pedido pedido;
    
    @EJB
    PrendaService prendaService;
    @EJB
    ClienteService clienteService;
    @EJB
    VendedorService vendedorService;
    
    public PedidoBuilder() {
        pedido = new Pedido();
    }
    
    public PedidoBuilder setCliente(Long clienteId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        pedido.setCliente(cliente);
        return this;
    }
    
    public PedidoBuilder setVendedor(Long vendedorId) {
        Vendedor vendedor = vendedorService.getVendedoryById(vendedorId);
        pedido.setVendedor(vendedor);
        return this;
    }
    
    public PedidoBuilder agregarPrenda(Long prendaId, int cantidad) {
        Prenda prenda = prendaService.getPrendaById(prendaId);
        LineaPedido lineaPedido = new LineaPedido(prenda, cantidad, prenda.getPrecio());
        pedido.getLineasPedido().add(lineaPedido);
        return this;
    }

    public Pedido build() {
        Pedido p = pedido;
        pedido = new Pedido();
        return p;
    }

    
    
    
}
