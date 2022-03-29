package com.microservices.authentification.Controller;

import com.microservices.authentification.DTO.RoleUser;
import com.microservices.authentification.Entity.AppRole;
import com.microservices.authentification.Entity.AppUser;
import com.microservices.authentification.Service.AuthServicesImp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    AuthServicesImp authServicesImp;

    public UserController(AuthServicesImp authServicesImp) {
        this.authServicesImp = authServicesImp;
    }

    @PostMapping(path = "/addUser")
    public AppUser addUser(@RequestBody AppUser appUser){
        return authServicesImp.addNewUser(appUser);
    }

    @PostMapping(path = "/addRoleToUser")
    public AppUser AddRoleToUser(@RequestParam(name = "username" ) String username,@RequestBody RoleUser roleUser){
        return authServicesImp.addRoleToUser(username,roleUser.rolename);
    }

    @GetMapping(path = "/allUsers")
    public List<AppUser> GetAllUser(){
        return  authServicesImp.getAllUser();

    }
    @GetMapping(path = "/findUser")
    public AppUser GetUserByUsername(@RequestParam(name = "username") String username){

        return authServicesImp.findByUsername(username);

    }

    @PostMapping(path = "/role")
    public AppRole AddRole(@RequestBody AppRole appRole){
        return authServicesImp.addNewRole(appRole);

    }

}
