# reservation

## Running in local development environment

```
mvn spring-boot:run
```

## Packaging and Running in docker environment

```
mvn package -B
docker build -t username/reservation:v1 .
docker run username/reservation:v1
```

## Push images and running in Kubernetes

```
docker login 
# in case of docker hub, enter your username and password

docker push username/reservation:v1
```

Edit the deployment.yaml under the /kubernetes directory:
```
    spec:
      containers:
        - name: reservation
          image: username/reservation:latest   # change this image name
          ports:
            - containerPort: 8080

```

Apply the yaml to the Kubernetes:
```
kubectl apply -f kubernetes/deployment.yml
```

See the pod status:
```
kubectl get pods -l app=reservation
```

If you have no problem, you can connect to the service by opening a proxy between your local and the kubernetes by using this command:
```
# new terminal
kubectl port-forward deploy/reservation 8080:8080

# another terminal
http localhost:8080
```

If you have any problem on running the pod, you can find the reason by hitting this:
```
kubectl logs -l app=reservation
```

Following problems may be occurred:

1. ImgPullBackOff:  Kubernetes failed to pull the image with the image name you've specified at the deployment.yaml. Please check your image name and ensure you have pushed the image properly.
1. CrashLoopBackOff: The spring application is not running properly. If you didn't provide the kafka installation on the kubernetes, the application may crash. Please install kafka firstly:

https://labs.msaez.io/#/courses/cna-full/full-course-cna/ops-utility

## test

http :8082/reservations 
http POST :8082/reservations rsvPlace="seoul" itemId=1 rsvStatus="CREATED"
http PATCH :8082/reservations/1 rsvStatus="CONFIRMED" 
http DELETE :8082/reservations/1 

http POST :8082/reservations rsvPlace="seoul" itemId=1000 rsvStatus="CREATED" #must fail , itemId is not exist!!

http POST :8082/reservations rsvPlace="seoul" itemId=2 rsvStatus="CREATED"
http PATCH :8082/reservations/3 rsvStatus="CONFIRMED" 