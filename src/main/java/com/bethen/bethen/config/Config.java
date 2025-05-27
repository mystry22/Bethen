package com.bethen.bethen.config;

import com.bethen.bethen.security.JwtFilter;
import com.bethen.bethen.security.JwtFilterTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class Config {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    public Config(AuthenticationEntryPoint authenticationEntryPoint){
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private  JwtFilterTwo jwtFilterTwo;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       return  http
                .authorizeHttpRequests(authorize -> authorize
                                //No Auth for these requests
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //Permits OPTION suitable to deal with CORS
                                .requestMatchers("/api/v1/members/createNewMember").permitAll()
                                .requestMatchers("/api/v1/members/memberLogin").permitAll()
                                .requestMatchers("/api/v1/admin/createAdmin").permitAll()
                                .requestMatchers("/api/v1/admin/loginAdmin").permitAll()
                                .requestMatchers("/api/v1/transactions/webhook").permitAll()
                                .requestMatchers("/api/v1/transactions/activate").permitAll()
                        //Auth required endpoints
                        .requestMatchers("/api/v1/members/deleteMember/*","/api/v1/admin/getAllAdmin","/api/v1/admin/deleteAdmin/**").hasRole("ADMIN")
                    .requestMatchers("/api/v1/members/getAllMembers").hasRole("ADMIN")
                        .requestMatchers("/api/v1/members/getMemberById/**").permitAll()
                                .requestMatchers("/api/v1/members/secret").permitAll()
                        .requestMatchers("/api/v1/members/changePassword").hasRole("BASIC")
                                .requestMatchers("/api/v1/transactions/paymentLink").hasRole("BASIC")
                                .requestMatchers("/api/v1/transactions/allTransactions").hasRole("ADMIN")
                                .requestMatchers("/api/v1/members/userDetails").permitAll()
                    .anyRequest().authenticated()
                        // Require authentication for all other requests
        ).csrf(AbstractHttpConfigurer::disable)
               .httpBasic(Customizer.withDefaults())
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               //.addFilterBefore(jwtFilter, JwtFilterTwo.class)
               .build();



    }
}
