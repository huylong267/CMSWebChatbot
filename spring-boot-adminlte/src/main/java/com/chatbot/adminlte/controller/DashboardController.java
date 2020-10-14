package com.chatbot.adminlte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String test(){
        return "redirect:/login";
    }
    @GetMapping("/dashboard")
    public String dashboard(HttpSession httpSession){

//            System.out.println(((UserDetails) principal).getUsername());
//            System.out.println(((UserDetails) principal).getAuthorities());

        return "dashboard/index";

    }

}
