package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Customers;
import com.chatbot.adminlte.service.CategoryService;
import com.chatbot.adminlte.service.CustomersService;
import com.chatbot.adminlte.util.Constanst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@RequestMapping("category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index() {
        return "redirect:/category/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        Page<Category> page = categoryService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "category/list";

    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("category", new Category());
        return "category/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute("category", categoryService.get(id));
        return "category/form";

    }

    @PostMapping(value = "/save")
    public String save(Category category, final RedirectAttributes ra) {
        category.setCreateDate(new Date());
        category.setStatus(Constanst.STATUS.ACTIVE.getCode());
        Category save = categoryService.save(category);
        ra.addFlashAttribute("successFlash", "Thêm danh mục thành công.");
        return "redirect:/category";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Category category = categoryService.get(id);
        category.setStatus(Constanst.STATUS.DELETE.getCode());
        category.setLastUpdate(new Date());
        Category delete = categoryService.save(category);
        return "redirect:/category";

    }

}
