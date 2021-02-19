# Order Service

Based on user-svc-helidon 

## Framework

This service utilizes Helidon MP with Hibernate to persist orders in an H2 DB


## Building

```bash
mvn package
```

## Running

 ```bash
java -jar target/order-svc.jar
```

## Test Endpoints

Get Order Service Endpoint (returns 200 OK):

```
curl -iX GET http://localhost:8080/oder                                                                                                                                                    

Save a new order  (ID is returned in `Location` header):



curl -iX POST -H "Content-Type: application/json" -d '{"firstName": "Todd", "lastName": "Sharp", "username": "recursivecodes"}' http://localhost:8080/user/save                            





## View Health and Metrics

```bash
curl -s -X GET http://localhost:8080/health                                                                                                                                                


                                                                                                                                      


## Dockerfile

The generated `Dockerfile` requires some changes. See the `Dockerfile` for reference, particularly the need to install the `ojdbc` dependencies to the local Maven repo so they are included in the build since these are unavailable via public Maven repos. 

## Building the Docker Image

```
docker build -t order-svc .
```

## Running with Docker

```
docker run --rm -p 8080:8080 order-svc:latest
```

Test the endpoints as [described above](#test-endpoints)

## Deploying to Kubernetes

```
kubectl cluster-info                         # Verify which cluster
kubectl get pods                             # Verify connectivity to cluster
kubectl create -f app.yaml               # Deploy application
kubectl get service user-svc-helidon  # Verify deployed service
```