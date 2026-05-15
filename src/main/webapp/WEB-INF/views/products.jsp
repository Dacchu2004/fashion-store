<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.Category" %>

<!DOCTYPE html>
<html>

<head>

    <title>Products - Fashion Store</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/products.css">

</head>

<body>

<!-- Navbar -->

<jsp:include page="partials/navbar.jsp" />

<div class="container products-page">

    <!-- PAGE HEADER -->

    <div class="products-header">

        <h1>Explore Products</h1>

        <p>
            Browse our latest fashion collections.
        </p>

    </div>

    <!-- FILTER SECTION -->

    <div class="filter-section">

        <!-- SEARCH -->

        <form action="${pageContext.request.contextPath}/products"
              method="get"
              class="search-box">

            <input type="text"
                   name="search"
                   placeholder="Search products..." />

        </form>

        <!-- CATEGORY FILTER -->

        <form action="${pageContext.request.contextPath}/products"
              method="get"
              class="category-filter">

            <select name="category"
                    onchange="this.form.submit()">

                <option value="">
                    All Categories
                </option>

                <%

                    List<Category> categories =
                            (List<Category>) request.getAttribute("categories");

                    if (categories != null) {

                        for (Category category : categories) {

                %>

                <option value="<%= category.getCategoryId() %>">

                    <%= category.getCategoryName() %>

                </option>

                <%

                        }
                    }

                %>

            </select>

        </form>

        <!-- SORT -->

        <form action="${pageContext.request.contextPath}/products"
              method="get"
              class="sort-filter">

            <select name="sort"
                    onchange="this.form.submit()">

                <option value="">
                    Sort By
                </option>

                <option value="lowToHigh">
                    Price: Low to High
                </option>

                <option value="highToLow">
                    Price: High to Low
                </option>

            </select>

        </form>

    </div>

    <!-- PRODUCTS GRID -->

    <div class="products-grid">

        <%

            List<Product> products =
                    (List<Product>) request.getAttribute("products");

            if (products != null && !products.isEmpty()) {

                for (Product product : products) {

        %>

        <!-- PRODUCT CARD -->

        <div class="product-card">

            <!-- IMAGE -->

            <div class="product-image">

                <img src="<%= product.getImageUrl() %>"
                     alt="<%= product.getName() %>">

            </div>

            <!-- PRODUCT INFO -->

            <div class="product-info">

                <p class="product-brand">

                    <%= product.getBrand() %>

                </p>

                <h3 class="product-name">

                    <%= product.getName() %>

                </h3>

                <p class="product-description">

                    <%= product.getDescription() %>

                </p>

                <p class="product-price">

                    ₹ <%= product.getPrice() %>

                </p>

                <a href="${pageContext.request.contextPath}/product-details?id=<%= product.getProductId() %>"
                   class="product-btn">

                    View Details

                </a>

            </div>

        </div>

        <%

                }

            } else {

        %>

        <!-- NO PRODUCTS -->

        <div class="no-products">

            <h2>No products found.</h2>

        </div>

        <%

            }

        %>

    </div>

</div>

<!-- Footer -->

<jsp:include page="partials/footer.jsp" />

</body>

</html>