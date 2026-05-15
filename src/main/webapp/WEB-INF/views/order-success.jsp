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
            position: relative;
            overflow: hidden;
        }

        .success-page::before {
            content: '';
            position: absolute;
            top: 20%;
            right: 10%;
            width: 300px;
            height: 300px;
            background: radial-gradient(circle, rgba(92,107,84,0.06) 0%, transparent 70%);
            border-radius: 50%;
            animation: float 6s ease-in-out infinite;
        }

        .success-card {
            background-color: var(--clr-surface);
            padding: 60px 50px;
            border-radius: var(--radius-xl);
            text-align: center;
            max-width: 520px;
            width: 100%;
            box-shadow: var(--shadow-lg);
            border: 1px solid var(--clr-border-light);
            position: relative;
            animation: scaleIn 0.6s ease;
        }

        .success-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 30px;
            right: 30px;
            height: 3px;
            background: linear-gradient(90deg, var(--clr-success), var(--clr-accent), var(--clr-success));
            border-radius: 0 0 3px 3px;
            background-size: 200% 100%;
            animation: gradientShift 3s ease infinite;
        }

        .success-icon {
            font-size: 56px;
            margin-bottom: 24px;
            line-height: 1;
            animation: float 3s ease-in-out infinite;
        }

        .success-title {
            font-family: var(--font-heading);
            font-size: 34px;
            font-weight: 600;
            color: var(--clr-text);
            margin-bottom: 16px;
            animation: fadeInUp 0.5s ease 0.2s both;
        }

        .success-message {
            color: var(--clr-text-secondary);
            font-size: 15px;
            line-height: 1.8;
            margin-bottom: 36px;
            animation: fadeInUp 0.5s ease 0.3s both;
        }

        .success-btn {
            display: inline-block;
            background-color: var(--clr-primary);
            color: #fff;
            padding: 14px 36px;
            border-radius: var(--radius-sm);
            text-decoration: none;
            font-family: var(--font-body);
            font-weight: 700;
            font-size: 12px;
            letter-spacing: 1.5px;
            text-transform: uppercase;
            transition: var(--transition);
            position: relative;
            overflow: hidden;
            animation: fadeInUp 0.5s ease 0.4s both;
        }

        .success-btn::after {
            content: '';
            position: absolute;
            top: 50%;
            left: 50%;
            width: 0;
            height: 0;
            background: rgba(255,255,255,0.15);
            border-radius: 50%;
            transform: translate(-50%, -50%);
            transition: width 0.5s, height 0.5s;
        }

        .success-btn:hover::after {
            width: 300px;
            height: 300px;
        }

        .success-btn:hover {
            background-color: var(--clr-primary-dark);
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(62, 74, 55, 0.25);
        }
    </style>
</head>

<body>

<jsp:include page="partials/navbar.jsp" />

<div class="success-page">
    <div class="success-card">
        <div class="success-icon">✅</div>
        <h1 class="success-title">Order Placed Successfully!</h1>
        <p class="success-message">
            Thank you for shopping with Fashion Store.
            Your order has been placed successfully
            and will be processed soon.
        </p>
        <a href="${pageContext.request.contextPath}/products"
           class="success-btn">Continue Shopping</a>
    </div>
</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>