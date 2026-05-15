<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.ProductVariant" %>
<%@ page import="java.util.List" %>

<%

    Product product =
            (Product) request.getAttribute("product");

    List<ProductVariant> variants =
            (List<ProductVariant>)
                    request.getAttribute("variants");

%>

<!DOCTYPE html>

<html>

<head>

    <title>

        <%= product.getName() %>

    </title>

    <!-- GLOBAL CSS -->

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">

    <!-- PAGE CSS -->

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/product-details.css">

</head>

<body>

<!-- NAVBAR -->

<jsp:include page="partials/navbar.jsp" />

<div class="container product-details-page">

    <div class="product-details-container">

        <!-- PRODUCT IMAGE -->

        <div class="product-image-section">

            <img src="<%= product.getImageUrl() %>"
                 alt="<%= product.getName() %>">

        </div>

        <!-- PRODUCT INFO -->

        <div class="product-info-section">

            <!-- BRAND -->

            <p class="product-brand">

                <%= product.getBrand() %>

            </p>

            <!-- PRODUCT NAME -->

            <h1 class="product-title">

                <%= product.getName() %>

            </h1>

            <!-- DESCRIPTION -->

            <p class="product-description">

                <%= product.getDescription() %>

            </p>

            <!-- PRICE -->

            <p class="product-price">

                ₹ <%= product.getPrice() %>

            </p>

            <!-- ADD TO CART FORM -->

            <form action="${pageContext.request.contextPath}/add-to-cart"
                  method="post">

                <!-- VARIANTS -->

                <div class="variant-section">

                    <h3 class="variant-title">

                        Select Variant

                    </h3>

                    <div class="variant-list">

                        <%

                            if (variants != null &&
                                    !variants.isEmpty()) {

                                for (ProductVariant variant : variants) {

                        %>

                        <label class="variant-item">

                            <input type="radio"
                                   name="variantId"
                                   value="<%= variant.getVariantId() %>"
                                   required>

                            Size:
                            <%= variant.getSize() %>

                            |

                            Color:
                            <%= variant.getColor() %>

                        </label>

                        <%

                                }

                            }

                        %>

                    </div>

                </div>

                <!-- STOCK -->

                <p class="stock-status">

                    In Stock

                </p>

                <!-- BUTTON -->

                <button type="submit"
                        class="add-to-cart-btn">

                    Add To Cart

                </button>

            </form>

        </div>

    </div>

</div>

<!-- FOOTER -->

<jsp:include page="partials/footer.jsp" />

</body>

</html>