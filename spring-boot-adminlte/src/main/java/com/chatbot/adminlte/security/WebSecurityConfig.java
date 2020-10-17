package com.chatbot.adminlte.security;

import com.chatbot.adminlte.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bootstrap/**", "/dist/**", "/plugins/**","/link/**","/api/**").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/home/**").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/dashboard").hasAnyRole("ADMIN", "USER")
                .antMatchers("/role/**").hasRole( "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error").successHandler(authenticationSuccessHandler)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .sessionManagement().sessionFixation().newSession()
                .sessionAuthenticationErrorUrl("/login")
                .invalidSessionUrl("/login")
                .maximumSessions(-1) // Number of concurrent session
                .expiredUrl("/login");

    }

}
