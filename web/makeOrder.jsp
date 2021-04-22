<%--
    Document   : makeOrder
    Created on : Jan 15, 2021, 2:46:12 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="bean.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
        <meta name="generator" content="Jekyll v4.1.1">
        <title>User - Make Order</title>

        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <!-- Site CSS -->
        <link rel="stylesheet" href="css/style.css"> 
        <!-- Responsive CSS -->
        <link rel="stylesheet" href="css/responsive.css"> 
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/custom.css">

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
                  <li class="nav-item active">
                    <a class="nav-link" href="#">Make Order</a>
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
                  User - Make Order <br>
                  This is a simple Food Delivery System
               </p>
            </div>
         </div>

         <!-- content start -->
         
         <div class="row">
             <form action="/FoodDelivery/AddOrderServlet" method="POST">
           <div class="row" style="padding-bottom:60px">
               
               <c:forEach items="${sessionScope.activeDishList}" var="dish" varStatus="loop">
                  <c:set var="dishPrice"><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${dish.price}"/></c:set>
                  
                                        
                                        
                <div class="column"style="column-width: 390px;">
          
                    <div class="products-single fix">
                        <div class="box-img-hover">
                            <div class="type-lb">
                                <c:choose>
                                    <c:when test="${dish.type.equals('Vegetarian')}">
                                        <p class="veg"><c:out value="${dish.type}"/></p>
                                    </c:when>
                                    <c:when test="${dish.type.equals('Spicy')}">
                                        <p class="spicy"><c:out value="${dish.type}"/></p>
                                    </c:when>
                                    <c:when test="${dish.type.equals('Western')}">
                                        <p class="western"><c:out value="${dish.type}"/></p>
                                    </c:when>
                                    <c:when test="${dish.type.equals('Asian')}">
                                        <p class="asian"><c:out value="${dish.type}"/></p>
                                    </c:when>
                                    <c:when test="${dish.type.equals('Appetizer')}">
                                        <p class="appetizer"><c:out value="${dish.type}"/></p>
                                    </c:when>
                                </c:choose>
                               
                            </div>
                            <img src="img/<c:out value='${dish.photo}'/>" class="img-fluid" alt="${dish.dishName}">
                            <div class="mask-icon">
                               
                                <div style="color:white;padding: 30px;text-align: center; margin: 0 auto;justify-content: center;position: absolute;
                                top: 30%;"> <c:out value="${dish.description}"/> </div>
                                
                            </div>
                        </div>
                        <div class="why-text">
                            <h4><c:out value="${dish.dishName}"/></h4>
                            <h5> RM<c:out value="${dishPrice}"/></h5>
                        </div>
                    </div>
                
               </div>
                </c:forEach>
   
          </div>
   
             
             
             <div class="row" style="padding-bottom:60px; column-width: 1000px;">
            <div class="col-md-6" style="column-width: 1500px;">

               <div class="card shadow">
                  <div class="card-body">

                      <h2>Make Order Form</h2>
                        
                            <div class="form-group">
                                <label for="dish">Dish</label>
                                <select class="form-control" name="dishOrder">
                                    <c:forEach items="${sessionScope.activeDishList}" var="dish" varStatus="loop">
                                        <c:set var="dishPrice"><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${dish.price}"/></c:set>
                                        <option value="${loop.index}"><c:out value="${dish.dishCode} | ${dish.dishName} | RM${dishPrice}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="quantity">Quantity</label>
                                <input type="number" name="quantity" id="quantity" class="form-control" required="" min="1" value="1">
                            </div>
                            <button type="submit" class="btn btn-primary">Add to Cart</button>

                  </div>
               </div>

            </div>
             </div>
             
         </form>
             <div class="row" style="padding-bottom:60px;">
                 
                     <!-- dynamic -->
                     
               <div class="card shadow" style="width: 1200px;">
                  <div class="card-body">
                      <h2>Your Order</h2>
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
                                 <th>Total Price</th>
                                 <th>Operations</th>
                              </tr>
                              </thead>
                              <tbody>
                                  <c:set var="totalPrice" value="${0}"/>
                                  <c:forEach items="${sessionScope.dishOrder}" var="dish" varStatus="loop">
                                      <c:set var="dishTotalPrice" value="${dish.price * Integer.parseInt(sessionScope.quantityList.get(loop.index))}"/>
                                      <c:set var="totalPrice" value="${totalPrice + dishTotalPrice}"/>
                                      <tr>
                                          <td><c:out value="${loop.index + 1}"/></td>
                                          <td><c:out value="${dish.dishCode}"/></td>
                                          <td><c:out value="${dish.dishName}"/></td>
                                          <td><img src="/FoodDelivery/img/${dish.photo}" alt="${dish.photo}" width="50" height="50"></td>
                                          <td>RM <fmt:formatNumber value="${dish.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                          <td><a href="/FoodDelivery/DecreaseQuantityServlet?index=${loop.index}"><img src="icons/minus-circle.svg" width="20" height="20"/></a>
                                              <c:out value="${sessionScope.quantityList.get(loop.index)}"/>
                                              <a href="/FoodDelivery/IncreaseQuantityServlet?index=${loop.index}"><img src="icons/plus-circle.svg" width="20" height="20"></a></td>
                                          <td>RM <fmt:formatNumber value="${dishTotalPrice}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                          <td><a href="/FoodDelivery/RemoveFromCartServlet?index=${loop.index}"><img src="icons/trash-fill.svg" alt="" width="20" height="20" title="Delete"></a></td>
                                      </tr>
                                  </c:forEach>
                                      <tr>
                                          <td colspan="6" align="right">Total Price: RM</td>
                                          <td><fmt:formatNumber value="${totalPrice}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                          <td><button type="submit" class="btn btn-primary" onclick="redirectToCartPage()">Go to Cart</button></td>
                                      </tr>    
                              </tbody>
                          </table>
                      </div>
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
            function redirectToCheckoutPage()
            {
                window.location = "/FoodDelivery/checkoutPage.jsp";
            }
            
            function redirectToCartPage()
            {
                window.location = "/FoodDelivery/cart.jsp";
            }
      </script>
      <script src="js/jquery-3.5.1.min.js"></script>
      <script src="js/popper.min.js"></script>
      <script src="js/bootstrap.min.js"></script>

    </body>
</html>
