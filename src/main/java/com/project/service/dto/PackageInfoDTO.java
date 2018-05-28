package com.project.service.dto;

import java.math.BigDecimal;

public class PackageInfoDTO {


    private Long productId;

    private BigDecimal price;

    private Long deliver_employeeId;
    private String deliver_employeeName;
    private String deliver_employeeEmail;
    private String deliverEmployee_tel;

    private Long contact_employeeId;
    private String contact_employeeName;
    private String contact_employeeEmail;
    private String contact_employeeTel;


    public PackageInfoDTO(Long productId, BigDecimal price, Long deliver_employeeId, String deliver_employeeName, String deliver_employeeEmail, String deliverEmployee_tel, Long contact_employeeId, String contact_employeeName, String contact_employeeEmail, String contact_employeeTel) {
        this.productId = productId;
        this.price = price;
        this.deliver_employeeId = deliver_employeeId;
        this.deliver_employeeName = deliver_employeeName;
        this.deliver_employeeEmail = deliver_employeeEmail;
        this.deliverEmployee_tel = deliverEmployee_tel;
        this.contact_employeeId = contact_employeeId;
        this.contact_employeeName = contact_employeeName;
        this.contact_employeeEmail = contact_employeeEmail;
        this.contact_employeeTel = contact_employeeTel;
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

    public Long getDeliver_employeeId() {
        return deliver_employeeId;
    }

    public void setDeliver_employeeId(Long deliver_employeeId) {
        this.deliver_employeeId = deliver_employeeId;
    }

    public String getDeliver_employeeName() {
        return deliver_employeeName;
    }

    public void setDeliver_employeeName(String deliver_employeeName) {
        this.deliver_employeeName = deliver_employeeName;
    }

    public String getDeliver_employeeEmail() {
        return deliver_employeeEmail;
    }

    public void setDeliver_employeeEmail(String deliver_employeeEmail) {
        this.deliver_employeeEmail = deliver_employeeEmail;
    }

    public String getDeliverEmployee_tel() {
        return deliverEmployee_tel;
    }

    public void setDeliverEmployee_tel(String deliverEmployee_tel) {
        this.deliverEmployee_tel = deliverEmployee_tel;
    }

    public Long getContact_employeeId() {
        return contact_employeeId;
    }

    public void setContact_employeeId(Long contact_employeeId) {
        this.contact_employeeId = contact_employeeId;
    }

    public String getContact_employeeName() {
        return contact_employeeName;
    }

    public void setContact_employeeName(String contact_employeeName) {
        this.contact_employeeName = contact_employeeName;
    }

    public String getContact_employeeEmail() {
        return contact_employeeEmail;
    }

    public void setContact_employeeEmail(String contact_employeeEmail) {
        this.contact_employeeEmail = contact_employeeEmail;
    }

    public String getContact_employeeTel() {
        return contact_employeeTel;
    }

    public void setContact_employeeTel(String contact_employeeTel) {
        this.contact_employeeTel = contact_employeeTel;
    }

    @Override
    public String toString() {
        return "PackageInfoDTO{" +
            "productId=" + productId +
            ", price=" + price +
            ", deliver_employeeId=" + deliver_employeeId +
            ", deliver_employeeName='" + deliver_employeeName + '\'' +
            ", deliver_employeeEmail='" + deliver_employeeEmail + '\'' +
            ", deliverEmployee_tel='" + deliverEmployee_tel + '\'' +
            ", contact_employeeId=" + contact_employeeId +
            ", contact_employeeName='" + contact_employeeName + '\'' +
            ", contact_employeeEmail='" + contact_employeeEmail + '\'' +
            ", contact_employeeTel='" + contact_employeeTel + '\'' +
            '}';
    }
}
