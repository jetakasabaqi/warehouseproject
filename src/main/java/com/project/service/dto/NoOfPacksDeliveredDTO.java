package com.project.service.dto;

public class NoOfPacksDeliveredDTO {

    private long Number_of_Packs_delivered ;

    public NoOfPacksDeliveredDTO()
    {}

    public NoOfPacksDeliveredDTO(long number_of_Packs_delivered) {
        Number_of_Packs_delivered = number_of_Packs_delivered;
    }

    public long getNumber_of_Packs_delivered() {
        return Number_of_Packs_delivered;
    }

    public void setNumber_of_Packs_delivered(int number_of_Packs_delivered) {
        Number_of_Packs_delivered = number_of_Packs_delivered;
    }

    @Override
    public String toString() {
        return "NoOfPacksDeliveredDTO{" +
            "Number_of_Packs_delivered=" + Number_of_Packs_delivered +
            '}';
    }
}
