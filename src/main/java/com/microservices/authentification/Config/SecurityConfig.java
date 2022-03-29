package com.microservices.authentification.Config;

import com.microservices.authentification.Entity.AppUser;
import com.microservices.authentification.Filters.AuthentificationFilter;
import com.microservices.authentification.Filters.JWTauthorizationFilter;
import com.microservices.authentification.Service.AuthServicesImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private AuthServicesImp authServicesImp;
    public SecurityConfig(AuthServicesImp authServicesImp) {
        this.authServicesImp = authServicesImp;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();
        http.formLogin();
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/user/addUser").permitAll();
        http.authorizeRequests().antMatchers("/user/addRoleToUser").permitAll();
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        //desactiver les sessions gérer par spring sécurity
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //ajouter un filtre
        http.addFilter(new AuthentificationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JWTauthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //userDetialsService pour indiquer à spring sécurity comment il doit faire l'authentification
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                AppUser appUser= authServicesImp.findByUsername(username);
                Collection<GrantedAuthority> roles=new ArrayList<>();
                appUser.getAppRoles().forEach(ru->{
                    roles.add(new SimpleGrantedAuthority(ru.getRolename()));
                });
                //on utilise l'object User de spring pour stocker les utilisateur et leur roles
                return new User(appUser.getUsername(),appUser.getPassword(),roles);
            }
        });


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
