package com.chatbot.adminlte.model.api;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class AddToCartRequest implements Serializable {
    private long productId;
    private List<Long> accessoryId;
    private long quantity;
    private long amount;

}
