package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;


    @GetMapping("/home")
    public String home (@RequestParam(value = "mid",required = false) String msid, Model model){
        List<Product> list = productService.findWithOutStatusDelete();
        model.addAttribute("list",list);
        return "webchatbot";
    }
}
