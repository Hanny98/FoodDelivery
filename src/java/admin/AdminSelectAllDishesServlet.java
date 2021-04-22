/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import bean.Dish;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.JdbcUtility;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Formatter;
/**
 *
 * @author User
 */
@WebServlet(name = "AdminSelectAllDishesServlet", urlPatterns = {"/AdminSelectAllDishesServlet"})
public class AdminSelectAllDishesServlet extends HttpServlet {
    
    private JdbcUtility jdbcUtility;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void init() throws ServletException
    {
        String driver = "com.mysql.jdbc.Driver";
        String dbName = "fooddelivery";
        String url = "jdbc:mysql://localhost/" + dbName + "?";
        String username = "root";
        String password = "";
        
        jdbcUtility = new JdbcUtility(driver, url, username, password);
        
        jdbcUtility.jdbcConnect();
        
        jdbcUtility.prepareSQLStatementSelectAllDishes();
        
    }
    
    public void destroy()
    {
        jdbcUtility.jdbcConClose();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        ArrayList<Dish> dishList = new ArrayList<Dish>();
        
        
        try
        {
            PreparedStatement ps = jdbcUtility.getPsSelectAllDishes();
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setDishCode(rs.getString("dishcode"));
                dish.setDishName(rs.getString("dishname"));
                dish.setPrice(rs.getDouble("price"));
                dish.setPhoto(rs.getString("photo"));
                dish.setStatus(rs.getString("status"));
                dish.setType(rs.getString("type"));
                dish.setDescription(rs.getString("description"));
                
                dishList.add(dish);
            }
            
            DecimalFormat df = new DecimalFormat("000");
            String nextCode = "D" + df.format(dishList.get((dishList.size()-1)).getId()+1);

            HttpSession session = request.getSession(true);
            session.setAttribute("dishList", dishList);
            session.setAttribute("nextCode", nextCode);

            response.sendRedirect(request.getContextPath() + "/admin/manageDish.jsp");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
