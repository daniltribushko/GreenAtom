package com.example.GreenAtom.configs;

import com.example.GreenAtom.services.UserService;
import com.example.GreenAtom.services.imp.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Tribushko Danil
 *
 * Фильтер для jwt токена
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    public static final String BEARER_PREFIX = "Bearer";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Autowired
    public JwtAuthenticationFilter(UserService userService,
                                   JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(@NotNull
                                    HttpServletRequest request,
                                    @NotNull
                                    HttpServletResponse response,
                                    @NotNull
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeaderValue = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authHeaderValue == null || !authHeaderValue.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeaderValue.substring(BEARER_PREFIX.length() + 1);
        String username = jwtTokenService.getUserName(token);
        if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtTokenService.isTokenValid(token, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
