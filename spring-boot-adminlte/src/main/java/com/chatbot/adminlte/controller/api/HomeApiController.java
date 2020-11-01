package com.chatbot.adminlte.controller.api;


import com.chatbot.adminlte.model.Accessory;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.model.api.AddToCartRequest;
import com.chatbot.adminlte.model.api.AddToCartViewModel;
import com.chatbot.adminlte.model.api.BaseApiResult;
import com.chatbot.adminlte.service.AccessoryService;
import com.chatbot.adminlte.service.CategoryService;
import com.chatbot.adminlte.service.ProductService;
import com.chatbot.adminlte.util.Constanst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class HomeApiController {
    @Autowired
    private ProductService productService;

    @Autowired
    private AccessoryService accessoryService;

    @PostMapping("/addtoCart")
    public BaseApiResult addToCart(HttpServletRequest request, HttpServletResponse response, @RequestBody AddToCartRequest addToCartRequest) {
        BaseApiResult result = new BaseApiResult();
        try {
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            addToCartRequest.setId(id);
            List<AddToCartRequest> listCart = (List<AddToCartRequest>) request.getSession().getAttribute("listCart");
            if (listCart == null) {
                listCart = new ArrayList<>();
            }
            listCart.add(addToCartRequest);
            request.getSession().setAttribute("listCart", listCart);

            final long[] totalPriceProduct = {0};
            final long[] totalPriceTopping = {0};
            long totalAmount = 0;
            listCart.forEach(c -> {
                Product product = productService.get(c.getProductId());
                if (!c.getAccessoryId().isEmpty()) {
                    List<Accessory> accessories = accessoryService.findByListAccessoryId(c.getAccessoryId());
                    if (!accessories.isEmpty()) {
                        accessories.forEach(a -> {
                            totalPriceTopping[0] += a.getPrice();
                        });

                    }
                }
                totalPriceProduct[0] += product.getPrice() * c.getQuantity();

            });
            totalAmount = totalPriceProduct[0] + totalPriceTopping[0];
            Map<String, Object> map = new HashMap<>();
            map.put("quantity", listCart.size());
            map.put("totalAmount", totalAmount);
            Integer quantitySS = (Integer) request.getSession().getAttribute("quantity");
            Integer totalAmountSS = (Integer) request.getSession().getAttribute("totalAmount");
            if (quantitySS == null) {
                request.getSession().setAttribute("quantity", listCart.size());
            } else {
                request.getSession().setAttribute("quantity", listCart.size());
            }
            if (totalAmountSS == null) {
                int total = (int) (totalAmount);
                request.getSession().setAttribute("totalAmount", total);
            } else {
                int total = (int) (totalAmount);
                request.getSession().setAttribute("totalAmount", total);
            }

            result.setErrorCode(Constanst.RESPONSE.SUCCESS.getCode());
            result.setErrorDescription(Constanst.RESPONSE.SUCCESS.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorCode(Constanst.RESPONSE.FAIL.getCode());
            result.setErrorDescription(e.getMessage());
        }
        return result;

    }


    @PostMapping("/removeToCart/{cartId}")
    public BaseApiResult removeToCart(HttpServletRequest request, HttpServletResponse response, @PathVariable("cartId") String cartId) {
        BaseApiResult result = new BaseApiResult();
        try {
            List<AddToCartRequest> listCart = (List<AddToCartRequest>) request.getSession().getAttribute("listCart");
            listCart = listCart.stream().filter(c -> !c.getId().equals(cartId)).collect(Collectors.toList());
            request.getSession().setAttribute("listCart", listCart);

            result.setErrorCode(Constanst.RESPONSE.SUCCESS.getCode());
            result.setErrorDescription(Constanst.RESPONSE.SUCCESS.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorCode(Constanst.RESPONSE.FAIL.getCode());
            result.setErrorDescription(e.getMessage());
        }
        return result;

    }


    @GetMapping("/clearSession")
    public BaseApiResult clearSession(HttpServletRequest request, HttpSession session) {
        BaseApiResult result = new BaseApiResult();
        session.invalidate();
        result.setErrorCode(Constanst.RESPONSE.SUCCESS.getCode());
        result.setErrorDescription(Constanst.RESPONSE.SUCCESS.getDescription());
        return result;
    }
}
