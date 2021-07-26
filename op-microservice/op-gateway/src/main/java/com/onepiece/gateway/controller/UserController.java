package com.onepiece.gateway.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * UserInfo Endpoint
 *
 * @author andy
 */
@RequestMapping("oauth2/userinfo")
@RestController
public class UserController {

    @GetMapping
    public Principal userInfo(Principal principal) {
        return principal;
    }

    @GetMapping("/getCurrentUser")
    public void getCurrentUser(Authentication authentication) {
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
