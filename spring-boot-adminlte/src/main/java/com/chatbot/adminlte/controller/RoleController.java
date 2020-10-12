package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.service.CategoryService;
import com.chatbot.adminlte.service.RoleService;
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
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @GetMapping
    public String index() {
        return "redirect:/role/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        Page<Role> page = roleService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "role/list";

    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("role", new Category());
        return "role/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute("role", roleService.get(id));
        return "role/form";

    }

    @PostMapping(value = "/save")
    public String save(Role role, final RedirectAttributes ra) {
        role.setCreateDate(new Date());
        role.setStatus(Constanst.STATUS.ACTIVE.getCode());
        Role save = roleService.save(role);
        ra.addFlashAttribute("successFlash", "Thêm quyền thành công.");
        return "redirect:/role";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Role role = roleService.get(id);
        role.setStatus(Constanst.STATUS.DELETE.getCode());
        role.setLastUpdate(new Date());
        Role delete = roleService.save(role);
        return "redirect:/role";

    }

}
