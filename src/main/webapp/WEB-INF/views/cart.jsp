<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>

<%@ page import="com.fashionstore.model.CartItem" %>

<%@ page import="com.fashionstore.dao.ProductDAO" %>
<%@ page import="com.fashionstore.dao.ProductVariantDAO" %>

<%@ page import="com.fashionstore.dao.impl.ProductDAOImpl" %>
<%@ page import="com.fashionstore.dao.impl.ProductVariantDAOImpl" %>

<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.ProductVariant" %>

<%

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

    <title>My Cart</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/cart.css">

</head>

<body>

<!-- NAVBAR -->

<jsp:include page="partials/navbar.jsp" />

<div class="container cart-page">

    <h1 class="cart-title">

        Shopping Cart

    </h1>

    <%

        if (cartItems == null ||
                cartItems.isEmpty()) {

    %>

    <!-- EMPTY CART -->

    <div class="empty-cart">

        <h2>Your cart is empty.</h2>

        <a href="${pageContext.request.contextPath}/products">

            Continue Shopping

        </a>

    </div>

    <%

        } else {

    %>

    <!-- CART TABLE -->

    <table class="cart-table">

        <thead>

        <tr>

            <th>Product</th>

            <th>Variant</th>

            <th>Quantity</th>

            <th>Price</th>

            <th>Action</th>

        </tr>

        </thead>

        <tbody>

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

        <tr>

            <!-- PRODUCT -->

            <td>

                <div class="cart-product">

                    <img src="<%= product.getImageUrl() %>"
                         alt="<%= product.getName() %>">

                    <div>

                        <div class="cart-product-name">

                            <%= product.getName() %>

                        </div>

                        <div>

                            <%= product.getBrand() %>

                        </div>

                    </div>

                </div>

            </td>

            <!-- VARIANT -->

            <td>

                Size:
                <%= variant.getSize() %>

                <br>

                Color:
                <%= variant.getColor() %>

            </td>

            <!-- QUANTITY -->

            <td>

                <%= cartItem.getQuantity() %>

            </td>

            <!-- PRICE -->

            <td>

                ₹ <%= product.getPrice() %>

            </td>

            <!-- REMOVE -->

            <td>

                <a href="${pageContext.request.contextPath}/remove-cart-item?id=<%= cartItem.getCartItemId() %>"
                   class="remove-btn">

                    Remove

                </a>

            </td>

        </tr>

        <%

            }

        %>

        </tbody>

    </table>

    <!-- SUMMARY -->

    <div class="cart-summary">

        <div class="cart-summary-box">

            <h3>Cart Summary</h3>

            <div class="cart-total">

                <span>Total</span>

                <span>

                    ₹ <%= cartTotal %>

                </span>

            </div>

            <a href="${pageContext.request.contextPath}/checkout"
               class="checkout-btn">

                Proceed To Checkout

            </a>

        </div>

    </div>

    <%

        }

    %>

</div>

<!-- FOOTER -->

<jsp:include page="partials/footer.jsp" />

</body>

</html>