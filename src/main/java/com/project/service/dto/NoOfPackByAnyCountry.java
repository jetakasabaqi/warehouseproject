package com.project.service.dto;



public class NoOfPackByAnyCountry {

    private long noOfPacksDelivered ;

    private String country;

    public NoOfPackByAnyCountry()
    {}

    public NoOfPackByAnyCountry(long noOfPacksDelivered, String country) {
        this.noOfPacksDelivered = noOfPacksDelivered;
        this.country=country;
    }

    public long getNumber_of_Packs_delivered() {
        return noOfPacksDelivered;
    }

    public void setNumber_of_Packs_delivered(int noOfPacksDelivered) {
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
