package mx.pedraza.kubernetes_api.models;

import lombok.Data;

/**
 * 
 */
@Data
public class FibonacciJobRequestModel {
    private int concurrency;
    private int requests;
}