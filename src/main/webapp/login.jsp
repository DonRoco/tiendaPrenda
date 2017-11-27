<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <title>Ingreso al Sistema de Ventas</title>
        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>

    <body>
        <div id="fullscreen_bg" class="fullscreen_bg"/>

        <div class="container">

            <%-- mensajes --%>
            <c:if test="${!empty mensajes}">
                <div class="alert alert-primary" role="alert">
                    <ul>
                        <c:forEach items="${mensajes}" var="mensaje">
                            <li>${mensaje}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>

            <%-- errores --%>
            <c:if test="${!empty errores}">
                <div class="alert alert-danger" role="alert">
                    <ul>
                        <c:forEach items="${errores}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            

            <form class="form-signin" method="post" action="login">
                <h2 class="form-signin-heading">Sistema de Ventas</h2>
                <label for="usuario" class="sr-only">Usuario</label>
                <input type="text" id="inputUsuario" name="inputUsuario" class="form-control" placeholder="Usuario" required autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Password" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Ingresar</button>
                <br />
                <jsp:include page="vendedor?op=include" />
                <c:if test="${empty vendedores}">
                    <a class="btn btn-warning" href="setup">Instalar</a>
                </c:if>
            </form>

        </div> <!-- /container -->

        <style type="text/css">
            body {
                padding-top: 120px;
                padding-bottom: 40px;
                background-color: #eee;

            }
            .btn 
            {
                outline:0;
                border:none;
                border-top:none;
                border-bottom:none;
                border-left:none;
                border-right:none;
                box-shadow:inset 2px -3px rgba(0,0,0,0.15);
            }
            .btn:focus
            {
                outline:0;
                -webkit-outline:0;
                -moz-outline:0;
            }
            .fullscreen_bg {
                position: fixed;
                top: 0;
                right: 0;
                bottom: 0;
                left: 0;
                background-size: cover;
                background-position: 50% 50%;
                background-image: url('http://cleancanvas.herokuapp.com/img/backgrounds/color-splash.jpg');
                background-repeat:repeat;
            }
            .form-signin {
                max-width: 280px;
                padding: 15px;
                margin: 0 auto;
                margin-top:50px;
            }
            .form-signin .form-signin-heading, .form-signin {
                margin-bottom: 10px;
            }
            .form-signin .form-control {
                position: relative;
                font-size: 16px;
                height: auto;
                padding: 10px;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                box-sizing: border-box;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="text"] {
                margin-bottom: -1px;
                border-bottom-left-radius: 0;
                border-bottom-right-radius: 0;
                border-top-style: solid;
                border-right-style: solid;
                border-bottom-style: none;
                border-left-style: solid;
                border-color: #000;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
                border-top-style: none;
                border-right-style: solid;
                border-bottom-style: solid;
                border-left-style: solid;
                border-color: rgb(0,0,0);
                border-top:1px solid rgba(0,0,0,0.08);
            }
            .form-signin-heading {
                color: #fff;
                text-align: center;
                text-shadow: 0 2px 2px rgba(0,0,0,0.5);
            }
        </style>
    </body>
</html>

