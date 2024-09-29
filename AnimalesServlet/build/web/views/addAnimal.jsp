<%-- 
    Document   : addAnimal.jsp
    Created on : 23/09/2024, 09:16:53 PM
    Author     : CruzF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de Animales</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Agregar Animal</h1>
        <!-- Formulario con Bootstrap -->
        <form action="${pageContext.request.contextPath}/animal" method="POST" class="border p-4 shadow-sm rounded">
            <div class="form-group">
                <label for="txt_color">Color</label>
                <input type="text" class="form-control" id="txt_color" name="txt_color" required>
            </div>
            <div class="form-group">
                <label for="txt_especie">Especie</label>
                <input type="text" class="form-control" id="txt_especie" name="txt_especie" required>
            </div>
            <div class="form-group">
                <label for="txt_tipo_animal">Tipo de Animal</label>
                <input type="text" class="form-control" id="txt_tipo_animal" name="txt_tipo_animal" required>
            </div>
            <div class="form-group">
                <label for="txt_tipo_alimento">Tipo de Alimento</label>
                <input type="text" class="form-control" id="txt_tipo_alimento" name="txt_tipo_alimento" required>
            </div>
            <div class="form-group">
                <label for="txt_peso">Peso</label>
                <input type="number" class="form-control" id="txt_peso" name="txt_peso" required>
            </div>
            <div class="form-group">
                <label for="txt_habitat">Hábitat</label>
                <input type="text" class="form-control" id="txt_habitat" name="txt_habitat" required>
            </div>
            <div class="form-group">
                <label for="txt_altura">Altura</label>
                <input type="text" class="form-control" id="txt_altura" name="txt_altura" required>
            </div>
            <button type="submit" class="btn btn-primary">Agregar</button>
        </form>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
