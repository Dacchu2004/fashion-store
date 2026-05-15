<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>

<%@ page import="com.fashionstore.model.User" %>
<%@ page import="com.fashionstore.model.CartItem" %>

<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.ProductVariant" %>

<%@ page import="com.fashionstore.dao.ProductDAO" %>
<%@ page import="com.fashionstore.dao.ProductVariantDAO" %>

<%@ page import="com.fashionstore.dao.impl.ProductDAOImpl" %>
<%@ page import="com.fashionstore.dao.impl.ProductVariantDAOImpl" %>

<%

    User user =
            (User) request.getAttribute("user");

    List<CartItem> cartItems =
            (List<CartItem>) request.getAttribute("cartItems");

    BigDecimal cartTotal =
            (BigDecimal) request.getAttribute("cartTotal");

    ProductDAO productDAO =
            new ProductDAOImpl();

    ProductVariantDAO productVariantDAO =
            new ProductVariantDAOImpl();

%>

<!DOCTYPE html>

<html>

<head>

    <title>Checkout</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/checkout.css">

</head>

<body>

<!-- NAVBAR -->

<jsp:include page="partials/navbar.jsp" />

<div class="container checkout-page">

    <h1 class="checkout-title">

        Checkout

    </h1>

    <div class="checkout-container">

        <!-- LEFT SECTION -->

        <div class="checkout-form-section">

            <h2 class="form-section-title">

                Shipping Details

            </h2>

            <form action="${pageContext.request.contextPath}/place-order"
                  method="post">

                <!-- NAME -->

                <div class="form-group">

                    <label>

                        Full Name

                    </label>

                    <input type="text"
                           name="name"
                           value="<%= user.getName() %>"
                           required>

                </div>

                <!-- PHONE -->

                <div class="form-group">

                    <label>

                        Phone Number

                    </label>

                    <input type="text"
                           name="phone"
                           value="<%= user.getPhone() %>"
                           required>

                </div>

                <!-- ADDRESS -->

                <div class="form-group">

                    <label>

                        Shipping Address

                    </label>

                    <textarea name="address"
                              required><%= user.getAddress() %></textarea>

                </div>

                <!-- CITY + STATE -->

                <div class="form-grid">

                    <div class="form-group">

                        <label>

                            City

                        </label>

                        <input type="text"
                               name="city"
                               value="<%= user.getCity() %>"
                               required>

                    </div>

                    <div class="form-group">

                        <label>

                            State

                        </label>

                        <input type="text"
                               name="state"
                               value="<%= user.getState() %>"
                               required>

                    </div>

                </div>

                <!-- PINCODE -->

                <div class="form-group">

                    <label>

                        Pincode

                    </label>

                    <input type="text"
                           name="pincode"
                           value="<%= user.getPincode() %>"
                           required>

                </div>

                <!-- PAYMENT METHOD -->

                <div class="form-group">

                    <label>

                        Payment Method

                    </label>

                    <select name="paymentMethod"
                            required>

                        <option value="">
                            Select Payment Method
                        </option>

                        <option value="Cash On Delivery">
                            Cash On Delivery
                        </option>

                        <option value="UPI">
                            UPI
                        </option>

                        <option value="Credit Card">
                            Credit Card
                        </option>

                    </select>

                </div>

                <!-- ORDER SUMMARY -->

                <div class="order-summary">

                    <h2 class="summary-title">

                        Order Summary

                    </h2>

                    <%

                        for (CartItem cartItem : cartItems) {

                            ProductVariant variant =
                                    productVariantDAO.getVariantById(
                                            cartItem.getVariantId()
                                    );

                            Product product =
                                    productDAO.getProductById(
                                            variant.getProductId()
                                    );

                    %>

                    <div class="summary-row">

                        <span>

                            <%= product.getName() %>

                            x

                            <%= cartItem.getQuantity() %>

                        </span>

                        <span>

                            ₹ <%= product.getPrice() %>

                        </span>

                    </div>

                    <%

                        }

                    %>

                    <!-- TOTAL -->

                    <div class="summary-total">

                        <span>Total</span>

                        <span>

                            ₹ <%= cartTotal %>

                        </span>

                    </div>

                    <!-- BUTTON -->

                    <button type="submit"
                            class="place-order-btn">

                        Place Order

                    </button>

                </div>

            </form>

        </div>

    </div>

</div>

<!-- FOOTER -->

<jsp:include page="partials/footer.jsp" />

</body>

</html>