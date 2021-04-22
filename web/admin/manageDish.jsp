<%-- 
    Document   : manageDish
    Created on : Jan 13, 2021, 8:47:29 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="bean.User" %>
<%@page import="bean.Dish" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
        <meta name="generator" content="Jekyll v4.1.1">
        <title>Admin - Manage Menu</title>
        
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
                   <li class="nav-item ">
                    <a class="nav-link" href="/FoodDelivery/admin/home.jsp">Home <span class="sr-only">(current)</span></a>
                  </li>
                  <li class="nav-item active">
                    <a class="nav-link" href="#">Manage Menu</a>
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
                  Admin - Manage Menu <br> 
                  This is a simple Food Delivery System 
               </p>
            </div>
         </div>         

         <!-- content start -->
         
         <c:choose>
             <c:when test="${(sessionScope.dishList.size()) == 0}">
                 <c:set var="dishcode" value="D001"/>
             </c:when>
             <c:otherwise>
                 <c:set var="dishcode" value="${sessionScope.nextCode}"></c:set>
             </c:otherwise>
         </c:choose>
         
         <div class="row">
             <div class="col-md-6">
               <div class="card shadow">
                  <div class="card-body">
                     <h2>Add Dish</h2>

                     <form action="/FoodDelivery/InsertDishServlet" method="POST">
                        <div class="form-group">
                          <label for="dishCode">Dish Code</label>
                          <input type="text" name="dishCode" class="form-control" id="dishCode" aria-describedby="DishCode" readonly="" value="${dishcode}">
                        </div>
                        <div class="form-group">
                          <label for="dishName">Dish Name</label>
                          <input type="text" name="dishName" class="form-control" id="dishName" aria-describedby="DishName" required>
                        </div>
                        <div class="form-group">
                          <label for="dishDescription">Dish Description</label>
                          <input type="text" name="dishDescription" class="form-control" id="dishDescription" aria-describedby="DishDescription" required>
                        </div>
                        
                        <div class="form-group">
                          <label for="price">Dish Price: RM</label>
                          <input type="text" name="price" class="form-control" id="price" aria-describedby="Price" required>
                        </div>
                        <div class="form-group">   
                          <label for="dishType">Dish Type:</label>
                            <select id="dishType" name="dishType" aria-describedby="DishType" required>
                              <option value="Vegetarian">Vegetarian</option>
                              <option value="Spicy">Spicy</option>
                              <option value="Western">Western</option>
                              <option value="Asian">Asian</option>
                              <option value="Appetizer">Appetizer</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit</button>
                     </form>                        
                  </div>
               </div>  
            </div>
         </div>

         <br><br>
         <div class="row">
            <div class="col-md-12">

               <div class="card shadow">
                  <div class="card-body">     
                      <h2>Dish List</h2>
                      <br>
                      <div class="table-responsive">
                          <table class="table table-striped table-bordered table-hover">
                              <thead class="thead-dark">
                                  <tr>
                                      <th>#</th>
                                      <th>Dish Code</th>
                                      <th>Dish Name</th>
                                      <th>Price</th>
                                      <th>Status</th>
                                      <th>Photo</th>
                                      <th>Operation</th>
                                  </tr>
                              </thead>
                              <tbody>
                                  <c:forEach items="${sessionScope.dishList}" var="dish" varStatus="loop">
                                      
                                       <!--Building url for each operation-->
                                          <c:url var="activationURL" value="/DishActivationServlet">
                                              <c:param name="id" value="${dish.id}"/>
                                              <c:param name="status" value="${dish.status}"/>
                                          </c:url>
                                          <c:url var="updateDishURL" value="/admin/updateDishForm.jsp">
                                              <c:param name="index" value="${loop.index}"/>
                                          </c:url>
                                          <c:url var="uploadDishPicURL" value="/admin/uploadDishPic.jsp">
                                              <c:param name="id" value="${dish.id}"/>
                                              <c:param name="originalPhoto" value="${dish.photo}"/>
                                          </c:url>
                                          <c:url var="deleteDishURL" value="/DeleteDishServlet">
                                              <c:param name="id" value="${dish.id}"/>
                                          </c:url>
                                       
                                      <tr>
                                          <td><c:out value="${loop.index + 1}"/></td>
                                          <td><c:out value="${dish.dishCode}"/></td>
                                          <td><c:out value="${dish.dishName}"/></td>
                                          <td><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${dish.price}"/></td>
                                          <td><a href="${activationURL}"><c:out value="${dish.status}"/></a></td>
                                          <td><img src="/FoodDelivery/img/${dish.photo}" width="50" height="50" alt="${dish.photo}" title="${dish.photo}"></td>
                                          <td>
                                              <a href="${updateDishURL}"><img src="icons/pencil-fill.svg" alt="Update" width="20" height="20" title="Update"></a>
                                              &nbsp
                                              <a href="${uploadDishPicURL}"><img src="icons/upload.svg" alt="Upload" width="20" height="20" title="Upload"></a>
                                              &nbsp
                                              <a href="${deleteDishURL}"><img src="icons/trash-fill.svg" alt="Delete" width="20" height="20" title="Delete"></a>
                                          </td>
                                      </tr>
                                  </c:forEach>
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
