<%@page contentType="text/html" pageEncoding="UTF-8"%>
<b>
    <body background="img/fondo.jpg">
</b>
<a id="logo" href="index.jsp">
    <img src="img/logo.jpg" alt="logotipo" style="width: 200px; height: auto;" />
</a>
<br /><br />

<div id="cerrar_session" class="text-right"><dt>Bienvenido ${vendedor.nombre}</dt><a href="login?op=logout">Cerrar sesión</a></div>


<nav class="navbar navbar-expand-lg navbar-dark" style="background-color:brown ">

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/index.jsp'?'active ':''}nav-link" href="index.jsp">Inicio</a>
            </li>
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/carrito/carrito.jsp'?'active ':''}nav-link" href="carrito">Carrito</a>
            </li>            
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/prenda/listar.jsp'?'active ':''}nav-link" href="catalogo">Prendas</a>
            </li>            
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/categoria/listar.jsp'?'active ':''}nav-link" href="categorias">Categorías</a>
            </li>            
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/pedido/listar.jsp'?'active ':''}nav-link" href="pedidos">Pedidos</a>
            </li>
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/cliente/listar.jsp'?'active ':''}nav-link" href="clientes">Clientes</a>
            </li>            
        </ul>


        <form action="catalogo" method="get" class="form-inline my-2 my-lg-0">
            <input type="hidden" name="op" value="buscar" />
            <input name="prenda" class="form-control mr-sm-2" type="text" placeholder="Buscar" aria-label="Buscar">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Buscar</button>
        </form>
    </div>
</nav>
<br />