package com.noscale.noscale_motocare.models;

import java.util.List;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class Garage {

    public int id;
    public String name;
    public String address;
    public double lat;
    public double lng;
    public String description;
    public String photo;
    public int sessionOne;
    public int sessionTwo;
    public List<Service> serviceList;
    public List<Day> days;
    public int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getSessionOne() {
        return sessionOne;
    }

    public void setSessionOne(int sessionOne) {
        this.sessionOne = sessionOne;
    }

    public int getSessionTwo() {
        return sessionTwo;
    }

    public void setSessionTwo(int sessionTwo) {
        this.sessionTwo = sessionTwo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
