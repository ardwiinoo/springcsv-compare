# springcsv-compare

This mini project tests the performance of Spring Boot applications using `CompletableFuture` to compare asynchronous and synchronous processing.

## Requirements

-   Java 17
-   Maven 3
-   PostgreSQL Database

## Installation

**1. Clone the repository:**

```bash
git clone https://github.com/ardwiinoo/springcsv-compare.git
```

**2. Enter the directory:**

```bash
cd springcsv-compare
```

**3. Set up the .env file:**

Copy `.env.example` and create a `.env` file with the following content:

| Key                 | Example Value                              |
| ------------------- | ------------------------------------------ |
| `DATABASE_URL`      | `jdbc:postgresql://<host>:<port>/<dbname>` |
| `DATABASE_USERNAME` | `postgres`                                 |
| `DATABASE_PASSWORD` | `supersecretpassword`                      |

**4. Run the application**

**5. Access the application:**

-   `csvapp` running on port `8888`
-   `csvappfuture` running on port `8889`

## Testing with JMeter

**Testing Scenarios:**

In JMeter, `Thread Group` is used with the following specifications:

**_[ A ]._**

| Key                       | Value |
| ------------------------- | ----- |
| Number of threads (users) | 1     |
| Ramp-up period (seconds)  | 5     |
| Loop count                | 1     |

**_[ B ]._**

| Key                       | Value |
| ------------------------- | ----- |
| Number of threads (users) | 5     |
| Ramp-up period (seconds)  | 5     |
| Loop count                | 1     |

Testing is conducted on both `csvapp` and `csvappfuture` services.

**Additional Properties:**

For more varied comparisons, testing is conducted under two scenarios:

**_[ 1 ]. Service with additional properties:_**

```properties
spring.jpa.properties.hibernate.jdbc.batch_size=1000
```

Explanation:

-   `spring.jpa.properties.hibernate.jdbc.batch_size=1000`: Configures Hibernate to batch 1000 JDBC operations, improving performance by reducing database calls.

**_[ 2 ]. Service without additional properties_**

This property is added to provide variation in testing or performance comparison between services.

**Test Data:**

Testing is performed by simulating HIT endpoints using the POST method with multipart/form-data, uploading a .csv file containing 31,627 records in each request.

**Result**

| Service        | Scenario | Average (ms) | Min (ms) | Max (ms) | Throughput (data/min) |
| -------------- | -------- | ------------ | -------- | -------- | --------------------- |
| `csvappfuture` | A + 1    | **2176**     | 2176     | 2176     | 27.6/min              |
| `csvapp`       | A + 1    | 3175         | 3175     | 3175     | 18.9/min              |
| `csvappfuture` | A + 2    | **5264**     | 5264     | 5264     | 11.4/min              |
| `csvapp`       | A + 2    | 43771        | 43771    | 43771    | 1.4/min               |
| `csvappfuture` | B + 1    | **3652**     | 1835     | 4854     | 34.8/min              |
| `csvapp`       | B + 1    | 4625         | 4179     | 5079     | 36.7/min              |
| `csvappfuture` | B + 2    | **16292**    | 9526     | 21714    | 11.7/min              |
| `csvapp`       | B + 2    | 44653        | 44497    | 44872    | 6.1/min               |
