package com.project.service.dto;

public class NoOfPacksPendingDTO {

    private long numberOfPacksPending;

    private String countryDestination;


    public NoOfPacksPendingDTO() {
    }

    public NoOfPacksPendingDTO(long numberOfPacksPending, String countryDestination) {
        this.numberOfPacksPending = numberOfPacksPending;
        this.countryDestination = countryDestination;
    }

    public long getNumberOfPacksPending() {
        return numberOfPacksPending;
    }

    public void setNumberOfPacksPending(long numberOfPacksPending) {
        this.numberOfPacksPending = numberOfPacksPending;
    }

    public String getCountryDestination() {
        return countryDestination;
    }

    public void setCountryDestination(String countryDestination) {
        this.countryDestination = countryDestination;
    }

    @Override
    public String toString() {
        return
            "numberOfPacksPending=" + numberOfPacksPending +
                ", countryDestination='" + countryDestination + '\''
            ;
    }
}
