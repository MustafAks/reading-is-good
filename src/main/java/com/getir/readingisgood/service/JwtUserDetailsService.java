package com.getir.readingisgood.service;


import com.getir.readingisgood.exception.ReadingIsGoodApiException;
import com.getir.readingisgood.exception.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public static final String USER = "getir";
    public static final String PASSWORD = "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (USER.equals(username)) {
            return new User(USER, PASSWORD,
                    new ArrayList<>());
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.USER_NOT_FOUND);
        }
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
