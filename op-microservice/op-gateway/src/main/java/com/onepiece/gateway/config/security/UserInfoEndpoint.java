package com.onepiece.gateway.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * UserInfo Endpoint
 *
 * @author cdrcool
 */
@RequestMapping("/oauth/userinfo")
@RestController
public class UserInfoEndpoint {

    @GetMapping
    public Principal userInfo(Principal principal) {
        return principal;
    }

    @GetMapping("/currentUser")
    public void getCurrentUser(Authentication authentication) {
    }
}

