package com.chatbot.adminlte.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_accessory")
public class ProductAccessory extends AbstractModel<Long>{
    private long product_id;
    private long accessory_id;

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getAccessory_id() {
        return accessory_id;
    }

    public void setAccessory_id(long accessory_id) {
        this.accessory_id = accessory_id;
    }
}
