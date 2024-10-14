package com.sailing.moviebooking.config;

import com.sailing.moviebooking.constant.PredefinedRole;
import com.sailing.moviebooking.model.Role;
import com.sailing.moviebooking.model.User;
import com.sailing.moviebooking.repository.RoleRepository;
import com.sailing.moviebooking.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;


@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                roleRepository.save(Role.builder()
                                .roleName(PredefinedRole.USER_ROLE)
                                .description("User Role")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                                .roleName(PredefinedRole.ADMIN_ROLE)
                                .description("Admin Role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("12345678"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Admin account has been create with default password: 12345678, please change it");
            }
        };
    }
}
