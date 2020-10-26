package com.chatbot.adminlte;

import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.model.User;
import com.chatbot.adminlte.repository.RoleRepository;
import com.chatbot.adminlte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
public class SistemApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userService;


	public static void main(String[] args) {
		SpringApplication.run(SistemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUserName("longph");
		user.setFullName("Pham Huy Long");
		user.setEmail("h.long195@gmail.com");
		user.setPassword(passwordEncoder.encode("123456"));
		HashSet<Role> role = new HashSet<>();
		role.add(roleRepository.findByName("ROLE_USER"));
		role.add(roleRepository.findByName("ROLE_ADMIN"));
		user.setRoles(role);
		userService.save(user);

	}
}
