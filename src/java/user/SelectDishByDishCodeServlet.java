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
import java.util.ArrayList;
import jdbc.JdbcUtility;
import bean.Dish;
import bean.Order;
import javax.servlet.http.HttpSession;
/**
 *
 * @author User
 */
@WebServlet(name = "SelectDishByDishCodeServlet", urlPatterns = {"/SelectDishByDishCodeServlet"})
public class SelectDishByDishCodeServlet extends HttpServlet {
    
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
        
        int index = Integer.parseInt(request.getParameter("index"));
        HttpSession session = request.getSession();
        ArrayList<Order> orderList = (ArrayList<Order>)session.getAttribute("orderList");
        Order detailedOrder = orderList.get(index);
        ArrayList<Dish> dishListOfDetailedOrder = new ArrayList<Dish>();
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
                
                dishList.add(dish);
            }
            
            String[] dishCodeList = orderList.get(index).getDishCodeList().split("/");
            String[] quantityList = orderList.get(index).getQuantityList().split("/");
            String statusOfOrder = orderList.get(index).getStatus();
            
            //addding dish into dishListOfDetailedOrder accroding to dishCode from order in database
            for(int i=0; i<dishCodeList.length; i++)
            {
                for(int j=0; j<dishList.size(); j++)
                {
                    if(dishList.get(j).getDishCode().equals(dishCodeList[i]))
                    {
                        dishListOfDetailedOrder.add(dishList.get(j));
                        break;
                    }
                }
            }
            
            session.setAttribute("detailedOrder", detailedOrder);
            session.setAttribute("detailedQuantityList", quantityList);
            session.setAttribute("dishListOfDetailedOrder", dishListOfDetailedOrder);
            session.setAttribute("detailedOrderStatus", statusOfOrder);
            
            response.sendRedirect(request.getContextPath() + "/orderDetail.jsp");
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home.jsp");
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
