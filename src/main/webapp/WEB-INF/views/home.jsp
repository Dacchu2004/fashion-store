<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.Category" %>
<%@ page import="com.fashionstore.model.User" %>

<%
    List<Product> latestProducts =
            (List<Product>) request.getAttribute("latestProducts");

    List<Category> categories =
            (List<Category>) request.getAttribute("categories");

    User loggedUser =
            (User) session.getAttribute("loggedInUser");
%>

<!DOCTYPE html>
<html>

<head>

    <title>Fashion Store — Home</title>

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

            <div class="hero-overlay"></div>

            <div class="hero-content">

                <span class="hero-badge">New Season 2026</span>

                <h1>
                    Elevate Your<br>
                    <span class="hero-accent">Everyday Style</span>
                </h1>

                <p>
                    Discover curated collections of clothing,
                    footwear, and accessories — crafted for
                    those who appreciate the art of dressing well.
                </p>

                <div class="hero-actions">

                    <a href="${pageContext.request.contextPath}/products"
                       class="btn btn-hero-primary">

                        Explore Collection

                    </a>

                    <a href="#categories"
                       class="btn btn-hero-secondary">

                        Browse Categories ↓

                    </a>

                </div>

                <div class="hero-stats">
                    <div class="stat-item">
                        <span class="stat-number">500+</span>
                        <span class="stat-label">Products</span>
                    </div>
                    <div class="stat-divider"></div>
                    <div class="stat-item">
                        <span class="stat-number">50+</span>
                        <span class="stat-label">Brands</span>
                    </div>
                    <div class="stat-divider"></div>
                    <div class="stat-item">
                        <span class="stat-number">Free</span>
                        <span class="stat-label">Shipping</span>
                    </div>
                </div>

            </div>

        </section>

        <!-- CATEGORY SECTION -->
        <section class="category-section" id="categories">

            <div class="section-header">
                <span class="section-label">Browse By</span>
                <h2 class="section-title">
                    Shop Categories
                </h2>
            </div>

            <div class="category-grid">

                <%
                    // Category icons/emojis for visual flair
                    String[] categoryIcons = {"👔", "👗", "👟", "⌚", "🧥", "👜", "🧢", "👕"};
                    int iconIndex = 0;

                    if (categories != null && !categories.isEmpty()) {

                        for (Category category : categories) {

                            String icon = categoryIcons[iconIndex % categoryIcons.length];
                            iconIndex++;
                %>

                <a href="${pageContext.request.contextPath}/products?category=<%= category.getCategoryId() %>"
                   class="category-card">

                    <span class="category-icon"><%= icon %></span>

                    <h3><%= category.getCategoryName() %></h3>

                    <span class="category-arrow">→</span>

                </a>

                <%
                        }
                    }
                %>

            </div>

        </section>

        <!-- LATEST PRODUCTS -->
        <section class="product-section">

            <div class="section-header">
                <span class="section-label">Just Arrived</span>
                <h2 class="section-title">
                    Latest Products
                </h2>
                <a href="${pageContext.request.contextPath}/products"
                   class="section-link">
                    View All →
                </a>
            </div>

            <div class="product-grid">

                <%
                    if (latestProducts != null &&
                            !latestProducts.isEmpty()) {

                        int delay = 0;
                        for (Product product : latestProducts) {
                %>

                <% String delayStyle = "animation-delay:" + delay + "ms"; %>
                <div class="product-card"
                     style="<%= delayStyle %>">

                    <div class="product-image-wrapper">

                        <img
                            src="${pageContext.request.contextPath}/<%= product.getImageUrl() %>"
                            alt="<%= product.getName() %>"
                            class="product-image">

                        <div class="product-overlay">
                            <a href="${pageContext.request.contextPath}/product-details?id=<%= product.getProductId() %>"
                               class="overlay-btn">
                                Quick View
                            </a>
                        </div>

                    </div>

                    <div class="product-info">

                        <p class="product-brand">
                            <%= product.getBrand() %>
                        </p>

                        <h3 class="product-name">
                            <%= product.getName() %>
                        </h3>

                        <div class="product-footer">
                            <p class="product-price">
                                ₹<%= product.getPrice() %>
                            </p>

                            <a href="${pageContext.request.contextPath}/product-details?id=<%= product.getProductId() %>"
                               class="product-link">
                                Details →
                            </a>
                        </div>

                    </div>

                </div>

                <%
                            delay += 60;
                        }
                    }
                %>

            </div>

        </section>

    </div>

    <!-- Footer -->
    <jsp:include page="partials/footer.jsp" />

</body>

</html>