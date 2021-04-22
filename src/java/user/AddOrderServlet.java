/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bean.Dish;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 *
 * @author User
 */
@WebServlet(name = "AddOrderServlet", urlPatterns = {"/AddOrderServlet"})
public class AddOrderServlet extends HttpServlet {

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
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        int dishIndex = Integer.parseInt(request.getParameter("dishOrder"));
        String quantity = request.getParameter("quantity");
        ArrayList<Dish> activeDishList = (ArrayList<Dish>)session.getAttribute("activeDishList");
        
        ArrayList<Dish> dishOrder = (ArrayList<Dish>)session.getAttribute("dishOrder");
        ArrayList<String> quantityList; 
        
        if(dishOrder == null)
        {
            dishOrder = new ArrayList<Dish>();
            quantityList = new ArrayList<String>();
           
            dishOrder.add(activeDishList.get(dishIndex));
            quantityList.add(quantity);
        }
        else
        {
            quantityList = (ArrayList<String>)session.getAttribute("quantityList");
            boolean found = false;
            
            for(int i = 0; i<dishOrder.size() ; i++)
            {
                if(dishOrder.get(i).getId() == activeDishList.get(dishIndex).getId())
                {
                    Integer newQuantity = Integer.parseInt(quantityList.get(i)) + Integer.parseInt(quantity);
                    quantityList.set(i, newQuantity.toString());
                    found = true;
                    break;
                }
                else
                {
                    continue;
                }
            }
            
            if(!found)
            {
                dishOrder.add(activeDishList.get(dishIndex));
                quantityList.add(quantity);
            }
        }
        
        session.setAttribute("dishOrder", dishOrder);
        session.setAttribute("quantityList", quantityList);
        
        out.println("<script>");
            out.println("alert('Successfully added to cart');");
            out.println("window.location = '/FoodDelivery/SelectActiveDishServlet'");
        out.println("</script>");
        
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
