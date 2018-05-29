package com.project.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "weight_unit")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeightUnit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name="unit")
    private String unit;

    public WeightUnit()
    {}

    public WeightUnit(Long id, String unit) {
        this.id = id;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightUnit that = (WeightUnit) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, unit);
    }

    @Override
    public String toString() {
        return "WeightUnit{" +
            "id=" + id +
            ", unit='" + unit + '\'' +
            '}';
    }
}

