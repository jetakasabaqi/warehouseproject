package com.project.service.dto;

public class NoOfPacksDeliveredDTO {

    private long noOfPacksDelivered;

    public NoOfPacksDeliveredDTO() {
    }

    public NoOfPacksDeliveredDTO(long noOfPacksDelivered) {
        this.noOfPacksDelivered = noOfPacksDelivered;
    }

    public long getNoOfPacksDelivered() {
        return noOfPacksDelivered;
    }

    public void setNoOfPacksDelivered(long noOfPacksDelivered) {
        this.noOfPacksDelivered = noOfPacksDelivered;
    }

    @Override
    public String toString() {
        return "NoOfPacksDeliveredDTO{" +
            "noOfPacksDelivered=" + noOfPacksDelivered +
            '}';
    }
}
