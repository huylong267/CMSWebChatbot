package com.chatbot.adminlte.security;

import com.chatbot.adminlte.model.User;
import com.chatbot.adminlte.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    HttpSession session; //autowiring session

    @Autowired
    UserRepository repository; //autowire the user repo


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // TODO Auto-generated method stub
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String userName = ((UserDetails)principal).getUsername();
            User user = repository.findByUsername(userName);
            session.setAttribute("user", user);
            response.sendRedirect("/dashboard");
        }
        //HttpSession session = request.getSession();


    }

}
