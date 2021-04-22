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
import javax.servlet.http.HttpSession;
import bean.Dish;
import bean.User;
import java.util.ArrayList;
import jdbc.JdbcUtility;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import org.apache.commons.lang3.RandomStringUtils;
/**
 *
 * @author User
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {
    
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
        
        jdbcUtility.prepareSQLStatementInsertOrder();
    }
    
    public void destroy()
    {
        jdbcUtility.jdbcConClose();
    }
    
    public String combineDishCode(ArrayList<Dish> dishOrder)
    {
        String dishCodeList = "";
        for(int i=0; i<dishOrder.size(); i++)
        {
            dishCodeList += dishOrder.get(i).getDishCode() + "/";
        }
        
        return dishCodeList;
    }
    
    public String combineQuantity(ArrayList<String> quantityList)
    {
        String quantity = "";
        for(int i=0; i<quantityList.size(); i++)
        {
            quantity += quantityList.get(i) + "/";
        }
        
        return quantity;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        try
        {
            User user = (User)session.getAttribute("user");
            ArrayList<Dish> dishOrder = (ArrayList<Dish>)session.getAttribute("dishOrder");
            ArrayList<String> quantityList = (ArrayList<String>)session.getAttribute("quantityList");

            double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
            String deliveryAddress = request.getParameter("deliveryAddress");
            
            int length = 9;
            boolean useNumbers = true;
            boolean useLetters = true;
            String orderID = ("O" + RandomStringUtils.random(length, useLetters, useNumbers)).toUpperCase();
            
            
            PreparedStatement ps = jdbcUtility.getPsInsertOrder();
            ps.setString(1, orderID);
            ps.setInt(2, user.getId());
            ps.setString(3, combineDishCode(dishOrder));
            ps.setString(4, combineQuantity(quantityList));
            ps.setDouble(5, totalPrice);
            ps.setString(6, deliveryAddress);
            
            ps.executeUpdate();
            
            request.setAttribute("checkoutOrderID", orderID);
            request.setAttribute("checkoutTotalPrice", totalPrice);
            request.setAttribute("checkoutDishOrder", dishOrder);
            request.setAttribute("checkoutQuantityList", quantityList);
            
            session.removeAttribute("dishOrder");
            session.removeAttribute("quantityList");
            
            sendPage(request, response, "/checkoutStatus.jsp");
            

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            out.println("<script>");
                out.println("alert('Fail to checkout')");
                out.println("window.location = '/FoodDelivery/cart.jsp'");
            out.println("</script>");
        }
    }
    
    void sendPage(HttpServletRequest req, HttpServletResponse res, String fileName) throws ServletException, IOException
    {
        // Get the dispatcher; it gets the main page to the user
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(fileName);

	if (dispatcher == null)
	{
            System.out.println("There was no dispatcher");
	    // No dispatcher means the html file could not be found.
	    res.sendError(res.SC_NO_CONTENT);
	}
	else
	    dispatcher.forward(req, res);
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
