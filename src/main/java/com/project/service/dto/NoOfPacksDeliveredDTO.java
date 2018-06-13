package com.project.service.dto;

public class NoOfPacksDeliveredDTO {

    private long noOfPacksDelivered ;

    public NoOfPacksDeliveredDTO()
    {}

    public NoOfPacksDeliveredDTO(long noOfPacksDelivered) {
        this.noOfPacksDelivered = noOfPacksDelivered;
    }

    public long getNumber_of_Packs_delivered() {
        return noOfPacksDelivered;
    }

    public void setNumber_of_Packs_delivered(int noOfPacksDelivered) {
        this.noOfPacksDelivered = noOfPacksDelivered;
    }

    @Override
    public String toString() {
        return "NoOfPacksDeliveredDTO{" +
            "Number_of_Packs_delivered=" + noOfPacksDelivered +
            '}';
    }
}
