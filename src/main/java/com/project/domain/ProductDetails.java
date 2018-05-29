package com.project.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn (name="product_id",referencedColumnName = "id")
    private Product product;


    @Column(name="width")
    private double width;

    @Column(name="length")
    private double length;

    @Column(name="height")
    private double height;


    @Column(name="weight")
    private double weight;


    @OneToOne
    @JoinColumn (name="weight_unit",referencedColumnName = "id")
    private WeightUnit weightUnit;

    @OneToOne
    @JoinColumn (name="product_type",referencedColumnName = "id")
    private ProductType type;

    public ProductDetails()
    {}


    public ProductDetails(Long id, Product product, double width, double length, double height, double weight, WeightUnit weightUnit, ProductType type) {
        this.id = id;
        this.product = product;
        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WeightUnit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetails that = (ProductDetails) o;
        return Double.compare(that.width, width) == 0 &&
            Double.compare(that.length, length) == 0 &&
            Double.compare(that.height, height) == 0 &&
            Double.compare(that.weight, weight) == 0 &&
            Objects.equals(id, that.id) &&
            Objects.equals(product, that.product) &&
            Objects.equals(weightUnit, that.weightUnit) &&
            Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, product, width, length, height, weight, weightUnit, type);
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
            "id=" + id +
            ", product=" + product +
            ", width=" + width +
            ", length=" + length +
            ", height=" + height +
            ", weight=" + weight +
            ", weightUnit=" + weightUnit +
            ", type=" + type +
            '}';
    }
}
