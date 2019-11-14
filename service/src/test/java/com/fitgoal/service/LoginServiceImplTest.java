package com.fitgoal.service;

import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.api.exceptions.IncorrectEmailOrPasswordException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.fitgoal.service.util.ServiceTestHelper.createUserDto;
import static com.fitgoal.service.util.ServiceTestHelper.createUserLoginData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    public void loginUser_whenUserExists_assertContentNotNull() {
        UserDto testUserDto = createUserDto();
        UserLoginData testUserLoginData = createUserLoginData();

        Mockito.when(userDao.findByEmail(testUserLoginData.getEmail()))
                .thenReturn(Optional.of(testUserDto));

        User receivedUser = loginService.login(testUserLoginData);

        assertThat(receivedUser).isNotNull();
        assertThat(receivedUser.getEmail()).isEqualTo(testUserDto.getEmail());
    }

    @Test
    public void loginUser_whenWrongEmail_throwIncorrectEmailOrPasswordException() {
        UserLoginData testUserLoginData = createUserLoginData();
        testUserLoginData.setEmail("wrongEmail@gmail.com");

        Mockito.when(userDao.findByEmail(testUserLoginData.getEmail()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginService.login(testUserLoginData))
                .isInstanceOf(IncorrectEmailOrPasswordException.class);
    }

    @Test
    public void loginUser_whenWrongPassword_throwIncorrectEmailOrPasswordException() {
        UserDto testUserDto = createUserDto();
        UserLoginData testUserLoginData = createUserLoginData();
        testUserLoginData.setPassword("wrongPass");

        Mockito.when(userDao.findByEmail(testUserLoginData.getEmail()))
                .thenReturn(Optional.of(testUserDto));

        assertThatThrownBy(() -> loginService.login(testUserLoginData))
                .isInstanceOf(IncorrectEmailOrPasswordException.class);
    }
}