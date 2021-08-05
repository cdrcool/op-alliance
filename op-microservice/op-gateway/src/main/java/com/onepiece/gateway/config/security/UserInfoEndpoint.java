package com.onepiece.gateway.config.security;

import com.onepiece.gateway.response.ApiResponse;
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
@RequestMapping("/oauth/userInfo")
@RestController
public class UserInfoEndpoint {

    @GetMapping
    public ApiResponse<Principal> userInfo(Principal principal) {
        return ApiResponse.<Principal>builder().data(principal).build();
    }

    @GetMapping("/currentUser")
    public void getCurrentUser(Authentication authentication) {
    }
}


