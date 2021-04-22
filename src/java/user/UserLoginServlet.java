/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import bean.User;
import javax.servlet.http.HttpSession;
import jdbc.JdbcUtility;
import org.apache.commons.codec.digest.DigestUtils;
/**
 *
 * @author User
 */
@WebServlet(name = "UserLoginServlet", urlPatterns = {"/UserLoginServlet"})
public class UserLoginServlet extends HttpServlet {

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
        
        jdbcUtility.prepareSQLStatementSelectUserByLogin();
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
            User user = null;
            PreparedStatement ps = jdbcUtility.getPsSelectUserByLogin();
            
            ps.setString(1, login);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
                user.setFullname(rs.getString("fullname"));
                user.setAddress(rs.getString("address"));
                user.setPhoto(rs.getString("photo"));
                user.setUserType(rs.getString("usertype"));
            }
            if(user != null)
            {
                String generatedPassword = DigestUtils.sha512Hex(password + user.getSalt());
                
                if(generatedPassword.equals(user.getPassword()))
                {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user); 
                    
                    response.sendRedirect(request.getContextPath() + "/home.jsp");
                }
                else
                {
                   response.sendRedirect(request.getContextPath() + "/not-exist.html");
                }
            }
            else
            {
                response.sendRedirect(request.getContextPath() + "/not-exist.html");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/not-exist.html");
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
