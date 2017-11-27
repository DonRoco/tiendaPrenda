package controller;

import entity.Cliente;
import entity.LineaPedido;
import entity.Pedido;
import entity.Prenda;
import entity.Vendedor;
import exception.PrendaNoEncontradaException;
import service.ClienteService;
import service.PedidoBuilder;
import service.PedidoService;
import service.PrendaService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CarritoServlet", urlPatterns = {"/carrito"})
public class CarritoServlet extends HttpServlet {

    @EJB
    PedidoBuilder pedidoBuilder;
    @EJB
    PedidoService pedidoService;
    @EJB
    ClienteService clienteService;
    @EJB
    PrendaService prendaService;

    private final static String JSP_CARRITO = "/WEB-INF/jsp/carrito/carrito.jsp";
    private final static String JSP_COMPROBANTE = "/WEB-INF/jsp/carrito/comprobante.jsp";
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");

        HttpSession session = request.getSession();
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        Long prendaId = 0L;

        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        if ("quitar".equals(operacion)) {
            String stringPrendaId = request.getParameter("prendaId");
            try {
                prendaId = Long.parseLong(stringPrendaId);
                pedido.quitarPrenda(prendaId);
                mensaje = String.format("Se quitó correctamente el prenda con ID %s del carrito", prendaId);
                mensajes.add(mensaje);
                logger.log(Level.INFO, mensaje);
            } catch (NumberFormatException nfe) {
                error = "ID de prenda mal formateado, no se pudo quitar el prenda del carrito";
                logger.log(Level.SEVERE, error);
                errores.add(error);
            }
        }

        request.setAttribute("errores", errores);
        request.setAttribute("mensajes", mensajes);
        request.setAttribute("pedido", pedido);
        if ("comprar".equals(operacion)) {
            Vendedor vendedor = (Vendedor) session.getAttribute("vendedor");
            pedido.setVendedor(vendedor);
            Cliente cliente = buildCliente(request, response);
            if(cliente==null){
                mensajes.add("complete todos los campos");
                request.getRequestDispatcher(JSP_CARRITO).forward(request, response);
                return;
            }
            pedido.setCliente(cliente);
            pedido = pedidoService.crearPedido(pedido);
            request.setAttribute("pedido", pedido);
            request.getRequestDispatcher(JSP_COMPROBANTE).forward(request, response);
        } else {
            request.getRequestDispatcher(JSP_CARRITO).forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringCantidad = request.getParameter("cantidad");
        String stringPrendaId = request.getParameter("prendaId");
        Prenda prenda = null;
        int cantidad = 1;

        try {
            Long prendaId = Long.parseLong(stringPrendaId);
            prenda = prendaService.getPrendaById(prendaId);
            if (prenda == null) {
                throw new PrendaNoEncontradaException();
            }
        } catch (NumberFormatException nfe) {
            error = "Prenda ID mal formateado";
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (PrendaNoEncontradaException pnee) {
            error = "Prenda no encontrado";
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        try {
            cantidad = Integer.parseInt(stringCantidad);
        } catch (NumberFormatException nfe) {
            cantidad = 1;
        }

        HttpSession session = request.getSession();
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        if (pedido == null) {
            pedido = pedidoBuilder.agregarPrenda(prenda.getId(), cantidad).build();
        } else {
            pedido.getLineasPedido().add(new LineaPedido(prenda, cantidad));
        }
        session.setAttribute("pedido", pedido);

        request.setAttribute("pedido", pedido);
        request.setAttribute("mensajes", mensajes);
        request.setAttribute("errores", errores);
        request.getRequestDispatcher(JSP_CARRITO).forward(request, response);
    }

    private Cliente buildCliente(HttpServletRequest request, HttpServletResponse response) {
        String stringRut = request.getParameter("rut");
        String stringDv = request.getParameter("dv");
        String nombre = request.getParameter("nombre");
        String stringFechaNacimiento = request.getParameter("fechanacimiento");
        String direccion = request.getParameter("direccion");
        String comuna = request.getParameter("comuna");

        // conversiones
        
        int rut = 0;
        Calendar fechaNacimiento = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            rut = Integer.parseInt(stringRut);
            Date fn = simpleDateFormat.parse(stringFechaNacimiento);
            fechaNacimiento.setTime(fn);
        } catch (NumberFormatException nfe) {
            logger.log(Level.SEVERE, "No se pudo convertir el string a RUT");
            return null;
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, "El forma de la fecha de nacimiento no coincide con el esperado yyyy-mm-dd");
            return null;
        }

        // creación de cliente
        Cliente cliente = clienteService.getClienteByRut(rut);
        if (cliente == null) {
            cliente = new Cliente();
            cliente.setRut(rut);
        }
        cliente.setDv(stringDv.charAt(0));
        cliente.setNombre(nombre);
        cliente.setFechaNacimiento(fechaNacimiento);
        cliente.setDireccion(direccion);
        cliente.setComuna(comuna);
        cliente = clienteService.crearCliente(cliente);
        return cliente;
    }

}
