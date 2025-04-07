package com.bethen.bethen.services;

import com.bethen.bethen.models.MemberModel;
import com.bethen.bethen.repos.MembersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MembersRepo membersRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberModel memberModel = membersRepo.findByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException("Invalid credentials")
        );

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ memberModel.getRole());


        return new User(memberModel.getEmail(),
                memberModel.getPrivateKey(),
                Collections.singletonList(authority));



    }
}
