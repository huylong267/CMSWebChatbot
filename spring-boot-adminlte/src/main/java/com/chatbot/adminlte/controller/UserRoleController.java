package com.chatbot.adminlte.controller;

import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.model.User;
import com.chatbot.adminlte.model.UserRole;
import com.chatbot.adminlte.service.RoleService;
import com.chatbot.adminlte.service.UserRoleService;
import com.chatbot.adminlte.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String roleControl(@PathVariable int id, Model model) {
        Optional<User> checkUser =  userService.findById(id);
        List<Role> listAll = roleService.findAll();
        List<Role> listRoleNotExistOfUser = roleService.findRoleNotExistOfUser(id);
        List<UserRole> listUserRoleById = userRoleService.findRoleIdByUserId(checkUser.get().getId());
        for (UserRole userRole:listUserRoleById) {
            for (Role role:listAll) {
                if(userRole.getRoleId().equals(role.getId())){
                    userRole.setRole(role.getName());
                }
            }
        }

        checkUser.ifPresent(user -> model.addAttribute("name", user.getUserName()));
        checkUser.ifPresent(user -> model.addAttribute("id", user.getId()));
        model.addAttribute("listRoleNotExistOfUser", listRoleNotExistOfUser);
        model.addAttribute("listUserRoleById", listUserRoleById);
        return "user-role/list";

    }

}
