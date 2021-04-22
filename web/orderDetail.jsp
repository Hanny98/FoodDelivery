<%-- 
    Document   : orderDetail
    Created on : Jan 16, 2021, 1:35:15 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="bean.User" %>
<%@page  import="bean.Order"%>
<%@page  import="bean.Dish"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
        <meta name="generator" content="Jekyll v4.1.1">
        <title>User - Order Details</title>
        
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
        <c:when test="${sessionScope.user == null}">
            <script>
                window.location = "/FoodDelivery/terminate.html";
            </script>
        </c:when>
    </c:choose>
            
    <jsp:useBean id="user" class="bean.User" scope="session" />
    <body>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
         <div class="container">
            <a class="navbar-brand" href="/FoodDelivery/home.jsp">Food Delivery</a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
               <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarCollapse">

               <ul class="navbar-nav mr-auto">
                   <li class="nav-item">
                    <a class="nav-link" href="/FoodDelivery/home.jsp">Home <span class="sr-only">(current)</span></a>
                  </li>
                  <li class="nav-item active">
                    <a class="nav-link" href="/FoodDelivery/SelectOrderByUserIDServlet">View Order</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="/FoodDelivery/SelectActiveDishServlet">Make Order</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="/FoodDelivery/cart.jsp">Cart</a>
                  </li>
               </ul>
                <img src="/FoodDelivery/img/<jsp:getProperty name="user" property="photo"/>" width="20" height="20"/>
                <ul class="navbar-nav"> 
                  <li class="nav-item dropdown">
                      <a class="nav-link dropdown-toggle" href="h#" id="dropdown07" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Welcome User
                          <jsp:getProperty name="user" property="fullname"/></a>
                    <div class="dropdown-menu" aria-labelledby="dropdown07">
                      <a class="dropdown-item" href="/FoodDelivery/userprofile.jsp"><img src="icons/person.svg" alt="" width="20" height="20" title="Userprofile">&nbsp User Profile</a>
                      <a class="dropdown-item" href="/FoodDelivery/UserLogoutServlet"><img src="icons/power.svg" alt="" width="20" height="20" title="Logout">&nbsp Logout</a>
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
                  User - View Order Details<br> 
                  This is a simple Food Delivery System 
               </p>
            </div>
         </div>         

         <!-- content start -->
         
         <div class="row">
            <div class="col-md-12">

               <div class="card shadow">
                  <div class="card-body">
                     
                      <h2>Order Details</h2>
                      <h3>Order ID &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  : <c:out value="${sessionScope.detailedOrder.orderID}"/></h3>
                      <h3>Order Status&nbsp : <c:out value="${sessionScope.detailedOrderStatus}"/></h3>
                      
                        <c:choose>
                            <c:when test="${detailedOrder.status.equals('Paid')}">
                                <div class="container" style="text-align:center;">
                                    <img src="img/paid.gif" class="img-fluid" alt="Image">
                                    <p> We've just received your order, hold on! </p>
                                </div>
                            </c:when>
                            <c:when test="${detailedOrder.status.equals('In Preparation')}">
                                 <div class="container" style="text-align:center; ">
                                    <img src="img/cooking.gif" class="img-fluid" alt="Image" style="padding-bottom: 20px;">
                                    <p> The kitchen is preparing your order right now. </p>
                                </div>
                            </c:when>
                            <c:when test="${detailedOrder.status.equals('Delivered')}">
                                 <div class="container" style="text-align:center;">
                                    <img src="img/delivered.gif" class="img-fluid" alt="Image" style="padding-bottom: 20px;">
                                    <p> Your order is delivered, thank you for dining with us! </p>
                                </div>
                            </c:when>
                        </c:choose>

                  </div>
               </div>
               
            </div>
               <div class="col-md-12">
                  <div class="card shadow">
                  <div class="card-body">
                     
                      
                      <div class="table-responsive"> 
                        <table class="table table-striped table-bordered table-hover">
                           <thead class="thead-dark">
                              <tr>
                                 <th>#</th>
                                 <th>Dish Code</th>
                                 <th>Dish Name</th>
                                 <th>Photo</th>
                                 <th>Unit Price</th>
                                 <th>Quantity</th>
                                 <th>Order Total</th>
                              </tr>
                           </thead>
                           <tbody>
                               <c:set var="totalPrice" value="${0}"/>
                                  <c:forEach items="${sessionScope.dishListOfDetailedOrder}" var="dish" varStatus="loop">
                                      <c:set var="dishTotalPrice" value="${dish.price * Integer.parseInt(sessionScope.detailedQuantityList[loop.index])}"/>
                                      <c:set var="totalPrice" value="${totalPrice + dishTotalPrice}"/>
                                      <tr>
                                          <td><c:out value="${loop.index + 1}"/></td>
                                          <td><c:out value="${dish.dishCode}"/></td>
                                          <td><c:out value="${dish.dishName}"/></td>
                                          <td><img src="/FoodDelivery/img/${dish.photo}" alt="${dish.photo}" width="50" height="50"></td>
                                          <td>RM <fmt:formatNumber value="${dish.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                          <td><c:out value="${sessionScope.detailedQuantityList[loop.index]}"/></td>
                                          <td>RM <fmt:formatNumber value="${dishTotalPrice}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                      </tr>
                                  </c:forEach>
                                      <tr>
                                          <td colspan="6" align="right">Total Price: RM</td>
                                          <td colspan="2"><fmt:formatNumber value="${totalPrice}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                      </tr>
                           </tbody>      
                        </table>                           
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
      
      <script src="js/jquery-3.5.1.min.js"></script>
      <script src="js/popper.min.js"></script>      
      <script src="js/bootstrap.min.js"></script>
      
    </body>
</html>
