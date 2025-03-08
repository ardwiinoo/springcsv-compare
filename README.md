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

## References

[1] M. H. Aissi, "Populating DataBase in Spring Boot using CSV files and OpenCsv" Medium, Jun. 16, 2024. [Online]. Available: https://medium.com/@mohamedhedi.aissi/spring-boot-csv-service-using-opencsv-5afd5c66c125. [Accessed: Feb. 27, 2025].

[2] A. Sahni, "Difference Between CompletableFuture And Future In Java," Hungry Coders, Sep. 14, 2024. [Online]. Available: https://www.hungrycoders.com/blog/difference-between-completablefuture-and-future-in-java. [Accessed: Mar. 01, 2025].

[3] P. Ravvich, "Hibernate Optimization with @BatchSize and batch_size Configuration," Medium, Mar. 2, 2024. [Online]. Available: https://medium.com/jpa-java-persistence-api-guide/hibernate-optimization-with-batchsize-and-batch-size-configuration-579bf759fc05. [Accessed: Mar. 03, 2025].

[4] U. Kumari, "How to Upload File in JMeter," Auriga IT, Jul. 5, 2022. [Online]. Available: https://aurigait.com/blog/how-to-upload-file-in-jmeter/. [Accessed: Mar. 01, 2025].

[5] D. Smith, "How Does Spring Boot Implement Asynchronous Programming? This Is How Masters Do It!" Javarevisited, Sep. 11, 2024. [Online]. Available: https://medium.com/javarevisited/how-does-spring-boot-implement-asynchronous-programming-this-is-how-masters-do-it-e89fc9245928. [Accessed: Mar. 02, 2025].

[6] A. Gola, "Why have a CompletableFuture when we have an ExecutorService?," Medium, Feb. 21, 2023. [Online]. Available: https://medium.com/@anil.java.story/why-have-a-completefuture-when-we-have-an-executorservice-which-problem-did-it-solve-f1cf083f834c. [Accessed: Mar. 02, 2025].
