package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class UsersServiceImplTest {
    private UsersRepository usersRepositoryMock = Mockito.mock(UsersRepository.class);
    private UsersServiceImpl usersServiceImpl = new UsersServiceImpl(usersRepositoryMock);
    @Test
    public void UsersServiceImplAuthenticateSuccess() {
        User user = new User(1L, "login1", "password1");
        user.setAuthenticated(false);

        Mockito.when(usersRepositoryMock.findByLogin("login1")).thenReturn(user);

        boolean auth = usersServiceImpl.authenticate("login1", "password1");

        Mockito.verify(usersRepositoryMock, Mockito.times(1)).update(user);

        assertTrue(auth);
    }

    @Test
    public void UsersServiceImplAuthenticateWrongLogin() {
        User user = new User(1L, "login1", "password1");
        user.setAuthenticated(false);

        Mockito.when(usersRepositoryMock.findByLogin("login1")).thenReturn(null);

        boolean auth = usersServiceImpl.authenticate("login1", "password1");

        assertFalse(auth);
    }

    @Test
    public void UsersServiceImplAuthenticateWrongPassword() {
        User user = new User(1L, "login1", "password1");

        user.setAuthenticated(false);

        Mockito.when(usersRepositoryMock.findByLogin("login1")).thenReturn(user);

        boolean auth = usersServiceImpl.authenticate("login1", "password2");

        assertFalse(auth);
    }

    @Test
    public void UsersServiceImplAuthenticateAlreadyAuth() {
        User user = new User(1L, "login1", "password1");
        user.setAuthenticated(true);

        Mockito.when(usersRepositoryMock.findByLogin("login1")).thenReturn(user);

        assertThrows(AlreadyAuthenticatedException.class,
                () -> usersServiceImpl.authenticate("login1", "password2"));
    }

}
