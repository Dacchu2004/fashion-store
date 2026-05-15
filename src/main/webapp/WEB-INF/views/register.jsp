<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

<head>

    <title>Register - Fashion Store</title>

    <!-- GLOBAL CSS -->

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">

    <!-- AUTH CSS -->

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/auth.css">

</head>

<body>

<!-- NAVBAR -->

<jsp:include page="partials/navbar.jsp" />

<div class="auth-page">

    <div class="auth-container">

        <!-- TITLE -->

        <h1 class="auth-title">

            Create Account

        </h1>

        <p class="auth-subtitle">

            Register to continue shopping.

        </p>

        <!-- ERROR MESSAGE -->

        <%

            String errorMessage =
                    (String) request.getAttribute("errorMessage");

            if (errorMessage != null) {

        %>

        <div class="error-message">

            <%= errorMessage %>

        </div>

        <%

            }

        %>

        <!-- REGISTER FORM -->

        <form action="${pageContext.request.contextPath}/register"
              method="post"
              class="auth-form">

            <!-- NAME -->

            <div class="form-group">

                <label>Name</label>

                <input type="text"
                       name="name"
                       placeholder="Enter your full name"
                       required>

            </div>

            <!-- EMAIL -->

            <div class="form-group">

                <label>Email</label>

                <input type="email"
                       name="email"
                       placeholder="Enter your email"
                       required>

            </div>

            <!-- PHONE -->

            <div class="form-group">

                <label>Phone</label>

                <input type="text"
                       name="phone"
                       placeholder="Enter phone number"
                       required>

            </div>

            <!-- PASSWORD -->

            <div class="form-group">

                <label>Password</label>

                <input type="password"
                       name="password"
                       placeholder="Enter password"
                       required>

            </div>

            <!-- ADDRESS -->

            <div class="form-group">

                <label>Address</label>

                <input type="text"
                       name="address"
                       placeholder="Enter address">

            </div>

            <!-- CITY -->

            <div class="form-group">

                <label>City</label>

                <input type="text"
                       name="city"
                       placeholder="Enter city">

            </div>

            <!-- STATE -->

            <div class="form-group">

                <label>State</label>

                <input type="text"
                       name="state"
                       placeholder="Enter state">

            </div>

            <!-- PINCODE -->

            <div class="form-group">

                <label>Pincode</label>

                <input type="text"
                       name="pincode"
                       placeholder="Enter pincode">

            </div>

            <!-- BUTTON -->

            <button type="submit"
                    class="auth-btn">

                Register

            </button>

        </form>

        <!-- LOGIN LINK -->

        <div class="auth-link">

            Already have an account?

            <a href="${pageContext.request.contextPath}/login">

                Login

            </a>

        </div>

    </div>

</div>

<!-- FOOTER -->

<jsp:include page="partials/footer.jsp" />

</body>

</html>