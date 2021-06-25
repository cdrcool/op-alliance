package com.op.authorization.config;

import com.op.authorization.feignclient.UserFeignClient;
import com.op.authorization.feignclient.dto.UserDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author cdrcool
 * @see UserDetailsService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserFeignClient userFeignClient;

    public UserDetailsServiceImpl(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userFeignClient.getByUsername(username);

        return new User(
                userDTO.getUsername(),
                userDTO.getPassword(),
                Objects.equals(userDTO.getStatus(), 1),
                !Objects.equals(userDTO.getStatus(), 2),
                !Objects.equals(userDTO.getStatus(), 4),
                !Objects.equals(userDTO.getStatus(), 3),
                userDTO.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}
