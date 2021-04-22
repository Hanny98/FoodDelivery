/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import bean.Order;
import bean.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.JdbcUtility;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author USER
 */
public class RemoveDeliveredOrderServlet extends HttpServlet {
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
        
        jdbcUtility.prepareSQLStatementSelectAllOrder();
        jdbcUtility.prepareSQLStatementSelectAllUser();
    }
    
    public void destroy()
    {
        jdbcUtility.jdbcConClose();
    }
    String getElapsedTime(String strMysqlTimestamp) {
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss.S");
        DateTime mysqlDate = formatter.parseDateTime(strMysqlTimestamp).
                             withZone(DateTimeZone.forID("Asia/Kuala_Lumpur"));
        
        System.out.println(mysqlDate.toString());
        
        DateTime now = new DateTime();
        Period period = new Period(mysqlDate, now);
        
        int seconds = period.getSeconds();
        int minutes = period.getMinutes();
        int hours = period.getHours();
        int days = period.getDays();
        int weeks = period.getWeeks();
        int months = period.getMonths();
        int years = period.getYears();
        
        String elapsedTime = "";
        if (years != 0)
            if (years == 1)
                elapsedTime = years + " year ago";
            else
                elapsedTime = years + " years ago";
        else if (months != 0)
            if (months == 1)
                elapsedTime = months + " month ago";
            else
                elapsedTime = months + " months ago";
        else if (weeks != 0)
            if (weeks == 1)
                elapsedTime = weeks + " week ago";
            else
                elapsedTime = weeks + " weeks ago";
        else if (days != 0)
            if (days == 1)
                elapsedTime = days + " day ago";
            else
                elapsedTime = days + " days ago";
        else if (hours != 0)
            if (hours == 1)
                elapsedTime = hours + " hour ago";
            else
                elapsedTime = hours + " hours ago";
        else if (minutes != 0)
            if (minutes == 1)
                elapsedTime = minutes + " minute ago";
            else
                elapsedTime = minutes + " minutes ago";
        else if (seconds != 0)
            if (seconds == 1)
                elapsedTime = seconds + " second ago";
            else
                elapsedTime = seconds + " seconds ago";   
        
        return elapsedTime;
    } 
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           HttpSession session = request.getSession();
           
           
           ArrayList<User> userList = new ArrayList<User>();
           ArrayList<Order> orderList = new ArrayList<Order>();
           
           ArrayList<Order> remainOrderList = new ArrayList<Order>();
           ArrayList<String> remainElapsedTimeList = new ArrayList<String>();
           ArrayList<String> remainRecipentList = new ArrayList<String>();
           

            PreparedStatement ps = jdbcUtility.getPsSelectAllOrder();
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                Order order = new Order();
                order.setOrderID(rs.getString("orderID"));
                order.setUserID(rs.getInt("userID"));
                order.setDishCodeList(rs.getString("dishCodeList"));
                order.setQuantityList(rs.getString("quantity"));
                order.setStatus(rs.getString("status"));
                order.setLastModifiedDate(rs.getString("lastModifiedDate"));
                order.setTotalPrice(rs.getDouble("totalPrice"));
                order.setDeliveryAddress(rs.getString("deliveryAddress"));
                
                orderList.add(order);
                
            }
            
            
            for (int i=0; i<orderList.size(); i++){
                
               //String status = orderList.get(i).getStatus() ;
               if(!orderList.get(i).getStatus().equals("Delivered")){
                   remainOrderList.add(orderList.get(i));
                   remainElapsedTimeList.add(getElapsedTime(orderList.get(i).getLastModifiedDate()));
               }
           }
            
           
            
            
            ps = jdbcUtility.getPsSelectAllUser();
            
            rs = ps.executeQuery();
            
            while(rs.next())
            {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));
                user.setUserType(rs.getString("usertype"));
                user.setFullname(rs.getString("fullname"));
                user.setAddress(rs.getString("address"));
                user.setPhoto(rs.getString("photo"));
                
                userList.add(user);
            }
          
           
           
           for(int i=0; i<remainOrderList.size(); i++)
            {
                for(int j=0; j<userList.size(); j++)
                {
                    if(remainOrderList.get(i).getUserID() == userList.get(j).getId())
                    {
                        remainRecipentList.add(userList.get(j).getFullname());
                        break;
                    }
                }
            }
           session.setAttribute("remainOrderList", remainOrderList);
           session.setAttribute("remainRecipentList", remainRecipentList);
           session.setAttribute("remainElapsedTimeList", remainElapsedTimeList);
           
           response.sendRedirect(request.getContextPath() + "/admin/viewRemainOrder.jsp");
           
        } catch (SQLException ex) {
            Logger.getLogger(RemoveDeliveredOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
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
