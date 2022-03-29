package com.microservices.authentification.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JWTauthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String AuthorizationToken=request.getHeader("authorization");
        if(AuthorizationToken!=null && AuthorizationToken.startsWith("Bearer ")){

            try {

                String jwt=AuthorizationToken.substring(7);
                Algorithm algorithm=Algorithm.HMAC256("Ibn&sina2021");
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT=jwtVerifier.verify(jwt);
                String username=decodedJWT.getSubject();
                String[] roles=decodedJWT.getClaim("roles").asArray(String.class);

                Collection<GrantedAuthority> grantedAuthorities=new ArrayList<>();
                for(String rl:roles){
                    grantedAuthorities.add(new SimpleGrantedAuthority(rl));
                }

                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(username,null,grantedAuthorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request,response);
            }catch (Exception e){
                response.setHeader("error",e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);

            }
        }

        else {

            filterChain.doFilter(request,response);

        }




    }
}
