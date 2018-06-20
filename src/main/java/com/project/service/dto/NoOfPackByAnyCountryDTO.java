package com.project.service.dto;


public class NoOfPackByAnyCountryDTO {

    private long noOfPacksDelivered;

    private String country;

    public NoOfPackByAnyCountryDTO() {
    }

    public NoOfPackByAnyCountryDTO(long noOfPacksDelivered, String country) {
        this.noOfPacksDelivered = noOfPacksDelivered;
        this.country = country;
    }

    public long getNoOfPacksDelivered() {
        return noOfPacksDelivered;
    }

    public void setNoOfPacksDelivered(long noOfPacksDelivered) {
        this.noOfPacksDelivered = noOfPacksDelivered;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return
            "noOfPacksDelivered=" + noOfPacksDelivered +
                ", country='" + country + '\''
            ;
    }
}
