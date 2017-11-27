package controller;

import entity.Categoria;
import entity.Cliente;
import entity.Pedido;
import entity.Prenda;
import entity.Vendedor;
import service.CategoriaService;
import service.ClienteService;
import service.PedidoBuilder;
import service.PedidoService;
import service.PrendaService;
import service.VendedorService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SetupServlet", urlPatterns = {"/setup"})
public class SetupServlet extends HttpServlet {

    @EJB
    CategoriaService categoriaService;

    @EJB
    PrendaService prendaService;
    
    @EJB
    VendedorService vendedorService;
    
    @EJB
    ClienteService clienteService;
    
    @EJB
    PedidoService pedidoService;
    
    @EJB
    PedidoBuilder pedidoBuilder;

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> mensajes = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        try {
            Vendedor vendedor = new Vendedor("Gonzalo Moreira", 13456987, 'k', "gmoreira", "1234");
            vendedorService.crearVendedor(vendedor);
            mensajes.add( String.format("Vendedor %s creado con ID %s", vendedor.getUsuario()+":"+vendedor.getContrasena(), vendedor.getId()) );
 
            Calendar fechaNacimiento = Calendar.getInstance();
            fechaNacimiento.add(Calendar.YEAR, -20);
            Cliente cliente = new Cliente("Cristobal Arevalo", 19643350, '3', "Parque San Francisco", "Puente Alto", fechaNacimiento);
            clienteService.crearCliente(cliente);
            mensajes.add(String.format("Cliente %s creado con ID %s", cliente.getNombre(), cliente.getId()));

            String imagen = "https://i2.linio.com/p/2a1fdca52918c8f0f6cde93f1b6c60af-catalog.jpg";
            String descripcion = "- Color azul marino\n"
                    + "- Material: algodón 100%\n"
                    + "- Género: Camisas ocasionales\n"
                    + "- Elementos populares: color sólido\n"
                    + "- Color: azul claro / azul oscuro\n"
                    + "- Tamaño: M / L / XL / 2XL\n"
                    + "- Ocasiones: en interiores y exteriores\n"
                    + "- Estación: primavera / verano / otoño\n"
                    + "- Peso: 0,25 kg";

            Categoria categoria1 = new Categoria("Deportes", "");
            categoria1= categoriaService.crearCategoria(categoria1);
            mensajes.add( String.format("Categoría %s creada con ID %s", categoria1.getNombre(), categoria1.getId()) );
            
            Categoria categoria2 = new Categoria("Casual", "");
            categoria2 = categoriaService.crearCategoria(categoria2);
            mensajes.add( String.format("Categoría %s creada con ID %s", categoria2.getNombre(), categoria2.getId()) );

            Prenda p = new Prenda("Camisa De Mezclilla De Tela Delgada Para Hombres", imagen, descripcion, 29990L, categoria2);
            p = prendaService.crearPrenda(p);
            Prenda camisa = p;
            mensajes.add( String.format("Prenda %s creado con ID %s", p.getNombre(), p.getId()) );

            descripcion = "- Color negro\n"
                    + "- Mezclas del algodón, poliéster\n"
                    + "- Tipo de la tela: Paño\n"
                    + "- Estilo: Activo\n"
                    + "- Longitud de la ropa: Regular\n"
                    + "- Cuello: cuello redondo\n"
                    + "- Grosor: Thin\n"
                    + "- Tipo de cierre: Triple de pecho\n"
                    + "- Peso: 0.166 kg\n"
                    + "- Contenido del paquete: 1 x Gimnasio Tanque\n";
            imagen = "http://cdn01.ovonni.com/uploads/2016/201605/20160513/source-img/1463076779024-P-147420.jpg";
            p = new Prenda("Musculosa Gym Tank", imagen, descripcion, 15000L, categoria1);
            p = prendaService.crearPrenda(p);
            Prenda musculosa = p;

            mensajes.add( String.format("Prenda %s creado con ID %s", p.getNombre(), p.getId()) );
            
            
            Pedido pedido = pedidoBuilder.setCliente(cliente.getId())
                        .setVendedor(vendedor.getId())
                        .agregarPrenda(camisa.getId(), 2)
                        .agregarPrenda(musculosa.getId(), 1)
                        .build();
            pedidoService.crearPedido(pedido);
            
        } catch (Exception e) {
            errores.add(e.getMessage());    
        }

        request.setAttribute("mensajes", mensajes);
        request.setAttribute("errores", errores);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

}
