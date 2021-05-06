package com.beta;

import com.beta.model.Role;
import com.beta.model.User;
import com.beta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(AppService appService, UserRepository userRepository) {
//        return args -> {
//
//            Role admin = new Role("ROLE_Admin");
//            Role user = new Role("ROLE_User");
//
//            User user1 = new User(
//                    "user1",
//                    "user1",
//                    11,
//                    "user1@mail",
//                    "login1",
//                    passwordEncoder.encode("pass1"));
//
//            User user2 = new User(
//                    "user2",
//                    "user2",
//                    22,
//                    "user2@mail",
//                    "login2",
//                    passwordEncoder.encode("pass2"));
//
//            User user3 = new User(
//                    "user3",
//                    "user3",
//                    33,
//                    "user3@mail",
//                    "login3",
//                    passwordEncoder.encode("pass3"));
//
//            User user4 = new User(
//                    "user4",
//                    "user4",
//                    44,
//                    "user4@mail",
//                    "login4",
//                    passwordEncoder.encode("pass4"));
//            user1.setRoles(List.of(admin, user));
//            user2.setRoles(List.of(admin));
//            user3.setRoles(List.of(user));
//            user4.setRoles(List.of(user));
//
//            userRepository.saveAll(List.of(user1, user2, user3, user4));
//
//        };
//    }
}
