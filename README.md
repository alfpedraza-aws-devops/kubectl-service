# kubernetes-api

Provides access to the Kubernetes API server to retrieve cluster metrics.

## Usage

To execute this service, deploy it to a web server and execute a `curl` to the different endpoints exposed by `/api/fibonacci-job` and `/api/nodes`.

### Fibonacci Job Endpoint

The Fibonacci Job endpoint `/api/fibonacci-job` allows to get information about a load test job that will call the `fibonacci` service programatically. The information exposed is:

* `/api/fibonacci-job/count`: Determines whether the job is currently running or not.
* `/api/fibonacci-job/status`: Gets the CPU and memory metric used by the `fibonacci` service and the number of pods executing and desired.
* `/api/fibonacci-job/parameters`: Gets the parameters used to execute the job.

### Nodes Enpoint

The Nodes endpoint `/api/nodes` allows to get information about the current nodes joined to the Kubernets cluster. The information exposed is:

* `/api/nodes/count`: Gets the number of current nodes running in the cluster.
* `/api/nodes/status`: Gets the CPU and memory metrics of all the nodes running in the Kubernetes cluster.

### Example
```
# Gets the metrics of the nodes in the cluster.
curl http://url.of.this.service/api/nodes/status

# Creates a new fibonacci job to start the load test.
curl -vX PUT http://url.of.this.service/api/fibonacci-job -d '{"requests":"5000","concurrency":"10"}' --header "Content-Type: application/json"

# Deletes the fibonacci job stopping the load test.
curl -vX DELETE http://url.of.this.service/api/fibonacci-job
```

## Kubernetes API

Altough the above operations could be performed by the Kubernetes API server by itself, accessing the API service directly through its REST endpoints requires to get a token and expose the Kubernetes API server to the Internet which is not convenient.

The solution was to create this Java Spring Boot project to call the Kubernetes API Server programatically (using `kubectl`) and to only expose the required access thorough the use of the Kubernetes RBAC.

Along with this Spring Boot application, there are several RBAC objects that were created specifically to grant access only to the resources exposed here. A Kubernetes service account is created and a Role and a RoleBinding objects are created for this purpose as well. Check the Helm project to see the details of these RBAC objects and the permissions that they grant to this service.

