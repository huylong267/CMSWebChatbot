package com.chatbot.adminlte.controller.api;

import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.model.User;
import com.chatbot.adminlte.model.api.BaseApiResult;
import com.chatbot.adminlte.model.api.SetUserRoleRequest;
import com.chatbot.adminlte.repository.RoleRepository;
import com.chatbot.adminlte.service.RoleService;
import com.chatbot.adminlte.service.UserRoleService;
import com.chatbot.adminlte.service.UserService;
import com.chatbot.adminlte.util.Constanst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/role")
public class UserRoleApiController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping(value = "/update/set-role")
    public BaseApiResult updateSetRole(@RequestBody SetUserRoleRequest request) {
        BaseApiResult apiResponse = new BaseApiResult();
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                User user = userService.findByUserName(request.getUserName());
                // xoa cac role  k chon
                boolean flagDelete = roleService.deleteRoleExist( request.getArr(),  user.getId());
                if (flagDelete) {
                    List<Role> roleNameListExist = roleService.getRoleNameExistOfUser(request.getArr(),  user.getId());
                    for (int i = 0; i < request.getArr().size(); i++) {
                        // check role nao can add
                        boolean added = false;
                        for (Role role : roleNameListExist) {
                            if (role.getName().equals(request.getArr().get(i))) {
                                added = true;
                                break;
                            }
                            if (!added) {
                                boolean flagInsert = userRoleService.insertUserRoleWithRoleName(request.getArr().get(i), user.getId());
                                if (flagInsert) {
                                    apiResponse.setErrorCode(Constanst.RESPONSE.SUCCESS.getCode());
                                    apiResponse.setErrorDescription(Constanst.RESPONSE.SUCCESS.getDescription());
                                } else {
                                    apiResponse.setErrorCode(Constanst.RESPONSE.FAIL.getCode());
                                    apiResponse.setErrorDescription(Constanst.RESPONSE.FAIL.getDescription());
                                }
                            }
                        }
                    }
                } else {
                    apiResponse.setErrorCode(Constanst.RESPONSE.FAIL.getCode());
                    apiResponse.setErrorDescription(Constanst.RESPONSE.FAIL.getDescription());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            apiResponse.setErrorCode(Constanst.RESPONSE.FAIL.getCode());
            apiResponse.setErrorDescription(e.getMessage());
        }
        return apiResponse;
    }
}
