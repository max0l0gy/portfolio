# Read Me First
The following was discovered as part of building this project:

# Getting Started

docker build -t maxmorev/portfolio-rest-api .

docker images
````
docker run -p 8081:8080 maxmorev/portfolio-rest-api --name portfolio-rest-api
````

echo $DOCKER_ACCESS_TOKEN | docker login -u maxmorev --password-stdin
docker build -t docker.io/maxmorev/portfolio-rest-api:test .
docker push docker.io/maxmorev/portfolio-rest-api:test



docker run --rm -p 5432:5432 \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_PASSWORD=postgres \
--name postgres \
postgres 
