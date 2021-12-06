# super-duper-octo-fiesta
run app:
  - mvn clean install
  - docker-compose up --build

test user: 
  - admin / 123456
  - customer / 123456
  
swagger: http://localhost:8080/swagger-ui/index.html?#/

note : when reach swagger url write '/v3/api-docs' to the search field and search it, it has some issue on swagger on docker :(
