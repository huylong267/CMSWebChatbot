package com.chatbot.adminlte.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product extends AbstractModel<Long>{

    private String name;
    private String img;
    private long price;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name ="category_id")
    @JsonIgnore
    private Category category;


    @ManyToMany(mappedBy = "products")
    @JsonBackReference
    private Set<Accessory> accessories = new HashSet<>();

    public Set<Accessory> getAccessories() {
        return accessories;
    }

    public void setAccessories(Set<Accessory> accessories) {
        this.accessories = accessories;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
