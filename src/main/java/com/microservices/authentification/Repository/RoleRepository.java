package com.microservices.authentification.Repository;

import com.microservices.authentification.Entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<AppRole,Long> {

    public AppRole findAppRoleByRolename(String roleName);
}
