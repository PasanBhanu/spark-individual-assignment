<p align="center"><img src="https://static.wixstatic.com/media/32f65e_b51531ff0cdb48e58bc3e2d309fe0133~mv2.png/v1/fill/w_160,h_36,al_c,q_85,usm_0.66_1.00_0.01/Spark%20logo%20X%20RGB%20dark.webp" width="160"></p>

# Introduction
Individual assignment for SparkX. A Covid Management System to manage hospitals and patients. Backend developed using Java & MySQL.

Backend   : [View Repository](https://github.com/PasanBhanu/spark-individual-assignment)

Frontend  : [View Repository](https://github.com/PasanBhanu/spark-individual-assignment-frontend)

# Instructions
- Clone the repository
- Install dependancies
- Create database on MySQL and import the tables. Tables are is database folder
- Update the database connection parameters in src/main/java/lk.spark.pasan/helpers Database.java file

``` java
final String dbURL = "jdbc:mysql://localhost:3306/";
final String dbName = "spark";
final String dbUsername = "root";
final String dbPassword = "";
```

# Dependancies
- Java : 14.0.2
- javax.servlet:javax.servlet-api:3.1.0
- com.google.code.gson:gson:2.8.6
- mysql:mysql-connector-java:8.0.21
