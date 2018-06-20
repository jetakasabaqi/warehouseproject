package com.project.service.dto;

import java.math.BigDecimal;

public class PackageDTO {

    private Long productId;

    private Long statusId;

    private Long receiverId;

    private Long priceId;

    private String statusName;

    private String receiverFullName;

    private String receiverAddress;

    private String receiverZipCode;

    //product details

    private double productWidth;

    private double productLength;

    private double productHeight;

    private double productWeight;

    private String productWeightUnit;

    private String productType;


    private BigDecimal price;


    public PackageDTO(Long productId, Long statusId, Long receiverId, Long priceId, String statusName, String receiverFullName, String receiverAddress, String receiverZipCode, double productWidth, double productLength, double productHeight, double productWeight, String productWeightUnit, String productType, BigDecimal price) {
        this.productId = productId;
        this.statusId = statusId;
        this.receiverId = receiverId;
        this.priceId = priceId;
        this.statusName = statusName;
        this.receiverFullName = receiverFullName;
        this.receiverAddress = receiverAddress;
        this.receiverZipCode = receiverZipCode;
        this.productWidth = productWidth;
        this.productLength = productLength;
        this.productHeight = productHeight;
        this.productWeight = productWeight;
        this.productWeightUnit = productWeightUnit;
        this.productType = productType;
        this.price = price;
    }

    public PackageDTO(Long productId, Long statusId, long receiverId, long priceId, String statusName, String receiverFullName, String receiverAddress, String receiverZipCode, BigDecimal price) {
        this.productId = productId;
        this.statusId = statusId;
        this.receiverId = receiverId;
        this.priceId = priceId;
        this.statusName = statusName;
        this.receiverFullName = receiverFullName;
        this.receiverAddress = receiverAddress;
        this.receiverZipCode = receiverZipCode;
        this.price = price;

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public void setReceiverFullName(String receiverFullName) {
        this.receiverFullName = receiverFullName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZipCode() {
        return receiverZipCode;
    }

    public void setReceiverZipCode(String receiverZipCode) {
        this.receiverZipCode = receiverZipCode;
    }

    public double getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(double productWidth) {
        this.productWidth = productWidth;
    }

    public double getProductLength() {
        return productLength;
    }

    public void setProductLength(double productLength) {
        this.productLength = productLength;
    }

    public double getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(double productHeight) {
        this.productHeight = productHeight;
    }

    public double getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }

    public String getProductWeightUnit() {
        return productWeightUnit;
    }

    public void setProductWeightUnit(String productWeightUnit) {
        this.productWeightUnit = productWeightUnit;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
