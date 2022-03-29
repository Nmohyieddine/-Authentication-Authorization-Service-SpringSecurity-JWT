package com.microservices.authentification.Service;

import com.microservices.authentification.Entity.AppRole;
import com.microservices.authentification.Entity.AppUser;
import com.microservices.authentification.Repository.RoleRepository;
import com.microservices.authentification.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServicesImplementation implements AuthServicesImp {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;


    public AuthServicesImplementation(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        String password=appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(appUser);

    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return roleRepository.save(appRole);
    }

    @Override
    public AppUser addRoleToUser(String userName, String roleName) {
            AppUser user=userRepository.findAppUserByUsername(userName);
            AppRole role=roleRepository.findAppRoleByRolename(roleName);
            user.getAppRoles().add(role);
            userRepository.save(user);
            return user;
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findAppUserByUsername(username);
    }

    @Override
    public List<AppUser> getAllUser() {
        return userRepository.findAll();
    }
}
