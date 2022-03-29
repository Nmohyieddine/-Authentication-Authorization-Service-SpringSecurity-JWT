package com.microservices.authentification.Projection;

import com.microservices.authentification.Entity.AppRole;
import com.microservices.authentification.Entity.AppUser;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "fulluser" ,types = AppUser.class)
public interface UserProjection {

    Long getId();
    String getUsername();
    Collection<AppRole> getAppRoles();


}
