# kubernetes-api

Provides access to the Kubernetes API server to retrieve cluster metrics.

## Usage

To execute this service, deploy it to a web server and execute a `curl` to the different endpoints exposed by `/api/fibonacci-job` and `/api/cluster`.

### Fibonacci Job Endpoint

The Fibonacci Job endpoint `/api/fibonacci-job` allows to get information about a cluster autoscaler test job that will call the `fibonacci` service programatically. The information exposed is:

* `/api/fibonacci-job/count`: Determines whether the job is currently running or not.
* `/api/fibonacci-job/parameters`: Gets the parameters used to execute the fibonacci job.

### Cluster Enpoint

The Cluster endpoint `/api/cluster` allows to get information about the current machines joined to the Kubernets cluster. The information exposed is:

* `/api/cluster/machine-count`: Gets the number of current machines running in the Kubernetes cluster.
* `/api/cluster/status`: Gets the CPU and memory metrics of all the machines running in the Kubernetes cluster.
* `/api/cluster/hpa-status`: Gets the CPU and memory metrics of the `fibonacci` Horizontal Pod Autoscaler.

### Example
```
# Gets the metrics of the machines in the cluster.
curl http://url.of.this.service/api/cluster/status

# Creates a new fibonacci job to start the cluster autoscaler test.
curl -vX PUT http://url.of.this.service/api/fibonacci-job -d '{"requests":"5000","concurrency":"10"}' --header "Content-Type: application/json"

# Deletes the fibonacci job, hence stopping the cluster autoscaler test.
curl -vX DELETE http://url.of.this.service/api/fibonacci-job
```

## Kubernetes API

Altough the above operations could be performed by the Kubernetes API server by itself, accessing the API service directly through its REST endpoints requires to get a token and expose the Kubernetes API server to the Internet, which is not desirable.

The solution was to create this Java Spring Boot project to call the Kubernetes API Server programatically (using `kubectl`) and to only expose the required resources through the use of the Kubernetes RBAC.

There are several RBAC objects created specifically to grant access only to the resources exposed by this service. A Kubernetes Service Account is created and a Role and a RoleBinding objects are created for this purpose as well. Check the Helm project to see the details about these RBAC objects and the permissions they grant to this service.

## Further Development

* Implement JWT Authentication as per https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/