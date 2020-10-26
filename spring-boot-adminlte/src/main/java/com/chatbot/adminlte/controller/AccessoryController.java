package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Accessory;
import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.service.AccessoryService;
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
import java.util.HashSet;

@Controller
@RequestMapping("accessory")
public class AccessoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping
    public String index() {
        return "redirect:/accessory/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        Page<Accessory> page = accessoryService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "accessory/list";

    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("accessory", new Accessory());
        model.addAttribute("products", productService.findAll());

        return "accessory/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute("accessory", accessoryService.get(id));
        model.addAttribute("products", productService.findAll());
        return "accessory/form";

    }

    @PostMapping(value = "/save")
    public String save(@Valid @ModelAttribute("accessory") Accessory accessory, @RequestParam("file") MultipartFile imageFile, final RedirectAttributes ra) {
        if (imageFile == null) {
            return "error";
        }
        if(accessory.getId() == null ) {
            Accessory accessoryAdd = new Accessory();
            String newFilename = fileStorageService.store(imageFile);
            accessoryAdd.setImg(Constanst.PREFIX_LINK_UPLOAD + newFilename);
            accessoryAdd.setCreateDate(new Date());
            accessoryAdd.setStatus(Constanst.STATUS.ACTIVE.getCode());
            accessoryAdd.setName(accessory.getName());
            accessoryAdd.setPrice(accessory.getPrice());

            HashSet<Product> products = new HashSet<>();
            accessory.getProducts().forEach((e) -> {
                products.add(productService.get(e.getId()));
            });
            accessoryAdd.setProducts(products);
            Accessory save = accessoryService.save(accessoryAdd);
        }else {
            Accessory accExist = accessoryService.get(accessory.getId());
            if(imageFile != null){
                String newFilename = fileStorageService.store(imageFile);
                accExist.setImg(Constanst.PREFIX_LINK_UPLOAD +newFilename);
            }
            accExist.setName(accessory.getName());
            accExist.setPrice(accessory.getPrice());
            accExist.setLastUpdate(new Date());
            HashSet<Product> products = new HashSet<>();
            accessory.getProducts().forEach((e) -> {
                products.add(productService.get(e.getId()));
            });
            accExist.setProducts(products);
            Accessory save = accessoryService.save(accExist);
        }
        ra.addFlashAttribute("successFlash", "Thêm phụ kiện thành công.");
        return "redirect:/accessory";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Accessory accessory = accessoryService.get(id);
        accessory.setStatus(Constanst.STATUS.DELETE.getCode());
        accessory.setLastUpdate(new Date());
        Accessory delete = accessoryService.save(accessory);
        return "redirect:/accessory";

    }

}
