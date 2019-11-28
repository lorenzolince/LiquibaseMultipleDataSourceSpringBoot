# LiquibaseMultipleDataSourceSpringBoot
# test dynamic datasorce and create multiple schemas

1. git clone https://github.com/lorenzolince/docker.git
2. cd docker/ORACLE-DATABASE
3. run ./dockerBuild.sh
4. run docker-compose up -d
5. cd LiquibaseMultipleDataSourceSpringBoot
6.  mvn clean install spring-boot:run
7. to access  http://localhost:8088/liquibase/swagger-ui.html
