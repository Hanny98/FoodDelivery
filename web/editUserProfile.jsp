<%-- 
    Document   : editUserProfile
    Created on : Jan 14, 2021, 9:19:43 PM
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
        <title>User Profile - Edit Profile</title>
        
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
                  <li class="nav-item">
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
                  User - Edit User Profile <br> 
                  This is a simple Food Delivery System 
               </p>
            </div>
         </div>         

         <!-- content start -->
         <div class="row">
            <div class="col-md-6">

               <div class="card shadow">
                  <div class="card-body">     

                     <h2>Edit User Profile</h2>
                     <form action="/FoodDelivery/UpdateUserByIdServlet" method="POST">
                            <div class="form-group">
                                <label for="login">Login</label>
                                <input type="text" name="login" id="login" class="form-control" required="" value="${sessionScope.user.login}" readonly="">    
                            </div>
                            <div class="form-group">
                                <label for="newpassword">New Password</label>
                                <input type="password" name="newpassword" id="newpassword" class="form-control">    
                            </div>
                            <div class="form-group">
                                <label for="fullname">Fullname</label>
                                <input type="text" name="fullname" id="fullname" class="form-control" required="" value="${sessionScope.user.fullname}">    
                            </div>
                            <div class="form-group">
                                <label for="address">Address</label>
                                <input type="text" name="address" id="address" class="form-control" required="" value="${sessionScope.user.address}">    
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
                     
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