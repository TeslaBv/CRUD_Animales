<%-- 
    Document   : showAnimal.jsp
    Created on : 23/09/2024, 09:17:41 PM
    Author     : CruzF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Animales"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lista de Animales</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            table {
                width: 80%;
                margin: 20px auto;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
        <script>
            function eliminarAnimal(id) {
                if (confirm("¿Estás seguro de que quieres eliminar este animal?")) {
                    fetch(`animal?id=` + id, {
                        method: 'DELETE'
                    }).then(response => {
                        console.log(response)
                        if (response.ok) {
                            alert('Animal eliminado exitosamente');
                            location.reload();
                        } else {
                            alert('Error al eliminar animal');
                        }
                    }).catch(error => console.error('Error:', error));
                }
            }
        </script>
    </head>
    <body>
        <div class="container mt-5">
            <h2>Lista de Animales</h2>
            <a href="views/addAnimal.jsp">Agregar Animal</a>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Color</th>
                        <th>Especie</th>
                        <th>Tipo de Animal</th>
                        <th>Tipo de Alimento</th>
                        <th>Peso</th>
                        <th>Hábitat</th>
                        <th>Altura</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ArrayList<Animales> listaAnimales = (ArrayList<Animales>) request.getAttribute("animales");

                        if (listaAnimales != null && !listaAnimales.isEmpty()) {
                            for (Animales animal : listaAnimales) {
                    %>
                    <tr>
                        <td><%= animal.getColor() %></td>
                        <td><%= animal.getEspecie() %></td>
                        <td><%= animal.getTipo_animal()%></td>
                        <td><%= animal.getTipo_alimento()%></td>
                        <td><%= animal.getPeso() %></td>
                        <td><%= animal.getHabitat() %></td>
                        <td><%= animal.getAltura() %></td>
                        <td>
                            <button onclick="eliminarAnimal(<%= animal.getId()%>)">Eliminar</button>
                            <!-- Botón para actualizar, que redirige a actualizarUsuario.jsp con el ID del usuario --> 
                            <form action="${pageContext.request.contextPath}/views/editAnimal.jsp" method="GET"> 
                                <input type="hidden" name="id" value="<%= animal.getId()%>"> 
                                <input type="submit" value="Actualizar"> 
                            </form> 
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="8">No hay animales registrados.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
