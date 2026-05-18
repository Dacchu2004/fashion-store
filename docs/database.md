# Database Documentation — Fashion Store

## Tables Overview

| Table | Rows Represent | Java Model |
|---|---|---|
| `users` | Registered customers | `User.java` |
| `categories` | Product categories (Men, Women…) | `Category.java` |
| `products` | Fashion items for sale | `Product.java` |
| `product_variants` | Size/color combinations of a product | `ProductVariant.java` |
| `carts` | One cart per user | `Cart.java` |
| `cart_items` | Items inside a cart | `CartItem.java` |
| `orders` | Placed orders | `Order.java` |
| `order_items` | Line items inside an order | `OrderItem.java` |

---

## Entity-Relationship Diagram

```mermaid
erDiagram
    users {
        int user_id PK
        varchar name
        varchar email
        varchar phone
        varchar password
        varchar address
        varchar city
        varchar state
        varchar pincode
        timestamp created_at
    }

    categories {
        int category_id PK
        varchar category_name
    }

    products {
        int product_id PK
        int category_id FK
        varchar name
        text description
        varchar brand
        decimal price
        varchar image_url
        timestamp created_at
    }

    product_variants {
        int variant_id PK
        int product_id FK
        varchar size
        varchar color
        int stock
    }

    carts {
        int cart_id PK
        int user_id FK
        timestamp created_at
    }

    cart_items {
        int cart_item_id PK
        int cart_id FK
        int variant_id FK
        int quantity
    }

    orders {
        int order_id PK
        int user_id FK
        decimal total_amount
        varchar status
        varchar payment_method
        varchar shipping_address
        varchar shipping_city
        varchar shipping_state
        varchar shipping_pincode
        timestamp created_at
    }

    order_items {
        int order_item_id PK
        int order_id FK
        int variant_id FK
        int quantity
        decimal price
    }

    users ||--o{ carts : "has one"
    users ||--o{ orders : "places"
    categories ||--o{ products : "contains"
    products ||--o{ product_variants : "has"
    carts ||--o{ cart_items : "contains"
    product_variants ||--o{ cart_items : "referenced by"
    orders ||--o{ order_items : "contains"
    product_variants ||--o{ order_items : "referenced by"
```

---

## Table Relationships

```mermaid
flowchart LR
    U[users] -->|1:1| C[carts]
    U -->|1:N| O[orders]
    CAT[categories] -->|1:N| P[products]
    P -->|1:N| PV[product_variants]
    C -->|1:N| CI[cart_items]
    PV -->|1:N| CI
    O -->|1:N| OI[order_items]
    PV -->|1:N| OI
```

| Relationship | Type | Description |
|---|---|---|
| `users` → `carts` | 1 : 1 | Each user has at most one cart |
| `users` → `orders` | 1 : N | A user can place many orders |
| `categories` → `products` | 1 : N | A category holds many products |
| `products` → `product_variants` | 1 : N | Each product has many size/color variants |
| `carts` → `cart_items` | 1 : N | A cart holds many items |
| `product_variants` → `cart_items` | 1 : N | A variant can be in many carts |
| `orders` → `order_items` | 1 : N | An order has many line items |
| `product_variants` → `order_items` | 1 : N | A variant can appear in many orders |

---

## Column Details

### `users`
| Column | Type | Notes |
|---|---|---|
| `user_id` | INT PK AUTO_INCREMENT | — |
| `name` | VARCHAR | Full name |
| `email` | VARCHAR UNIQUE | Used for login |
| `phone` | VARCHAR UNIQUE | — |
| `password` | VARCHAR | ⚠️ Stored plain-text currently |
| `address`, `city`, `state`, `pincode` | VARCHAR | Pre-filled at checkout |
| `created_at` | TIMESTAMP | Auto on insert |

### `products`
| Column | Type | Notes |
|---|---|---|
| `product_id` | INT PK AUTO_INCREMENT | — |
| `category_id` | INT FK → categories | — |
| `name` | VARCHAR | Display name |
| `description` | TEXT | — |
| `brand` | VARCHAR | — |
| `price` | DECIMAL(10,2) | Stored as `BigDecimal` in Java |
| `image_url` | VARCHAR | Relative path from webapp root |
| `created_at` | TIMESTAMP | Used for `getLatestProducts()` ORDER BY |

### `product_variants`
| Column | Type | Notes |
|---|---|---|
| `variant_id` | INT PK AUTO_INCREMENT | The unit added to cart |
| `product_id` | INT FK → products | — |
| `size` | VARCHAR | e.g. S, M, L, XL |
| `color` | VARCHAR | e.g. Red, Blue |
| `stock` | INT | Quantity available |

### `cart_items`
| Column | Type | Notes |
|---|---|---|
| `cart_item_id` | INT PK AUTO_INCREMENT | — |
| `cart_id` | INT FK → carts | — |
| `variant_id` | INT FK → product_variants | Specific size+color chosen |
| `quantity` | INT | Incremented if same variant added again |

### `orders`
| Column | Type | Notes |
|---|---|---|
| `order_id` | INT PK AUTO_INCREMENT | — |
| `user_id` | INT FK → users | — |
| `total_amount` | DECIMAL(10,2) | Computed from cart at time of order |
| `status` | VARCHAR | Default: `"Placed"` |
| `payment_method` | VARCHAR | COD / UPI / Credit Card |
| `shipping_*` | VARCHAR | Address captured at checkout |

### `order_items`
| Column | Type | Notes |
|---|---|---|
| `order_item_id` | INT PK AUTO_INCREMENT | — |
| `order_id` | INT FK → orders | — |
| `variant_id` | INT FK → product_variants | — |
| `quantity` | INT | — |
| `price` | DECIMAL(10,2) | Price at time of order (snapshot) |
