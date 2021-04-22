/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import jdbc.JdbcUtility;
import bean.User;
import org.apache.commons.codec.digest.DigestUtils;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(name = "AdminLoginServlet", urlPatterns = {"/AdminLoginServlet"})
public class AdminLoginServlet extends HttpServlet {
    
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
        
        jdbcUtility.prepareSQLStatementSelectAdmin();
    }
    
    public void destroy()
    {
        jdbcUtility.jdbcConClose();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        try
        {
            PreparedStatement ps = jdbcUtility.getPsSelectAdmin();
            User admin = null;
            
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                admin = new User();
                admin.setId(rs.getInt("id"));
                admin.setLogin(rs.getString("login"));
                admin.setPassword(rs.getString("password"));
                admin.setSalt(rs.getString("salt"));
                admin.setUserType(rs.getString("usertype"));
                admin.setFullname(rs.getString("fullname"));
                admin.setPhoto(rs.getString("photo"));
                admin.setAddress("address");
            }
            
            if(admin != null)
            {
                //check password
                String generatedPassword = DigestUtils.sha512Hex(password + admin.getSalt());
                if(generatedPassword.equals(admin.getPassword()))
                {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("admin", admin);
                    
                    response.sendRedirect(request.getContextPath() + "/admin/home.jsp");
                }
                else
                {
                    response.sendRedirect(request.getContextPath() + "/admin/not-exist.html");
                }
            }
            else
            {
                
                response.sendRedirect(request.getContextPath() + "/admin/not-exist.html");
                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/not-exist.html");
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
