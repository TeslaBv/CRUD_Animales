<%-- 
    Document   : resultado
    Created on : 25/09/2024, 12:44:03 AM
    Author     : CruzF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado de la accion</title>
    </head>
    <body>
        <h2>Hello World!</h2>
        <p><%= request.getAttribute("mensaje")%></p>
        <a href="inputForm.jsp">Regresar al formulario</a>
    </body>
</html>
