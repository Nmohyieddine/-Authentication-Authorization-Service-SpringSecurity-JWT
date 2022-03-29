package com.microservices.authentification;

import com.microservices.authentification.Entity.AppRole;
import com.microservices.authentification.Entity.AppUser;
import com.microservices.authentification.Repository.RoleRepository;
import com.microservices.authentification.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class AuthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthentificationApplication.class, args);
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(RoleRepository roleRepository, UserRepository userRepository){
        return args -> {

            roleRepository.save(new AppRole(null,"USER"));
            roleRepository.save(new AppRole(null,"ADMIN"));
            roleRepository.save(new AppRole(null,"CONSUMER"));


            userRepository.save(new AppUser(null,"Mohyi eddine","Ibn&sina2021",null));
            userRepository.save(new AppUser(null,"Saad","SaadMacbook",null));


        };
    }

}
