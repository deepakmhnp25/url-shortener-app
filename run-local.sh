mvn clean install
docker build -t urlshortener .
docker run -p8080:8080 urlshortener