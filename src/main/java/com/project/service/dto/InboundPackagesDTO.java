package com.project.service.dto;

import java.math.BigDecimal;

public class InboundPackagesDTO {

    private String senderName;
    private String senderEmail;

    private String receiverName;
    private String receiverAddress;

    private String deliverEmployeeName;
    private String deliverEmployeeTel;

    private String contactEmployeeName;
    private String contactEmployeeTel;

    private Long statusId;
    private String statusName;

    private Long productId;

    private BigDecimal price;

    private Long locationId;

    private String productType;



    public InboundPackagesDTO(String senderName, String senderEmail, String receiverName, String receiverAddress, String deliverEmployeeName, String deliverEmployeeTel, String contactEmployeeName, String contactEmployeeTel, Long statusId, String statusName, Long productId, BigDecimal price, Long locationId, String type) {
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.deliverEmployeeName = deliverEmployeeName;
        this.deliverEmployeeTel = deliverEmployeeTel;
        this.contactEmployeeName = contactEmployeeName;
        this.contactEmployeeTel = contactEmployeeTel;
        this.statusId = statusId;
        this.statusName = statusName;
        this.productId = productId;
        this.price = price;
        this.locationId = locationId;
        this.productType=type;
    }




    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getDeliverEmployeeName() {
        return deliverEmployeeName;
    }

    public void setDeliverEmployeeName(String deliverEmployeeName) {
        this.deliverEmployeeName = deliverEmployeeName;
    }

    public String getDeliverEmployeeTel() {
        return deliverEmployeeTel;
    }

    public void setDeliverEmployeeTel(String deliverEmployeeTel) {
        this.deliverEmployeeTel = deliverEmployeeTel;
    }

    public String getContactEmployeeName() {
        return contactEmployeeName;
    }

    public void setContactEmployeeName(String contactEmployeeName) {
        this.contactEmployeeName = contactEmployeeName;
    }

    public String getContactEmployeeTel() {
        return contactEmployeeTel;
    }

    public void setContactEmployeeTel(String contactEmployeeTel) {
        this.contactEmployeeTel = contactEmployeeTel;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "InboundPackagesDTO{" +
            "senderName='" + senderName + '\'' +
            ", senderEmail='" + senderEmail + '\'' +
            ", receiverName='" + receiverName + '\'' +
            ", receiverAddress='" + receiverAddress + '\'' +
            ", deliverEmployeeName='" + deliverEmployeeName + '\'' +
            ", deliverEmployeeTel='" + deliverEmployeeTel + '\'' +
            ", contactEmployeeName='" + contactEmployeeName + '\'' +
            ", contactEmployeeTel='" + contactEmployeeTel + '\'' +
            ", statusId=" + statusId +
            ", statusName='" + statusName + '\'' +
            ", productId=" + productId +
            ", productType='" + productType + '\'' +
            ", price=" + price +
            ", locationId=" + locationId +
            '}';
    }
}
