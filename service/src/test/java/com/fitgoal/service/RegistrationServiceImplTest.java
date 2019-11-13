package com.fitgoal.service;

import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserRegistrationData;
import com.fitgoal.api.exceptions.UserAlreadyExistException;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    public void registerUser_whenUserNotExists_verifySaveInDB() {
        UserRegistrationData testUserRegistrationData = createUserRegistrationData();

        when(userDao.findByEmail(testUserRegistrationData.getEmail()))
                .thenReturn(Optional.empty());

        registrationService.register(testUserRegistrationData);

        verify(userDao, times(1)).save(any(UserDto.class));
    }

    @Test
    public void registerUser_whenUserExists_throwUserAlreadyExistException() {
        UserRegistrationData testUserRegistrationData = createUserRegistrationData();
        UserDto testUserDto = createUserDto();

        when(userDao.findByEmail(testUserRegistrationData.getEmail()))
                .thenReturn(Optional.of(testUserDto));

        assertThatThrownBy(() -> registrationService.register(testUserRegistrationData))
                .isInstanceOf(UserAlreadyExistException.class);
    }

    @Test
    public void activateUser_whenUserLinkValid_verifyUserUpdatedInDB() {
        UserDto testUserDto = createUserDto();
        String link = testUserDto.getLink();

        when(userDao.findByLink(link))
                .thenReturn(Optional.of(testUserDto));
        when(userDao.update(testUserDto)).thenReturn(testUserDto);

        User actualUser = registrationService.activateUser(link);

        assertThat(actualUser).isNotNull();
        verify(userDao, times(1)).update(any(UserDto.class));
    }

    @Test
    public void activateUser_whenUserLinkExpired_throwUserNotFoundException() {
        String link = "wrongLink";

        when(userDao.findByLink(link)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.activateUser(link))
                .isInstanceOf(UserNotFoundException.class);
    }
}