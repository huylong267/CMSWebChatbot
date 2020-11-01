package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Accessory;
import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.model.api.AddToCartViewModel;
import com.chatbot.adminlte.model.api.AddToCartRequest;
import com.chatbot.adminlte.model.api.ProductCategoryViewModel;
import com.chatbot.adminlte.service.AccessoryService;
import com.chatbot.adminlte.service.CategoryService;
import com.chatbot.adminlte.service.ProductService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccessoryService accessoryService;


    @GetMapping("/home")
    public String home(@RequestParam(value = "mid", required = false) String msid, HttpServletRequest request, Model model) {

        List<Category> listCategory = categoryService.findWithOutStatusDelete();
        List<ProductCategoryViewModel> listViewModel = new ArrayList<>();
        listCategory.forEach(c -> {
            ProductCategoryViewModel vm = new ProductCategoryViewModel();
            List<Product> list = productService.findByCategoryAndStatus(c.getId());
            vm.setCategoryName(c.getName());
            vm.setListProduct(list);
            listViewModel.add(vm);

        });
        Integer quantitySS = (Integer) request.getSession().getAttribute("quantity");
        Integer totalAmountSS = (Integer) request.getSession().getAttribute("totalAmount");
        if(quantitySS == null){
            request.getSession().setAttribute("quantity",0);
            model.addAttribute("quantity",0);
        }else {
            request.getSession().setAttribute("quantity",quantitySS);
            model.addAttribute("quantity",quantitySS);
        }
        if(totalAmountSS == null){
            request.getSession().setAttribute("totalAmount",0);
            model.addAttribute("totalAmount",0);
        }else {
            request.getSession().setAttribute("totalAmount",totalAmountSS);
            model.addAttribute("totalAmount",totalAmountSS);
        }
        model.addAttribute("listViewModel", listViewModel);

        return "webchatbot";
    }


    @GetMapping("/addToCart")
    public String addToCart(HttpServletRequest request, Model model) {
        List<AddToCartRequest> listCart = (List<AddToCartRequest>) request.getSession().getAttribute("listCart");
        if (listCart == null) {
            model.addAttribute("msg", "Giỏ hàng trống");
        } else {
            List<AddToCartViewModel> cartViewModels = new ArrayList<>();
            final long[] totalPriceProduct = {0};
            final long[] totalPriceTopping = {0};
            long totalAmount = 0;
            listCart.forEach(c -> {
                AddToCartViewModel vm = new AddToCartViewModel();
                vm.setId(c.getId());
                Product product = productService.get(c.getProductId());
                if (!c.getAccessoryId().isEmpty()) {
                    List<Accessory> accessories = accessoryService.findByListAccessoryId(c.getAccessoryId());
                    if (!accessories.isEmpty()) {
                        final String[] accessoryName = {""};
                        accessories.forEach(a -> {
                            accessoryName[0] += ", " + a.getName();
                            totalPriceTopping[0] += a.getPrice();
                        });

                        vm.setAccessory(accessoryName[0].substring(1));
                    }
                }
                vm.setProduct(product);
                vm.setQuantity(c.getQuantity());
                vm.setTotalToppingPrice(totalPriceTopping[0]);
                totalPriceProduct[0] += product.getPrice() * c.getQuantity();
                cartViewModels.add(vm);
            });
            totalAmount = totalPriceProduct[0] + totalPriceTopping[0];
            model.addAttribute("list", cartViewModels);
            model.addAttribute("totalAmount", totalAmount);
        }
        return "addtocart";
    }
}
