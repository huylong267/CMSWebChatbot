package com.chatbot.adminlte.model.api;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class AddToCartRequest implements Serializable {
    private final long serialVersionUID = 4261301955948640341L;
    private  String id;
    private long productId;
    private List<Long> accessoryId;
    private int quantity;
    private long amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public List<Long> getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(List<Long> accessoryId) {
        this.accessoryId = accessoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
