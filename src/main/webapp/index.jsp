<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Bienvenido a la mejor tienda de ropa online</title>        
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        
        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />

            <p>
                Bienvenido a la plataforma de gestión de prendas
            </p>
            <div width="600px" style="margin: auto;">
            <img class="img-fluid" src="img/fotoInicio.jpg" alt="Sistema de Gestión de Prendas" aling="center" />
            </div>
        </div>

        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>