<%-- 
    Document   : addAnimal.jsp
    Created on : 23/09/2024, 09:16:53 PM
    Author     : CruzF
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="configuration.ConnectionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar Animal</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4">Actualizar Animal</h2>
            <%
                String id = request.getParameter("id");
                String color = "";
                String especie = "";
                String tipoAnimal = "";
                String tipoAlimento = "";
                double peso = 0.0;
                String habitat = "";
                String altura = "";
                ConnectionBD conexion = new ConnectionBD();
                Connection connection = conexion.getConnectionBD();
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    // Consulta para obtener los datos del animal por ID 
                    String sql = "SELECT color, especie, tipo_animal, tipo_alimento, peso, habitat, altura FROM animales WHERE id = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, id);
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        color = resultSet.getString("color");
                        especie = resultSet.getString("especie");
                        tipoAnimal = resultSet.getString("tipo_animal");
                        tipoAlimento = resultSet.getString("tipo_alimento");
                        peso = resultSet.getDouble("peso");
                        habitat = resultSet.getString("habitat");
                        altura = resultSet.getString("altura");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                        if (statement != null) {
                            statement.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            %> 

            <form id="formActualizarAnimal" class="border p-4 shadow-sm rounded"> 
                <div class="form-group">
                    <label for="txt_color">Color:</label>
                    <input type="text" id="txt_color" class="form-control" value="<%= color %>">
                </div>
                <div class="form-group">
                    <label for="txt_especie">Especie:</label>
                    <input type="text" id="txt_especie" class="form-control" value="<%= especie %>">
                </div>
                <div class="form-group">
                    <label for="txt_tipo_animal">Tipo de Animal:</label>
                    <input type="text" id="txt_tipo_animal" class="form-control" value="<%= tipoAnimal %>">
                </div>
                <div class="form-group">
                    <label for="txt_tipo_alimento">Tipo de Alimento:</label>
                    <input type="text" id="txt_tipo_alimento" class="form-control" value="<%= tipoAlimento %>">
                </div>
                <div class="form-group">
                    <label for="txt_peso">Peso:</label>
                    <input type="number" id="txt_peso" class="form-control" value="<%= peso %>" step="0.01">
                </div>
                <div class="form-group">
                    <label for="txt_habitat">Habitat:</label>
                    <input type="text" id="txt_habitat" class="form-control" value="<%= habitat %>">
                </div>
                <div class="form-group">
                    <label for="txt_altura">Altura:</label>
                    <input type="text" id="txt_altura" class="form-control" value="<%= altura %>">
                </div>
                <button type="button" class="btn btn-primary" onclick="actualizarAnimal()">Actualizar</button> 
            </form> 
            <div id="resp" class="mt-3"></div> 
            <a href="${pageContext.request.contextPath}/animal" class="btn btn-link mt-3">Ver Animales</a>
        </div>

        <script>
            function actualizarAnimal() {
                const id = "<%= id %>";
                const color = document.getElementById("txt_color").value;
                const especie = document.getElementById("txt_especie").value;
                const tipo_animal = document.getElementById("txt_tipo_animal").value;
                const tipo_alimento = document.getElementById("txt_tipo_alimento").value;
                const peso = document.getElementById("txt_peso").value;
                const habitat = document.getElementById("txt_habitat").value;
                const altura = document.getElementById("txt_altura").value;
                
                const datos = {
                    color: color,
                    especie: especie,
                    tipo_animal: tipo_animal,
                    tipo_alimento: tipo_alimento,
                    peso: peso,
                    habitat: habitat,
                    altura: altura,
                    id: id
                };

                fetch(`${pageContext.request.contextPath}/animal`, {
                    method: "PUT",
                    body: JSON.stringify(datos),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("resp").innerText = data;
                        })
                        .catch(error => {
                            document.getElementById("resp").innerText = "Error en la actualizacion del animal.";
                            console.error('Error:', error);
                        });
            }
        </script> 

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
