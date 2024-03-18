package com.newidea.payment.persistence.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="sellers")
public class SellerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String sellerName;


    public SellerEntity(Long id, String sellerName) {
        this.id = id;
        this.sellerName = sellerName;
    }

    public SellerEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return sellerName;
    }

    public void setName(String name) {
        this.sellerName = name;
    }
}
