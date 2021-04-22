<%-- 
    Document   : viewOrder
    Created on : Jan 16, 2021, 8:19:57 PM
    Author     : User
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="bean.User" %>
<%@page import="bean.Order" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
        <meta name="generator" content="Jekyll v4.1.1">
        <title>Admin - Manage Order</title>
        
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <style>
           .bd-placeholder-img {
             font-size: 1.125rem;
             text-anchor: middle;
             -webkit-user-select: none;
             -moz-user-select: none;
             -ms-user-select: none;
             user-select: none;
           }

           @media (min-width: 768px) {
             .bd-placeholder-img-lg {
               font-size: 3.5rem;
             }
           }
        </style>

        <!-- Custom styles for this template -->
        <link href="css/navbar-top-fixed.css" rel="stylesheet">
    </head>
    <c:choose>
        <c:when test="${sessionScope.admin == null}">
            <script>
                window.location = "/FoodDelivery/admin/terminate.html";
            </script>
        </c:when>
        <c:when test="${sessionScope.admin.userType != 'admin'}">
            <script>
                window.location = "/FoodDelivery/admin/nonauthorize.html";
            </script>
        </c:when>
    </c:choose>
    <jsp:useBean id="admin" class="bean.User" scope="session" />
    <body>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
         <div class="container">
            <a class="navbar-brand" href="/FoodDelivery/admin/home.jsp">Food Delivery</a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
               <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarCollapse">

               <ul class="navbar-nav mr-auto">
                   <li class="nav-item">
                    <a class="nav-link" href="/FoodDelivery/admin/home.jsp">Home <span class="sr-only">(current)</span></a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="/FoodDelivery/AdminSelectAllDishesServlet">Manage Menu</a>
                  </li>
                  <li class="nav-item active">
                    <a class="nav-link" href="#">Manage Order</a>
                  </li> 
               </ul>
                <img src="/FoodDelivery/img/<jsp:getProperty name="admin" property="photo"/>" width="20" height="20"/>
                <ul class="navbar-nav"> 
                  <li class="nav-item dropdown">
                      <a class="nav-link dropdown-toggle" href="h#" id="dropdown07" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Welcome
                          <jsp:getProperty name="admin" property="fullname"/></a>
                    <div class="dropdown-menu" aria-labelledby="dropdown07">
                      <a class="dropdown-item" href="/FoodDelivery/admin/userprofile.jsp"><img src="icons/person.svg" alt="" width="20" height="20" title="Userprofile">&nbsp User Profile</a>
                      <a class="dropdown-item" href="/FoodDelivery/AdminLogoutServlet"><img src="icons/power.svg" alt="" width="20" height="20" title="Logout">&nbsp Logout</a>
                    </div>
                    
                  </li>                  
               </ul>
            </div>
         </div>
        </nav>     

        <main role="main" class="container">
 

         <!-- Main jumbotron for a primary marketing message or call to action -->
         <div class="jumbotron">
            <div class="container">
               <h1 class="display-3">Food Delivery System</h1>
               <p>
                  Admin - View Order <br> 
                  This is a simple Food Delivery System 
               </p>
            </div>
         </div>         

         <!-- content start -->
         <c:set var="totalEarning" value="${0}"/>
         <c:set var="totalPaidOrder" value="${0}"/>
         <c:set var="totalInPreparationOrder" value="${0}"/>
         <c:set var="totalDeliveredOrder" value="${0}"/>
         <c:forEach items="${sessionScope.remainOrderList}" var="order" varStatus="loop">
             <c:set var="totalEarning"><fmt:formatNumber value="${totalEarning + order.totalPrice}" maxFractionDigits="2" minFractionDigits="2"/></c:set>
             <c:choose>
                 <c:when test="${order.status.equals('Paid')}">
                     <c:set var="totalPaidOrder" value="${totalPaidOrder + 1}"/>
                 </c:when>
                 <c:when test="${order.status.equals('In Preparation')}">
                     <c:set var="totalInPreparationOrder" value="${totalInPreparationOrder + 1}"/>
                 </c:when>
                 <c:when test="${order.status.equals('Delivered')}">
                     <c:set var="totalDeliveredOrder" value="${totalDeliveredOrder + 1}"/>
                 </c:when>
             </c:choose>
         </c:forEach>
         <div class="row">
             <div class="col-md-6">

               <div class="card shadow">
                  <div class="card-body">     

                     <h2>Order Information</h2>
                     <div class="alert alert-primary" role="alert">
                        Total Earning: RM <c:out value="${totalEarning}"/>
                     </div>
                     <div class="alert alert-secondary" role="alert">
                        Total Paid Order: <c:out value="${totalPaidOrder}"/>
                     </div>
                     <div class="alert alert-success" role="alert">
                       Total In Preparation Order: <c:out value="${totalInPreparationOrder}"/>
                     </div>
                     <div class="alert alert-danger" role="alert">
                       Total Delivered Order: <c:out value="${totalDeliveredOrder}"/>
                     </div>                      
                  </div>
               </div>
               
            </div>
         </div>
         
         <div class="row">
            <div class="col-md-12">

               <div class="card shadow">
                  <div class="card-body">
                     <h2>Order History/Status</h2>

                     <div class="table-responsive"> 
                        <table class="table table-striped table-bordered table-hover">
                           <thead class="thead-dark">
                              <tr>
                                 <th>#</th>
                                 <th>Order ID</th>
                                 <th>Recipient</th>
                                 <th>Deliver To</th>
                                 <th>Order Total</th>
                                 <th>Status</th>
                                 <th>Last Modified Time</th>
                              </tr>
                           </thead>
                           <tbody>
                               <c:forEach items="${sessionScope.remainOrderList}" var="order" varStatus="loop">
                                   <c:set var="totalPrice"><fmt:formatNumber value="${order.totalPrice}" maxFractionDigits="2" minFractionDigits="2"/></c:set>
                                   <tr>
                                        <td><c:out value="${loop.index + 1}"/></td>
                                        <td><a href="/FoodDelivery/AdminSelectDishByDishCodeServlet?index=${loop.index}"><c:out value="${order.orderID}"/></a></td>
                                        <td><c:out value="${sessionScope.remainRecipentList.get(loop.index)}"/></td>
                                        <td><c:out value="${order.deliveryAddress}"/></td>
                                        <td><c:out value="RM${totalPrice}"/></td>
                                        <c:choose>
                                            <c:when test="${order.status.equals('Delivered')}">
                                                <td><c:out value="${order.status}"/></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td><a href="/FoodDelivery/admin/updateOrderStatus.jsp?index=${loop.index}"><c:out value="${order.status}"/></a></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td><span class="badge badge-primary"><c:out value="${sessionScope.remainElapsedTimeList.get(loop.index)}"/></span></td>
                                   </tr>
                               </c:forEach>
                           
                           </tbody>
                        </table>   
                         <button type="submit" class="btn btn-primary" onclick="showAllOrder()">Show Delivered Dish</button>
                     </div> 

                  </div>
               </div>
               
            </div>
         </div>  
         <!-- content ends -->

      </main>

      <footer class="container">
         <br />
         <p>&copy; Company 2017-2020</p>
      </footer>
                     <script>
                         function showAllOrder(){
                             window.location="/FoodDelivery/SelectAllOrderServlet";
                             
                         }
                         </script>
      <script src="js/jquery-3.5.1.min.js"></script>
      <script src="js/popper.min.js"></script>      
      <script src="js/bootstrap.min.js"></script>
      
    </body>
</html>

