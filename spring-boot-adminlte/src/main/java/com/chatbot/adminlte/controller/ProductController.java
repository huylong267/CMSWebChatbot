package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.service.CategoryService;
import com.chatbot.adminlte.service.FileStorageService;
import com.chatbot.adminlte.service.ProductService;
import com.chatbot.adminlte.util.Constanst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FileStorageService fileStorageService;



    @GetMapping
    public String index() {
        return "redirect:/product/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        Page<Product> page = productService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "product/list";

    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryService.findAll());
        return "product/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute("product", productService.get(id));
        model.addAttribute("category", categoryService.findAll());
        return "product/form";

    }


    @PostMapping(value = "/save")
    public String save(@Valid @ModelAttribute("product") Product product, @RequestParam("file") MultipartFile imageFile, final RedirectAttributes ra) {
        if(imageFile == null ){
            return "error";
        }
        if(product.getCategory() == null ){
            return "error";
        }
        if(product.getId() == null ){
            Product productAdd = new Product();
            String newFilename = fileStorageService.store(imageFile);
            productAdd.setImg(Constanst.PREFIX_LINK_UPLOAD +newFilename);
            productAdd.setCreateDate(new Date());
            productAdd.setStatus(Constanst.STATUS.ACTIVE.getCode());
            productAdd.setName(product.getName());
            productAdd.setPrice(product.getPrice());
            Category category = categoryService.get(product.getCategory().getId());
            productAdd.setCategory(category);
            Product save = productService.save(productAdd);
        }else {
            Product productExist = productService.get(product.getId());
            if(imageFile != null){
                String newFilename = fileStorageService.store(imageFile);
                productExist.setImg(Constanst.PREFIX_LINK_UPLOAD +newFilename);
            }
            productExist.setName(product.getName());
            productExist.setPrice(product.getPrice());
            productExist.setLastUpdate(new Date());
            productService.save(productExist);
        }

        ra.addFlashAttribute("successFlash", "Thêm sản phẩm thành công.");
        return "redirect:/product";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Product product = productService.get(id);
        product.setStatus(Constanst.STATUS.DELETE.getCode());
        product.setLastUpdate(new Date());
        Product delete = productService.save(product);
        return "redirect:/product";

    }

}
