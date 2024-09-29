/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Animales;
import utils.CustomResponse;

/**
 *
 * @author CruzF
 */

@WebServlet(urlPatterns = {"/api/data"})
public class ApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private Connection conn2;
    
    // Método para obtener la conexión a la base de datos (debes configurarlo según tu base de datos)
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        // Configura la conexión a tu base de datos
        String jdbcURL = "jdbc:mysql://localhost:3306/animalesdb";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request 
     * @param response 
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        //Configuracion del JSON que respondera la peticion
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        List<String> list = new ArrayList<>();
        
        List<Animales> listaAnimales = new ArrayList<>();
        String sql = "SELECT color, especie, tipo_animal, tipo_alimento, peso, habitat, altura FROM animales";
        try {
            conn2 = getConnection();
            PreparedStatement ps = conn2.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            //Iteracion sobre los resultados
            while(rs.next()){
                Animales aninmal = new Animales();
                aninmal.setColor(rs.getString("color"));
                aninmal.setEspecie(rs.getString("especie"));
                aninmal.setTipo_animal(rs.getString("tipo_animal"));
                aninmal.setTipo_alimento(rs.getString("tipo_alimento"));
                aninmal.setPeso(rs.getDouble("peso"));
                aninmal.setHabitat(rs.getString("habitat"));
                aninmal.setAltura(rs.getString("altura"));
                listaAnimales.add(aninmal);
            }
        
        } catch (Exception e) {
        }
        
        CustomResponse cResponse = new CustomResponse (200, "OK", list);
        
        String jsonResponse = new Gson().toJson(cResponse);
        response.getWriter().write(jsonResponse);
    }
}
