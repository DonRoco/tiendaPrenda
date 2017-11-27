package controller;

import entity.Categoria;
import entity.Prenda;
import exception.CategoriaNoEncontradaException;
import exception.PrendaNoEncontradaException;
import service.CategoriaService;
import service.PrendaService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PrendaServlet", urlPatterns = {"/catalogo"})
public class PrendaServlet extends HttpServlet {

    @EJB
    PrendaService prendaService;
    @EJB
    CategoriaService categoriaService;

    private final String JSP_LISTA_PRODUCTOS = "/WEB-INF/jsp/prenda/listar.jsp";
    private final String JSP_CREAR = "/WEB-INF/jsp/prenda/crear.jsp";
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";
        switch (operacion) {
            case "crear":
                request.setAttribute("categorias", categoriaService.getCategorias());
                request.getRequestDispatcher(JSP_CREAR).forward(request, response);
                break;
            case "buscar":
                buscar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response, List<Prenda> prendas) throws ServletException, IOException {
        List<Categoria> categorias = categoriaService.getCategorias();

        request.setAttribute("prendas", prendas);
        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher(JSP_LISTA_PRODUCTOS).forward(request, response);
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Prenda> prendas = prendaService.getPrendas();
        listar(request, response, prendas);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringId = request.getParameter("id");
        Long prendaId = 0L;
        try {
            prendaId = Long.parseLong(stringId);
            prendaService.eliminarPrenda(prendaId);
            mensaje = String.format("Se ha eliminado correctamente el prenda con ID %s", prendaId);
            logger.log(Level.INFO, mensaje);
            request.setAttribute("mensajes", mensajes);
            mensajes.add(mensaje);
        } catch (NumberFormatException nfe) {
            error = String.format("Formato de ID inválido");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (PrendaNoEncontradaException ex) {
            error = String.format("No se pudo encontrar el prenda con el ID especificado");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        listar(request, response);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String prendaBuscado = request.getParameter("prenda");
        String stringCategoria = request.getParameter("categoria");
        Long categoriaId = null;
        try {
            if (stringCategoria != null) {
                categoriaId = Long.parseLong(stringCategoria);
            }
        } catch (NumberFormatException nfe) {
            logger.log(Level.INFO, "La Categoría ID entregada no corresponde a un ID válido");
        }
        List<Prenda> prendas = prendaService.buscarPrenda(prendaBuscado, categoriaId);
        listar(request, response, prendas);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";
        
        String nombre = request.getParameter("prenda");
        String stringCategoria = request.getParameter("categoria");
        String stringPrecio = request.getParameter("precio");
        String imagen = request.getParameter("imagen");
        String descripcion = request.getParameter("descripcion");
        Long precio = 0L;
        Categoria categoria = null;
        request.setAttribute("categorias", categoriaService.getCategorias());
        try {
            Long categoriaId = Long.parseLong(stringCategoria);
            categoria = categoriaService.getCategoriaById(categoriaId);
            if(categoria == null) throw new CategoriaNoEncontradaException("No se encontró la categoría asignada al prenda");
            precio = Long.parseLong(stringPrecio);
            Prenda prenda = new Prenda(nombre, imagen, descripcion, precio, categoria);
            prenda = prendaService.crearPrenda(prenda);
            mensaje = String.format("Prenda %s creada correctamente con ID %s", prenda.getNombre(), prenda.getId());
            mensajes.add(mensaje);
        } catch(NumberFormatException nfe) {
            errores.add("Formato numérico incompatible");
        } catch (CategoriaNoEncontradaException ex) {
            errores.add(ex.getMessage());
        }
        
        request.setAttribute("errores", errores);
        request.setAttribute("mensajes", mensajes);
        request.getRequestDispatcher(JSP_CREAR).forward(request, response);
    }

}
