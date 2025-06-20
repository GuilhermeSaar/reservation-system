package com.gstech.reservationSystem.security;

import com.gstech.reservationSystem.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;


    private String recoveryToken(HttpServletRequest request) {

        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoveryToken(request);

        if(token != null) {
            var optionalEmail = this.jwtService.validateToken(token);

            if(optionalEmail.isPresent()) {
                var email = optionalEmail.get();
                var optionalUser = userRepository.findByEmail(email);

                if(optionalUser.isPresent()) {
                    var user = optionalUser.get();

                    var authentication = new UsernamePasswordAuthenticationToken(user,
                            null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
