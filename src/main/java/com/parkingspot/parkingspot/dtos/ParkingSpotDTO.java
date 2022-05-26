package com.parkingspot.parkingspot.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ParkingSpotDTO {

    private long id;
    @NotBlank
    private String parkingNumber;
    @NotBlank
    @Size(max=10)
    private String carNumber;
    @NotBlank
    private String owner;
    @NotBlank
    private String localization;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    @Override
    public String toString() {
        return "ParkingSpotDTO{" +
                "id=" + id +
                ", parkingNumber='" + parkingNumber + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", owner='" + owner + '\'' +
                ", localization='" + localization + '\'' +
                '}';
    }
}
