package com.project.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Shipment.
 */
@Entity
@Table(name = "shipment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Person senderP;

    @OneToOne
    @JoinColumn(unique = true)
    private Vendor senderV;

    @OneToOne
    @JoinColumn(unique = true)
    private ReceiverInfo receiver;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee deliverEmployee;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee contactEmployee;
    @OneToOne
    @JoinColumn(unique = true)
    private Status status;

    @OneToOne
    @JoinColumn(unique = true)
    private Product product;

    @OneToOne
    @JoinColumn(unique = true)
    private WarehouseLocation location;

   public Shipment()
   {}
    public Shipment(Person senderP, Vendor senderV, ReceiverInfo receiver, Employee deliverEmployee, Employee contactEmployee, Status status, Product product, WarehouseLocation location) {
        this.senderP = senderP;
        this.senderV = senderV;
        this.receiver = receiver;
        this.deliverEmployee = deliverEmployee;
        this.contactEmployee = contactEmployee;
        this.status = status;
        this.product = product;
        this.location = location;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getSenderP() {
        return senderP;
    }

    public Shipment senderP(Person person) {
        this.senderP = person;
        return this;
    }

    public void setSenderP(Person person) {
        this.senderP = person;
    }

    public Vendor getSenderV() {
        return senderV;
    }

    public Shipment senderV(Vendor vendor) {
        this.senderV = vendor;
        return this;
    }

    public void setSenderV(Vendor vendor) {
        this.senderV = vendor;
    }

    public ReceiverInfo getReceiver() {
        return receiver;
    }

    public Shipment receiver(ReceiverInfo receiverInfo) {
        this.receiver = receiverInfo;
        return this;
    }

    public void setReceiver(ReceiverInfo receiverInfo) {
        this.receiver = receiverInfo;
    }

    public Employee getDeliverEmployee() {
        return deliverEmployee;
    }

    public void setDeliverEmployee(Employee deliverEmployee) {
        this.deliverEmployee = deliverEmployee;
    }

    public Employee getContactEmployee() {
        return contactEmployee;
    }

    public void setContactEmployee(Employee contactEmployee) {
        this.contactEmployee = contactEmployee;
    }

    public Status getStatus() {
        return status;
    }

    public Shipment status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public Shipment product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public WarehouseLocation getLocation() {
        return location;
    }

    public Shipment location(WarehouseLocation warehouseLocation) {
        this.location = warehouseLocation;
        return this;
    }

    public void setLocation(WarehouseLocation warehouseLocation) {
        this.location = warehouseLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shipment shipment = (Shipment) o;
        if (shipment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shipment{" +
            "id=" + id +
            ", senderP=" + senderP +
            ", senderV=" + senderV +
            ", receiver=" + receiver +
            ", deliverEmployee=" + deliverEmployee +
            ", contactEmployee=" + contactEmployee +
            ", status=" + status +
            ", product=" + product +
            ", location=" + location +
            '}';
    }
}

