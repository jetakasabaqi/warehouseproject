package com.project.service.dto;

public class LoyalClientsDTO {


    private String name;
    private String tel;
    private String address;
    private Long numberOfPacksSended;

    public LoyalClientsDTO() {
    }

    public LoyalClientsDTO(String name, String tel, String address, Long numberOfPacksSended) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.numberOfPacksSended = numberOfPacksSended;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getNumberOfPacksSended() {
        return numberOfPacksSended;
    }

    public void setNumberOfPacksSended(Long numberOfPacksSended) {
        this.numberOfPacksSended = numberOfPacksSended;
    }

    @Override
    public String toString() {
        return "Client:" +
            "name='" + name + '\'' +
            ", tel='" + tel + '\'' +
            ", address='" + address + '\'' +
            ", numberOfPacksSended=" + numberOfPacksSended
            ;
    }
}
