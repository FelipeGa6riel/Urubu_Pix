package com.urubu.pix.infra.filter;

import com.urubu.pix.domain.user.User;
import com.urubu.pix.repositories.UserRepository;
import com.urubu.pix.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.getToken(request);
        if(token != null) {
            var cpf = tokenService.validateToken(token);

            UserDetails user = userRepository.findUsersByCpf(cpf);
            User id = userRepository.findUserByCpf(cpf).orElseThrow(()-> new RuntimeException("Permissão negada"));
            request.setAttribute("validateId",id.getId());
            var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    public String getToken(HttpServletRequest request) {
        var authToken = request.getHeader("Authorization");
        if(authToken == null) return null;
        return authToken.replace("Bearer ","");
    }
}
