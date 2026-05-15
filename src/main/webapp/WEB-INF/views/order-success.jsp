<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html>

<head>

    <title>Order Placed Successfully</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">

    <style>

        .success-page {

            min-height: 70vh;

            display: flex;

            justify-content: center;

            align-items: center;

            padding: 50px 20px;
        }

        .success-card {

            background-color: white;

            padding: 50px;

            border-radius: 20px;

            text-align: center;

            max-width: 600px;

            width: 100%;

            box-shadow: 0 2px 15px rgba(0,0,0,0.08);
        }

        .success-icon {

            font-size: 70px;

            margin-bottom: 25px;
        }

        .success-title {

            font-size: 36px;

            color: #222;

            margin-bottom: 20px;
        }

        .success-message {

            color: #666;

            font-size: 17px;

            line-height: 1.8;

            margin-bottom: 35px;
        }

        .success-btn {

            display: inline-block;

            background-color: #7c3aed;

            color: white;

            padding: 15px 28px;

            border-radius: 12px;

            text-decoration: none;

            font-weight: 600;

            transition: 0.3s ease;
        }

        .success-btn:hover {

            background-color: #5b21b6;
        }

    </style>

</head>

<body>

<!-- NAVBAR -->

<jsp:include page="partials/navbar.jsp" />

<div class="success-page">

    <div class="success-card">

        <div class="success-icon">

            ✅

        </div>

        <h1 class="success-title">

            Order Placed Successfully!

        </h1>

        <p class="success-message">

            Thank you for shopping with Fashion Store.
            Your order has been placed successfully
            and will be processed soon.

        </p>

        <a href="${pageContext.request.contextPath}/products"
           class="success-btn">

            Continue Shopping

        </a>

    </div>

</div>

<!-- FOOTER -->

<jsp:include page="partials/footer.jsp" />

</body>

</html>