package com.utsavj.journalApp.service;
import static org.mockito.Mockito.*;
import com.utsavj.journalApp.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockitoBean
    private UserRepo userRepo;

    @Test
    void testLoadUserByUsername(){
        when(userRepo.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn((com.utsavj.journalApp.entity.User) User.builder().username("utsav").password("utsav").build());
        UserDetails testUser = userDetailsServiceImpl.loadUserByUsername("utsav");
    }
}
