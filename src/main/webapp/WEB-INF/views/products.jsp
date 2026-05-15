<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.Category" %>

<%
    String selectedCategory =
            (String) request.getAttribute("selectedCategory");

    String selectedSort =
            (String) request.getAttribute("selectedSort");

    String searchQuery =
            (String) request.getAttribute("searchQuery");

    if (selectedCategory == null) selectedCategory = "";
    if (selectedSort == null) selectedSort = "";
    if (searchQuery == null) searchQuery = "";
%>

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

    <!-- FILTER SECTION — SINGLE FORM -->

    <form action="${pageContext.request.contextPath}/products"
          method="get"
          class="filter-section">

        <!-- SEARCH -->

        <div class="search-box">

            <input type="text"
                   name="search"
                   placeholder="Search products..."
                   value="<%= searchQuery %>" />

        </div>

        <!-- CATEGORY FILTER -->

        <div class="category-filter">

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

                            String catId =
                                    String.valueOf(category.getCategoryId());

                            String isSelected =
                                    catId.equals(selectedCategory)
                                            ? "selected" : "";

                %>

                <option value="<%= category.getCategoryId() %>"
                        <%= isSelected %>>

                    <%= category.getCategoryName() %>

                </option>

                <%

                        }
                    }

                %>

            </select>

        </div>

        <!-- SORT -->

        <div class="sort-filter">

            <select name="sort"
                    onchange="this.form.submit()">

                <option value="">
                    Sort By
                </option>

                <option value="lowToHigh"
                        <%= "lowToHigh".equals(selectedSort) ? "selected" : "" %>>
                    Price: Low to High
                </option>

                <option value="highToLow"
                        <%= "highToLow".equals(selectedSort) ? "selected" : "" %>>
                    Price: High to Low
                </option>

            </select>

        </div>

    </form>

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