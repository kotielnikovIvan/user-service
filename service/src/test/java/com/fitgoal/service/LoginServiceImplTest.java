package com.fitgoal.service;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.api.exceptions.IncorrectEmailOrPasswordException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.fitgoal.service.util.TestHelper.createUserDto;
import static com.fitgoal.service.util.TestHelper.createUserLoginData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

    @Mock
    private UserDao userDao;

    private LoginService loginService;

    @Before
    public void setUp() {
        loginService = new LoginServiceImpl(userDao);
    }

    @Test
    public void whenLoginUserWithCorrectEmailAndPassword_thenReturnUser() {
        UserDto testUserDto = createUserDto();
        UserLoginData testUserLoginData = createUserLoginData();

        Mockito.when(userDao.findByEmail(testUserLoginData.getEmail()))
                .thenReturn(Optional.of(testUserDto));

        User receivedUser = loginService.login(testUserLoginData);

        assertThat(receivedUser).isNotNull();
        assertThat(receivedUser.getEmail()).isEqualTo(testUserDto.getEmail());
    }

    @Test(expected = IncorrectEmailOrPasswordException.class)
    public void whenLoginUserWithIncorrectEmail_thenThrowIncorrectEmailOrPasswordException() {
        UserLoginData testUserLoginData = createUserLoginData();
        testUserLoginData.setEmail("wrongEmail@gmail.com");

        Mockito.when(userDao.findByEmail(testUserLoginData.getEmail()))
                .thenReturn(Optional.empty());

        loginService.login(testUserLoginData);

        assertThatThrownBy(IncorrectEmailOrPasswordException::new);
    }

    @Test(expected = IncorrectEmailOrPasswordException.class)
    public void whenLoginUserWithIncorrectPassword_thenThrowIncorrectEmailOrPasswordException() {
        UserDto testUserDto = createUserDto();
        UserLoginData testUserLoginData = createUserLoginData();
        testUserLoginData.setPassword("wrongPass");

        Mockito.when(userDao.findByEmail(testUserLoginData.getEmail()))
                .thenReturn(Optional.of(testUserDto));

        loginService.login(testUserLoginData);

        assertThatThrownBy(IncorrectEmailOrPasswordException::new);
    }
}