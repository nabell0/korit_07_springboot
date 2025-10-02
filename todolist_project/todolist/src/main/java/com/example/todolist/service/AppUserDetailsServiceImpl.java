package com.example.todolist.service;


import com.example.todolist.domain.AppUser;
import com.example.todolist.domain.AppUserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Optional<> 을 사용하면 null 대신 Optional<null>과 유사한 형태가 return 되기 때문에 NullPointerException이 발생하지 않음 -> 그러면 예외처리 로직을 안써도 된다.
        Optional<AppUser> user = appUserRepository.findByUsername(username);

        //
        UserBuilder builder = null;
        if(user.isPresent()) {
            AppUser currentUser = user.get(); // user 자체는 Optional 자료형이지 AppUser가 아님.
            builder = withUsername(username);
            builder.password(currentUser.getPassword()).roles(currentUser.getRole());
        }else{
            throw new UsernameNotFoundException("User Not Found");
        }
        return builder.build();


    }
}
