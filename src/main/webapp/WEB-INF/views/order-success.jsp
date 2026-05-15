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

            background-color: var(--clr-surface);

            padding: 60px 50px;

            border-radius: var(--radius-xl);

            text-align: center;

            max-width: 560px;

            width: 100%;

            box-shadow: var(--shadow-lg);
            border: 1px solid var(--clr-border-light);
        }

        .success-icon {

            font-size: 64px;

            margin-bottom: 28px;
            line-height: 1;
        }

        .success-title {

            font-family: var(--font-heading);
            font-size: 36px;
            font-weight: 600;

            color: var(--clr-text);

            margin-bottom: 18px;
        }

        .success-message {

            color: var(--clr-text-secondary);

            font-size: 16px;

            line-height: 1.8;

            margin-bottom: 36px;
        }

        .success-btn {

            display: inline-block;

            background-color: var(--clr-primary);

            color: #fff;

            padding: 14px 32px;

            border-radius: var(--radius-sm);

            text-decoration: none;

            font-family: var(--font-body);
            font-weight: 600;
            font-size: 12px;
            letter-spacing: 1px;
            text-transform: uppercase;

            transition: var(--transition);
        }

        .success-btn:hover {

            background-color: var(--clr-primary-dark);
            transform: translateY(-1px);
            box-shadow: var(--shadow-md);
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