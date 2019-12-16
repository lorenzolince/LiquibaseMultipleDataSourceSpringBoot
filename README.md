# LiquibaseMultipleDataSourceSpringBoot
# test dynamic datasorce and create multiple schemas

1. git clone https://github.com/lorenzolince/docker.git
2. cd docker/ORACLE-DATABASE
3. run ./dockerBuild.sh
4. run docker-compose up -d
5. git clone https://github.com/lorenzolince/LiquibaseMultipleDataSourceSpringBoot.git
6. cd LiquibaseMultipleDataSourceSpringBoot
7. mvn clean install spring-boot:run
8. to access  http://localhost:8088/liquibase/swagger-ui.html
