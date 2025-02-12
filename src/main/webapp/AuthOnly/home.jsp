<%@ page import="am.personal.acc_management.Model.User" %>
<%@ page import="am.personal.acc_management.Model.Product" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/8/2025
  Time: 3:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Your page</title>

    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            display: flex;
        }
        .cart-section {
            width: 25%;
            margin: 1%;
        }
        .product-section {
            width: 70%;
            margin: 1%;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        button {
            padding: 5px 10px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 4px;
        }
        button:hover {
            background-color: #0056b3;
        }
        .header {
            margin-left: 40%;
        }
        .action-buttons {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-right: 1%;
        }
    </style>
</head>
<body>
<br><br>
<%
    User user = (User) request.getSession().getAttribute("user");
%>

<h2 class="header">Home page, <%= user.getUsername() %></h2>

<br><br>
<p style="margin-left: 1%;">Balance: <%= "$" + user.getBalance() %></p>

<div class="action-buttons">
    <button style="background: aqua">
        <a href="AuthOnly/changePassword.jsp" style="text-decoration: none">Change Password</a>
    </button>
    <form method="post" action="/logout">
        <button style="background: crimson  ">Sign out</button>
    </form>
</div>

<div class="container">
    <!-- Cart Section -->
    <div class="cart-section">
        <h3>Your Cart</h3>
        <table>
            <thead>
            <tr>
                <th>Product</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Product> cart = (List<Product>)request.getSession().getAttribute("cart");
                if (cart != null && !cart.isEmpty()) {
                    for (Product item : cart) {
            %>
            <tr>
                <td><%= item.getName() %></td>
                <td><%= item.getAmount() %></td>
                <td><%= "$" + item.getPrice() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="3">Your cart is empty.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- Product Section -->
    <div class="product-section">
        <h3>Explore Our Products</h3>
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Amount Left</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Product> products = (List<Product>) request.getSession().getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
                        if(product.getAmount()>0){
            %>
            <tr>
                <td><%= product.getName() %></td>
                <td><%= product.getPrice() %></td>
                <td><%= product.getAmount() %></td>
                <td>
                    <form action="/addToCart" method="post">
                        <input type="hidden" name="productName" value="<%= product.getName() %>">
                        <input type="hidden" name="email" value="<%= user.getEmail() %>">
                        <button type="submit">Add to Cart</button>
                    </form>
                </td>
            </tr>
            <%

                    }
                }
            } else {
            %>
            <tr>
                <td colspan="4">No products available.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
</body>
