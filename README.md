# springcsv-compare

This mini project tests the performance of Spring Boot applications using `CompletableFuture` to compare asynchronous and synchronous processing.

## Requirement

-   Java 17
-   Maven 3
-   PostgreSQL Database

## Installation

**1. Clone the repository:**

```bash
git clone https://github.com/ardwiinoo/springcsv-compare.git
```

**2. Enter directory**

```bash
cd springcsv-compare
```

**3. Setup .env**

Copy `.env.example` and create `.env` file

| Key                 | Description            | Example Value                              |
| ------------------- | ---------------------- | ------------------------------------------ |
| `DATABASE_URL`      | JDBC Database host URL | `jdbc:postgresql://<host>:<port>/<dbname>` |
| `DATABASE_USERNAME` | Database username      | `postgres`                                 |
| `DATABASE_PASSWORD` | Database password      | `supersecretpassword`                      |

**4. Run the application**

**5. Access the application**

`csvapp` running on port `8888`

`csvappfuture` running on port `8889`

## Testing

Tested using Apache JMeter, documentation will follow
