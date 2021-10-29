package com.example.demo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.demo.model.User;
import com.example.demo.model.UserDetailsImpl;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        // User basicUser = new UserImpl("Basic User", "user@company.com", "password");
        // UserActive basicActiveUser = new UserActive(basicUser, Arrays.asList(
        // new SimpleGrantedAuthority("ROLE_USER"),
        // new SimpleGrantedAuthority("PERM_FOO_READ")
        // ));

        UserDetails managerUser = new UserDetailsImpl(new User(1L, "Gaby", "aaaa", "ADMIN"));

        return new InMemoryUserDetailsManager(managerUser);
    }

    // @Bean
    // @Primary
    // public UserDetailsService userDetailsService() {
    // GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
    // UserDetails userDetails = new User("Gaby", "aaaa", Arrays.asList(authority));
    // return new InMemoryUserDetailsManager(Arrays.asList(userDetails));
    // }

    // @Bean
    // @Primary
    // public UserDetailsService userDetailsService() {
    // User basicUser = new User(1L, "Gaby", "aaaa", "ADMIN");
    // UserDetailsImpl basicActiveUser = new UserDetailsImpl(basicUser, Arrays.asList(
    // new SimpleGrantedAuthority("ROLE_USER"),
    // new SimpleGrantedAuthority("PERM_FOO_READ")
    // ));
//
    // User managerUser = new UserImpl("Manager User", "manager@company.com", "password");
    // UserActive managerActiveUser = new UserActive(managerUser, Arrays.asList(
    // new SimpleGrantedAuthority("ROLE_MANAGER"),
    // new SimpleGrantedAuthority("PERM_FOO_READ"),
    // new SimpleGrantedAuthority("PERM_FOO_WRITE"),
    // new SimpleGrantedAuthority("PERM_FOO_MANAGE")
    // ));
//
    // return new InMemoryUserDetailsManager(Arrays.asList(
    // basicActiveUser, managerActiveUser
    // ));
    // }
}
