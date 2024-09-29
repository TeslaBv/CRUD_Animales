/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import configuration.ConnectionBD;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Animales;

/**
 *
 * @author CruzF
 */
@WebServlet("/animal")
public class Animal extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ConnectionBD conexion = new ConnectionBD();
    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Animal</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Animal at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPut");
        ConnectionBD conexion = new ConnectionBD();
        StringBuilder sb = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String json = sb.toString();
        Gson gson = new Gson();
        Animales animal = gson.fromJson(json, Animales.class);

        String sql = "UPDATE animales SET color = ?, especie = ?, tipo_animal = ?, tipo_alimento = ?, peso = ?, habitat = ?, altura = ? WHERE id = ?";
        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, animal.getColor());
            ps.setString(2, animal.getEspecie());
            ps.setString(3, animal.getTipo_animal());
            ps.setString(4, animal.getTipo_alimento());
            ps.setDouble(5, animal.getPeso());
            ps.setString(6, animal.getHabitat());
            ps.setString(7, animal.getAltura());
            ps.setInt(8, animal.getId());

            int filasActualizadas = ps.executeUpdate();
            response.setContentType("text/plain");
            if (filasActualizadas > 0) {
                response.getWriter().write("Animal actualizado exitosamente.");
            } else {
                response.getWriter().write("No se encontró el animal para actualizar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error al actualizar el animal.");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost");
        //Obtener los parametros del formulario
        String color = request.getParameter("txt_color");
        String especie = request.getParameter("txt_especie");
        String tipo_animal = request.getParameter("txt_tipo_animal");
        String tipo_alimento = request.getParameter("txt_tipo_alimento");
        String pesoS = request.getParameter("txt_peso");
        String habitat = request.getParameter("txt_habitat");
        String altura = request.getParameter("txt_altura");
        conn = conexion.getConnectionBD();
        
        //Conversion de String -> Double
        Double peso = Double.parseDouble(pesoS);

        try {
            //Creacion de la consulta SQL para la insercion del usuario
            String sql = "INSERT INTO animales(color, especie, tipo_animal, tipo_alimento, peso, habitat, altura)"
                    + "VALUES (?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, color);
            ps.setString(2, especie);
            ps.setString(3, tipo_animal);
            ps.setString(4, tipo_alimento);
            ps.setDouble(5, peso);
            ps.setString(6, habitat);
            ps.setString(7, altura);

            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas > 0) {
                //Insercion exitosa
                request.setAttribute("mensaje", "Animal registrado con exito");
                request.getRequestDispatcher("/views/showAnimal.jsp").forward(request, response);
            } else {
                //Insercion fallida
                request.setAttribute("mensaje", "Error al registrar Animal");
                request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrio un error: " + e.getMessage());
            request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doDelete");
        ConnectionBD conexion = new ConnectionBD();
        String id = request.getParameter("id");
        // Validate input
        if (id == null || id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Invalid request
            return;
        }

        String sql = "DELETE FROM animales WHERE id like ?";

        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK); // Eliminar exitoso 
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // No se encontró el usuario 
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Error del servidor 
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("doGet");
        conn = conexion.getConnectionBD();
        List<Animales> listaAnimales = new ArrayList<>();
        String sql = "SELECT id, color, especie, tipo_animal, tipo_alimento, peso, habitat, altura FROM animales";
        try {
            System.out.println("Empieza el trycatch");

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //Iteracion sobre los resultados
            while (rs.next()) {
                Animales aninmal = new Animales();
                aninmal.setId(rs.getInt("id"));
                aninmal.setColor(rs.getString("color"));
                aninmal.setEspecie(rs.getString("especie"));
                aninmal.setTipo_animal(rs.getString("tipo_animal"));
                aninmal.setTipo_alimento(rs.getString("tipo_alimento"));
                aninmal.setPeso(rs.getDouble("peso"));
                aninmal.setHabitat(rs.getString("habitat"));
                aninmal.setAltura(rs.getString("altura"));
                listaAnimales.add(aninmal);
            }
            System.out.println("Número de animales obtenidos: " + listaAnimales.size());

            System.out.println("Obtencion datos");
            //Muestreo de datos en el JSP
            request.setAttribute("animales", listaAnimales);
            request.getRequestDispatcher("/views/showAnimal.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los animales xd");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
