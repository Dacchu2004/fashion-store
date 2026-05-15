<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.fashionstore.model.User" %>

<%

    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

%>

<header class="navbar">

    <div class="container navbar-container">

        <!-- LOGO -->

        <div class="logo">

            <a href="${pageContext.request.contextPath}/home">

                Fashion Store

            </a>

        </div>

        <!-- SEARCH BAR -->

        <div class="search-bar">

            <form action="${pageContext.request.contextPath}/products"
                  method="get">

                <input type="text"
                       name="search"
                       placeholder="Search products..." />

                <button type="submit">

                    Search

                </button>

            </form>

        </div>

        <!-- NAVIGATION -->

        <nav class="nav-links">

            <a href="${pageContext.request.contextPath}/home">

                Home

            </a>

            <a href="${pageContext.request.contextPath}/products">

                Products

            </a>

            <a href="${pageContext.request.contextPath}/cart">

                Cart

            </a>

            <%

                // USER LOGGED IN

                if (loggedInUser != null) {

            %>

            <!-- USER NAME -->

            <a href="#">

                Hi,
                <%= loggedInUser.getName() %>

            </a>

            <!-- LOGOUT -->

            <a href="${pageContext.request.contextPath}/logout">

                Logout

            </a>

            <%

                } else {

            %>

            <!-- LOGIN -->

            <a href="${pageContext.request.contextPath}/login">

                Login

            </a>

            <!-- REGISTER -->

            <a href="${pageContext.request.contextPath}/register">

                Register

            </a>

            <%

                }

            %>

        </nav>

    </div>

</header>