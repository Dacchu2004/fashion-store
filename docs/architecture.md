# Architecture — Fashion Store

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 11 |
| Web Framework | Jakarta Servlet 5.0 + JSP |
| Build Tool | Maven |
| Database | MySQL |
| Server | Apache Tomcat |
| Frontend | HTML, CSS (Vanilla), JSP EL |

---

## MVC Pattern Overview

```mermaid
flowchart TD
    Browser(["🌐 Browser"])

    subgraph Controller["Controller Layer — com.fashionstore.controller"]
        C1[LoginServlet]
        C2[RegisterServlet]
        C3[HomeServlet]
        C4[ProductServlet]
        C5[ProductDetailsServlet]
        C6[AddToCartServlet]
        C7[CartServlet]
        C8[RemoveCartItemServlet]
        C9[CheckoutServlet]
        C10[PlaceOrderServlet]
        C11[LogoutServlet]
        C12[OrderSuccessServlet]
    end

    subgraph Model["Model Layer — com.fashionstore.model"]
        M1[User]
        M2[Product]
        M3[Category]
        M4[ProductVariant]
        M5[Cart]
        M6[CartItem]
        M7[Order]
        M8[OrderItem]
    end

    subgraph DAO["DAO Layer — com.fashionstore.dao"]
        D1[UserDAO / UserDAOImpl]
        D2[ProductDAO / ProductDAOImpl]
        D3[CategoryDAO / CategoryDAOImpl]
        D4[ProductVariantDAO / ProductVariantDAOImpl]
        D5[CartDAO / CartDAOImpl]
        D6[CartItemDAO / CartItemDAOImpl]
        D7[OrderDAO / OrderDAOImpl]
        D8[OrderItemDAO / OrderItemDAOImpl]
    end

    subgraph View["View Layer — WEB-INF/views"]
        V1[login.jsp]
        V2[register.jsp]
        V3[home.jsp]
        V4[products.jsp]
        V5[product-details.jsp]
        V6[cart.jsp]
        V7[checkout.jsp]
        V8[order-success.jsp]
        VP[partials/navbar.jsp\npartials/footer.jsp]
    end

    DB[(MySQL Database)]

    Browser -->|HTTP Request| Controller
    Controller -->|uses| Model
    Controller -->|calls| DAO
    DAO -->|JDBC| DB
    DB -->|ResultSet| DAO
    DAO -->|Model objects| Controller
    Controller -->|setAttribute + forward| View
    View -->|HTML Response| Browser
```

---

## Project Directory Structure

```
fashion-store/
├── src/main/
│   ├── java/com/fashionstore/
│   │   ├── controller/         ← 12 Servlets (Controllers)
│   │   ├── dao/                ← 8 DAO interfaces
│   │   │   └── impl/           ← 8 DAO implementations
│   │   ├── model/              ← 8 POJO model classes
│   │   └── util/
│   │       ├── DBConnection.java
│   │       └── AppInitializer.java
│   └── webapp/
│       ├── assets/
│       │   └── css/            ← style.css, home.css, products.css …
│       ├── WEB-INF/
│       │   ├── views/          ← 8 JSP pages + partials/
│       │   └── web.xml         ← DB credentials (not in Git)
│       └── index.jsp           ← Redirects to /login
├── docs/                       ← This documentation
└── pom.xml
```

---

## URL Routing Map

All routing is done via `@WebServlet` annotations — no `web.xml` servlet mappings needed.

| URL Pattern | Servlet | Method | Description |
|---|---|---|---|
| `/` | index.jsp | — | Redirects → `/login` |
| `/login` | LoginServlet | GET | Show login form |
| `/login` | LoginServlet | POST | Authenticate user |
| `/register` | RegisterServlet | GET | Show register form |
| `/register` | RegisterServlet | POST | Create new user |
| `/logout` | LogoutServlet | GET | Invalidate session |
| `/home` | HomeServlet | GET | Home page (auth required) |
| `/products` | ProductServlet | GET | Product listing + filter |
| `/product-details` | ProductDetailsServlet | GET | Single product view |
| `/add-to-cart` | AddToCartServlet | POST | Add item to cart |
| `/cart` | CartServlet | GET | View cart (auth required) |
| `/remove-cart-item` | RemoveCartItemServlet | GET | Remove cart item |
| `/checkout` | CheckoutServlet | GET | Checkout page (auth required) |
| `/place-order` | PlaceOrderServlet | POST | Create order, clear cart |
| `/order-success` | OrderSuccessServlet | GET | Success confirmation |

---

## Session & Authentication

```mermaid
flowchart LR
    A([User visits any URL]) --> B{Session has\n'loggedInUser'?}
    B -- No --> C[/redirect to /login/]
    B -- Yes --> D[Proceed normally]
    C --> E([Login Form])
    E --> F{Credentials valid?}
    F -- No --> E
    F -- Yes --> G[session.setAttribute\n'loggedInUser' = User]
    G --> H[/redirect to /home/]
```

**Protected routes** (redirect to `/login` if no session):
- `HomeServlet`, `CartServlet`, `CheckoutServlet`, `AddToCartServlet`, `PlaceOrderServlet`

---

## DB Credential Configuration

Credentials are **not hardcoded**. They are loaded from `web.xml` context params at startup by `AppInitializer.java` (a `ServletContextListener`), and stored statically in `DBConnection.java`.

```
web.xml  →  AppInitializer (on startup)  →  DBConnection.setCredentials()  →  DAO classes
```
