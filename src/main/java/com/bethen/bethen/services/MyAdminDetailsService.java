package com.bethen.bethen.services;

import com.bethen.bethen.models.AdminModel;
import com.bethen.bethen.repos.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyAdminDetailsService  implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminModel adminModel =  adminRepo.findAdminByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("Invalid credentials") );

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+adminModel.getRole());

        return new User(adminModel.getEmail(),
                        adminModel.getPassword(),
                Collections.singletonList(authority)
                );

    }
}
