package com.noscale.noscale_motocare.models;

/**
 * Created by kurniawanrizzki on 20/01/18.
 */

public class Service {

    private int id;
    private String serviceName;
    private int serviceWeight;
    private int servicePrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceWeight() {
        return serviceWeight;
    }

    public void setServiceWeight(int serviceWeight) {
        this.serviceWeight = serviceWeight;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }
}
