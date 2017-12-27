package com.noscale.noscale_motocare.models;

/**
 * Created by kurniawanrrizki on 27/12/17.
 */

public class Schedule {

    private int id;
    private String garageName;
    private int serviceType;
    private long serviceDate;
    private int status;

    public Schedule () {}

    public Schedule (int id, String garageName, int serviceType, long serviceDate, int status) {
        this.id = id;
        this.garageName = garageName;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public long getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(long serviceDate) {
        this.serviceDate = serviceDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
