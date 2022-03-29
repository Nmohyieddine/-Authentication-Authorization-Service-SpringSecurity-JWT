package com.microservices.authentification.Repository;

import com.microservices.authentification.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<AppUser,Long> {

    public AppUser findAppUserByUsername(String userName);
}

