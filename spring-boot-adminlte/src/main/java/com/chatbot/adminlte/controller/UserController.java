package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Customers;
import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.model.User;
import com.chatbot.adminlte.service.CustomersService;
import com.chatbot.adminlte.service.RoleService;
import com.chatbot.adminlte.service.UserService;
import com.chatbot.adminlte.util.Constanst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashSet;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public String index() {
        return "redirect:/user/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        Page<User> page = userService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "user/list";

    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("user", new User());
        return "user/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute("user", userService.get(id));
        return "user/edit";

    }
    @PostMapping(value = "/edit")
    public String editPost(User user, final RedirectAttributes ra) {
        User checkUser =  userService.findByUserName(user.getUserName());
        if(checkUser != null ){
            checkUser.setLastUpdate(new Date());
            checkUser.setStatus(Constanst.STATUS.ACTIVE.getCode());
            checkUser.setFullName(user.getFullName());
            checkUser.setEmail(user.getEmail());
            User save = userService.save(checkUser);
            ra.addFlashAttribute("successFlash", "Sửa nhân viên  thành công.");
        }else {
            ra.addFlashAttribute("errorFlash", "Username không tồn tại");
        }

        return "redirect:/user";

    }



    @PostMapping(value = "/save")
    public String save(User user, final RedirectAttributes ra) {
        User checkUser =  userService.findByUserName(user.getUserName());
        if(checkUser == null ){
            user.setCreateDate(new Date());
            user.setStatus(Constanst.STATUS.ACTIVE.getCode());
            user.setPassword(passwordEncoder.encode("123456"));
            HashSet<Role> role = new HashSet<>();
            role.add(roleService.findByName("ROLE_USER"));
            user.setRoles(role);
            User save = userService.save(user);
            ra.addFlashAttribute("successFlash", "Thêm nhân viên  thành công.");
        }else {
            ra.addFlashAttribute("errorFlash", "Username đã tồn tại");
        }

        return "redirect:/user";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        User user = userService.get(id);
        user.setStatus(Constanst.STATUS.DELETE.getCode());
        user.setLastUpdate(new Date());
        User delete = userService.save(user);
        return "redirect:/user";

    }

}
