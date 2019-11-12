package com.fitgoal.service;

import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserRegistrationData;
import com.fitgoal.api.exceptions.UserAlreadyExistException;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.fitgoal.service.util.TestHelper.createUserDto;
import static com.fitgoal.service.util.TestHelper.createUserRegistrationData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest {

    @Mock
    private UserDao userDao;

    private RegistrationService registrationService;

    @Before
    public void setUp() {
        registrationService = new RegistrationServiceImpl(userDao);
    }

    @Test
    public void whenRegisterNewUser_thenShouldSaveItToDB() {
        UserRegistrationData testUserRegistrationData = createUserRegistrationData();

        when(userDao.findByEmail(testUserRegistrationData.getEmail()))
                .thenReturn(Optional.empty());

        registrationService.register(testUserRegistrationData);

        verify(userDao, times(1)).save(any(UserDto.class));
    }

    @Test(expected = UserAlreadyExistException.class)
    public void whenRegisterExistingUser_thenShouldThrowUserAlreadyExistException() {
        UserRegistrationData testUserRegistrationData = createUserRegistrationData();
        UserDto testUserDto = createUserDto();

        when(userDao.findByEmail(testUserRegistrationData.getEmail()))
                .thenReturn(Optional.of(testUserDto));

        registrationService.register(testUserRegistrationData);
        assertThatThrownBy(() -> new UserAlreadyExistException(testUserRegistrationData.getEmail()));
    }

    @Test
    public void whenActivateUser_thenShouldReturnUserAndUpdateUserInDB() {
        UserDto testUserDto = createUserDto();
        String link = testUserDto.getLink();

        when(userDao.findByLink(link))
                .thenReturn(Optional.of(testUserDto));
        when(userDao.update(testUserDto)).thenReturn(testUserDto);

        User actualUser = registrationService.activateUser(link);

        assertThat(actualUser).isNotNull();
        verify(userDao, times(1)).update(any(UserDto.class));
    }

    @Test(expected = UserNotFoundException.class)
    public void whenActivateUserByExpiredLink_thenShouldThrowUserNotFoundException() {
        String link = "wrongLink";

        when(userDao.findByLink(link)).thenReturn(Optional.empty());

        User user = registrationService.activateUser(link);

        assertThat(user).isNull();
        assertThatThrownBy(() -> new UserNotFoundException("link expired"));

    }
}