# kubernetes-api

## Description

Provides access to the Kubernetes API server to retrieve the cluster metrics.

## Usage

To execute this service, deploy it to a web server and execute a `curl` to the different endpoints exposed by `/api/fibonacci-job` and `/api/cluster`.

### Fibonacci Job Endpoint

The Fibonacci Job endpoint (`/api/fibonacci-job`) gets information about the Kubernetes job that will call the `fibonacci` service programatically. The information exposed is:

* `/api/fibonacci-job/count`: Determines whether the job is currently running or not.
* `/api/fibonacci-job/parameters`: Gets the parameters used to execute the fibonacci job.

### Cluster Enpoint

The Cluster endpoint (`/api/cluster`) gets information about the current nodes joined to the Kubernets cluster. The information exposed is:

* `/api/cluster/node-count`: Gets the number of current nodes running in the Kubernetes cluster.
* `/api/cluster/status`: Gets the CPU and memory metrics of all the nodes running in the Kubernetes cluster.
* `/api/cluster/hpa-status`: Gets the CPU and memory metrics of the `fibonacci` Horizontal Pod Autoscaler.

### Example
```
# Gets the metrics of the nodes in the cluster.
curl http://url.of.this.service/api/cluster/status

# Creates a new Kubernetes fibonacci job to start the test.
curl -vX PUT http://url.of.this.service/api/fibonacci-job -d '{"requests":"5000","concurrency":"10"}' --header "Content-Type: application/json"

# Deletes the fibonacci job, hence stopping the test.
curl -vX DELETE http://url.of.this.service/api/fibonacci-job
```

## Dependencies

This project depends on the Fibonacci micro service as well as many other Kubernetes objects that are deployed to the Kubernetes server. The main dependency is with a Kubernetes Job called Fibonacci and with a Kubernetes Horizontal Pod Autoscaler which tracks metrics of the Fibonacci Deployment object and its ReplicaSet. See the _deployment_ project for more information about these Kubernetes objects.

## Role

Altough the above operations could be performed by the Kubernetes API server by itself, accessing the API service directly through its REST endpoints requires to get a token and expose the Kubernetes API server to the Internet, which is not desirable.

The solution was to create this Java Spring Boot project to call the Kubernetes API Server programatically (using `kubectl`) and to only expose the required resources through the use of the Kubernetes RBAC.

There are several RBAC objects created specifically to grant access only to the resources exposed by this service. A Kubernetes Service Account is created and a Role and a RoleBinding objects are created for this purpose as well.

Check the `deployment` project to see the details about these RBAC objects and the permissions they grant to this particular service.

## Further Development

* Implement JWT Authentication as per https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/