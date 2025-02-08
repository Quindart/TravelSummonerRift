package vn.edu.iuh.fit.userservice.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.edu.iuh.fit.userservice.entities.User;
import vn.edu.iuh.fit.userservice.enums.Role;
import vn.edu.iuh.fit.userservice.repositories.UserRepository;

@Configuration
public class AplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) { // Được khởi chạy mỗi khi application start
        return args -> {
            // tạo một user admin
            if (userRepository.findByUsername("admin").isEmpty()) {

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(user);
            }
        };
    }
}
