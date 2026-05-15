<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>

<%
    List<Product> latestProducts =
            (List<Product>) request.getAttribute("latestProducts");
%>

<!DOCTYPE html>
<html>

<head>

    <title>Fashion Store</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/home.css">

</head>

<body>

    <!-- Navbar -->
    <jsp:include page="partials/navbar.jsp" />

    <div class="container">

        <!-- HERO SECTION -->
        <section class="hero-section">

            <div class="hero-content">

                <h1>
                    Discover Your Fashion Style
                </h1>

                <p>
                    Explore the latest trends in fashion,
                    footwear, and accessories.
                </p>

                <a href="${pageContext.request.contextPath}/products"
                   class="btn">

                    Shop Now

                </a>

            </div>

        </section>

        <!-- CATEGORY SECTION -->
        <section class="category-section">

            <h2 class="section-title">
                Categories
            </h2>

            <div class="category-grid">

                <div class="category-card">
                    <h3>Men</h3>
                </div>

                <div class="category-card">
                    <h3>Women</h3>
                </div>

                <div class="category-card">
                    <h3>Footwear</h3>
                </div>

                <div class="category-card">
                    <h3>Accessories</h3>
                </div>

            </div>

        </section>

        <!-- LATEST PRODUCTS -->
        <section class="product-section">

            <h2 class="section-title">
                Latest Products
            </h2>

            <div class="product-grid">

                <%
                    if (latestProducts != null &&
                            !latestProducts.isEmpty()) {

                        for (Product product : latestProducts) {
                %>

                <div class="product-card">

                    <img
                        src="${pageContext.request.contextPath}/<%= product.getImageUrl() %>"
                        alt="Product Image"
                        class="product-image">

                    <div class="product-info">

                        <p class="product-brand">
                            <%= product.getBrand() %>
                        </p>

                        <h3 class="product-name">
                            <%= product.getName() %>
                        </h3>

                        <p class="product-price">
                            ₹ <%= product.getPrice() %>
                        </p>

                        <a href="#"
                           class="btn">

                            View Details

                        </a>

                    </div>

                </div>

                <%
                        }
                    }
                %>

            </div>

        </section>

    </div>

    <!-- Footer -->
    <jsp:include page="partials/footer.jsp" />

</body>

</html>ś