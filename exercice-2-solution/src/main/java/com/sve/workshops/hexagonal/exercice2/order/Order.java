package com.sve.workshops.hexagonal.exercice2.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    public String id;

    public String username;
    public String status;
}
