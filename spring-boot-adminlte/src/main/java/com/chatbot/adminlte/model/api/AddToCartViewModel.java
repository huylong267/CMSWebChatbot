package com.chatbot.adminlte.model.api;

import com.chatbot.adminlte.model.Accessory;
import com.chatbot.adminlte.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
public class AddToCartViewModel implements Serializable {
    private String id;
    private Product product;
    private String accessory;
    private int quantity;
    private long totalToppingPrice;
}
