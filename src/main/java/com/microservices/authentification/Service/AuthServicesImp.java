package com.microservices.authentification.Service;

import com.microservices.authentification.Entity.AppRole;
import com.microservices.authentification.Entity.AppUser;

import java.util.List;

public interface AuthServicesImp {

    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    AppUser addRoleToUser(String userName,String roleName);
    AppUser findByUsername(String username);
    List<AppUser> getAllUser();

}
