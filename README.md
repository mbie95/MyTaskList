# Project com.mycompany/MyTaskList

Steps to run this project (run commands below):

./mvnw clean install
#./mvnw flyway:migrate - run migrations if necessary
#change database's path!! in: src/main/resources/hibernate.cfg.xml, propoerty: connection.url
./mvnw jetty:run
http://127.0.0.1:8080/
