package com.project.service.dto;

import java.math.BigDecimal;

public class PackageStatusDTO {



    private Long productId;

    private Long priceId;

    private Long statusId;

    private String statusName;

    private BigDecimal price;


    public PackageStatusDTO(Long productId, Long priceId, Long statusId, String statusName, BigDecimal price) {
        this.productId = productId;
        this.priceId = priceId;
        this.statusId = statusId;
        this.statusName = statusName;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
