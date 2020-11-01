package com.chatbot.adminlte.controller.api;

import com.chatbot.adminlte.model.Accessory;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.model.api.BaseApiResult;
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
import java.util.List;

@RestController
@RequestMapping("/api/accessory")
public class AccessoryApiController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping("findByProductId/{productId}")
    public BaseApiResult findByProductId(@PathVariable("productId") long productId){
        BaseApiResult  result = new BaseApiResult();
        try {
            List<Accessory> list = accessoryService.findByProducts_Id(productId);
            if (!list.isEmpty()) {
                result.setErrorCode(Constanst.RESPONSE.SUCCESS.getCode());
                result.setErrorDescription(Constanst.RESPONSE.SUCCESS.getDescription());
                result.setData(list);
            } else {
                Product product = productService.get(productId);
                if (product != null){
                    result.setData(product);
                    result.setErrorCode(Constanst.RESPONSE.SUCCESS_FIND_PRODUCTID.getCode());
                    result.setErrorDescription(Constanst.RESPONSE.SUCCESS_FIND_PRODUCTID.getDescription());
                }else {
                    result.setErrorCode(Constanst.RESPONSE.FAIL.getCode());
                    result.setErrorDescription(Constanst.RESPONSE.FAIL.getDescription());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrorCode(Constanst.RESPONSE.FAIL.getCode());
            result.setErrorDescription(e.getMessage());
        }
        return result;

    }


}
