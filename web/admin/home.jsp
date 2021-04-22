<%-- 
    Document   : home
    Created on : Jan 12, 2021, 8:14:10 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="bean.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
        <meta name="generator" content="Jekyll v4.1.1">
        <title>Admin Home</title>
        
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
            <a class="navbar-brand" href="#">Food Delivery</a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
               <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarCollapse">

               <ul class="navbar-nav mr-auto">
                   <li class="nav-item active">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="/FoodDelivery/AdminSelectAllDishesServlet">Manage Menu</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="/FoodDelivery/SelectAllOrderServlet">Manage Order</a>
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
                  Admin - Home <br> 
                  This is a simple Food Delivery System 
               </p>
            </div>
         </div>         

         <!-- content start -->
         
         
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
