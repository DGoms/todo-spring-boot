package com.davidgomes.todospringboot.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.davidgomes.todospringboot.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserCache userCache = new NullUserCache();
    private final UserDetailsService userDetailsService;
    private final AppProperties appProperties;

//    private final UserDetailsServiceImpl userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, AppProperties appProperties, UserDetailsServiceImpl userDetailsService) {
        super(authManager);

        this.appProperties = appProperties;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(appProperties.getJwt().getHeader());

        if (header == null || !header.startsWith(appProperties.getJwt().getTokenPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(appProperties.getJwt().getHeader());
        if (token != null) {
            // parse the token.
            String userEmail = JWT.require(Algorithm.HMAC512(appProperties.getJwt().getSecret().getBytes()))
                    .build()
                    .verify(token.replace(appProperties.getJwt().getTokenPrefix(), ""))
                    .getSubject();

            if (userEmail != null) {
                UserDetails user = getUserByUsername(userEmail);

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            }
            return null;
        }
        return null;
    }

    private UserDetails getUserByUsername(String username) {
        UserDetails user = this.userCache.getUserFromCache(username);

        if (user == null) {
            user = userDetailsService.loadUserByUsername(username);

            if (user == null) {
                throw new AuthenticationServiceException("AuthenticationDao returned null, which is an interface contract violation");
            }

            logger.info("User loaded by DB");
            this.userCache.putUserInCache(user);

            return user;
        } else {
            logger.info("User loaded by cache");
        }

        return null;
    }
}