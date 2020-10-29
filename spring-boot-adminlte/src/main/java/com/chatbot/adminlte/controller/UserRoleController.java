package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.model.User;
import com.chatbot.adminlte.model.UserRole;
import com.chatbot.adminlte.service.RoleService;
import com.chatbot.adminlte.service.UserRoleService;
import com.chatbot.adminlte.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("userRole")
public class UserRoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/role-control/{id}")
    public String roleControl(@PathVariable long id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Optional<User> checkUser = userService.findById(id);
            model.addAttribute("name",checkUser.get().getUserName());
            List<Role> listRoleNotExist = roleService.findRoleNotExistOfUser(id);
            List<Role> listRoleExist = roleService.getRoleNameExistOfUser(null,id);
            model.addAttribute("listRoleNotExistOfUser", listRoleNotExist);
            model.addAttribute("listUserRoleById", listRoleExist);
            return "user-role/list";

        }
        return "redirect:/login";
    }

}
