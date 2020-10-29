package com.chatbot.adminlte.controller.api;


import com.chatbot.adminlte.model.api.AddToCartRequest;
import com.chatbot.adminlte.model.api.BaseApiResult;
import com.chatbot.adminlte.util.Constanst;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeApiController {

    @PostMapping("/addtoCart")
    public BaseApiResult addToCart(HttpServletRequest request, @RequestBody AddToCartRequest addToCartRequest) {
        BaseApiResult result = new BaseApiResult();
        try {
            List<AddToCartRequest> listCart = (List<AddToCartRequest>) request.getSession().getAttribute("cart");
            if(listCart == null){
                listCart = new ArrayList<>();
            }
            listCart.add(addToCartRequest);
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
}
