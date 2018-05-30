package com.project.domain;

import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "product_type")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private  Long id;


    @Column(name="type")
    private String type;

    public ProductType()
    {}

    public ProductType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType that = (ProductType) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "ProductType{" +
            "id=" + id +
            ", type='" + type + '\'' +
            '}';
    }

    public ProductType type(String envelope) {
        this.type=type;
        return this;
    }
}
