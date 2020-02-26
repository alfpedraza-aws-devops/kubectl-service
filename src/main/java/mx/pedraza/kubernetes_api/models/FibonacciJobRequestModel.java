package mx.pedraza.kubernetes_api.models;

import lombok.Data;

/**
 * Contains the parameters used to create a fibonacci job.
 */
@Data
public class FibonacciJobRequestModel {

    // Specifies the number of requests to perform at a time. 
    private int concurrency;

    // Specfies the number of requests to perform on the job.
    private int requests;
}