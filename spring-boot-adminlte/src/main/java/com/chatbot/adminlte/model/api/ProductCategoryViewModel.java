package com.chatbot.adminlte.model.api;

import com.chatbot.adminlte.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductCategoryViewModel {
    private String categoryName;
    private List<Product> listProduct;
}
