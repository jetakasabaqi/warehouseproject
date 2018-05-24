package com.project.service.dto;

import com.project.domain.Shipment;

import java.math.BigDecimal;
import java.util.Objects;

public class PackageDTO {

    private Long productId;

    private  Long statusId;

    private Long receiverId;

    private Long priceId;

    private String statusName;

    private String receiverFullName;

    private String receiverAddress;

    private String receiverZipCode;

    private BigDecimal price;

    public PackageDTO(Shipment shipment){
        this.productId=shipment.getProduct().getId();
        this.statusId=shipment.getStatus().getId();
        this.receiverId=shipment.getReceiver().getId();
        this.priceId=shipment.getProduct().getPrice().getId();
        this.statusName=shipment.getStatus().getStatusName();
        this.receiverFullName=shipment.getReceiver().getFullName();
        this.receiverAddress=shipment.getReceiver().getAddress();
        this.receiverZipCode=shipment.getReceiver().getZipCode();
    }
    public  PackageDTO(Long productId,Long statusId,long receiverId,long priceId,String statusName, String receiverFullName, String receiverAddress, String receiverZipCode,BigDecimal price)
    {
        this.productId=productId;
        this.statusId=statusId;
        this.receiverId=receiverId;
        this.priceId=priceId;
        this.statusName=statusName;
        this.receiverFullName=receiverFullName;
        this.receiverAddress=receiverAddress;
        this.receiverZipCode=receiverZipCode;
        this.price=price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

//    @Override
//    public String toString() {
//        return "PackageDTO{" +
//            "productId=" + productId +
//            ", statusId=" + statusId +
//            ", receiverId=" + receiverId +
//            ", priceId=" + priceId +
//            ", statusName='" + statusName + '\'' +
//            ", receiverFullName='" + receiverFullName + '\'' +
//            ", receiverAddress='" + receiverAddress + '\'' +
//            ", receiverZipCode='" + receiverZipCode + '\'' +
//            ", price=" + price +
//            '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PackageDTO that = (PackageDTO) o;
//        return Objects.equals(productId, that.productId) &&
//            Objects.equals(statusId, that.statusId) &&
//            Objects.equals(receiverId, that.receiverId) &&
//            Objects.equals(priceId, that.priceId) &&
//            Objects.equals(statusName, that.statusName) &&
//            Objects.equals(receiverFullName, that.receiverFullName) &&
//            Objects.equals(receiverAddress, that.receiverAddress) &&
//            Objects.equals(receiverZipCode, that.receiverZipCode) &&
//            Objects.equals(price, that.price);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(productId, statusId, receiverId, priceId, statusName, receiverFullName, receiverAddress, receiverZipCode, price);
//    }
}
