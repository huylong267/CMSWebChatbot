package com.chatbot.adminlte.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends AbstractModel<Long> {

    private String name;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="id_Product")
    private List<Product> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
