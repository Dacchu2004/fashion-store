<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

<head>

    <title>Login - Fashion Store</title>

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

            Welcome Back

        </h1>

        <p class="auth-subtitle">

            Login to continue shopping.

        </p>

        <!-- SUCCESS MESSAGE -->

        <%

            String success =
                    request.getParameter("success");

            if ("registered".equals(success)) {

        %>

        <div class="success-message">

            Registration successful.
            Please login.

        </div>

        <%

            }

        %>

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

        <!-- LOGIN FORM -->

        <form action="${pageContext.request.contextPath}/login"
              method="post"
              class="auth-form">

            <!-- EMAIL -->

            <div class="form-group">

                <label>Email</label>

                <input type="email"
                       name="email"
                       placeholder="Enter your email"
                       required>

            </div>

            <!-- PASSWORD -->

            <div class="form-group">

                <label>Password</label>

                <input type="password"
                       name="password"
                       placeholder="Enter your password"
                       required>

            </div>

            <!-- BUTTON -->

            <button type="submit"
                    class="auth-btn">

                Login

            </button>

        </form>

        <!-- REGISTER LINK -->

        <div class="auth-link">

            Don't have an account?

            <a href="${pageContext.request.contextPath}/register">

                Register

            </a>

        </div>

    </div>

</div>

<!-- FOOTER -->

<jsp:include page="partials/footer.jsp" />

</body>

</html>