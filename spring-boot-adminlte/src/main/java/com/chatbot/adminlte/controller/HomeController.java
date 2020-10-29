package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.model.api.ProductCategoryViewModel;
import com.chatbot.adminlte.service.CategoryService;
import com.chatbot.adminlte.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/home")
    public String home (@RequestParam(value = "mid",required = false) String msid, Model model){

        List<Category> listCategory = categoryService.findWithOutStatusDelete();
        List<ProductCategoryViewModel> listViewModel = new ArrayList<>();
        listCategory.forEach(c -> {
            ProductCategoryViewModel vm = new ProductCategoryViewModel();
            List<Product> list = productService.findByCategoryAndStatus(c.getId());
            vm.setCategoryName(c.getName());
            vm.setListProduct(list);
            listViewModel.add(vm);

        });
        model.addAttribute("listViewModel",listViewModel);
        return "webchatbot";
    }
}
