package com.a1.insecureswe.security;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired private UserInfoRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userRes = userRepo.findByUsername(username);
        if(userRes == null)
            throw new UsernameNotFoundException("Could not findUser with username = " + username);
        return new org.springframework.security.core.userdetails.User(
                username,
                userRes.getPassword(),
                List.of(new SimpleGrantedAuthority(userRes.getRole())));
    }
}
