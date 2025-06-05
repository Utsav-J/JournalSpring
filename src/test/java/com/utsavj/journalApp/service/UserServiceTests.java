package com.utsavj.journalApp.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.utsavj.journalApp.repository.UserRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepo userRepo;

    @Disabled
    @Test
    public void testFindUserByUsername(){
        assertNotNull(userRepo.findByUsername("utsav"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,1,3"
    })
    public void testAddition(int a, int b, int expected){
        assertEquals(expected, a + b);
    }
}
