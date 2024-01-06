package ru.myapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("paid_group")
public class PaidGroup extends Group {

    @Column(name = "cost")
    private Integer cost;

    public PaidGroup() {
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
