package ist.challenge.agil_syofian_hidayat.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ist.challenge.agil_syofian_hidayat.model.Users;
import ist.challenge.agil_syofian_hidayat.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

  @Mock private UsersRepository usersRepository;
  
  private UsersService testingUsersService;

  @BeforeEach
  void setUp(){
    testingUsersService = new UsersService(usersRepository);
  }

  @Test
  void testCanAddNewUsers() {
    String username = "john";
    String password = "123456abc";
    
    Users newUser = new Users(
      username,
      password
    );
    testingUsersService.addNewUsers(newUser);

    ArgumentCaptor<Users> usersArgumentCaptor = ArgumentCaptor.forClass(Users.class);

    verify(usersRepository).save(usersArgumentCaptor.capture());
    
    Users captureUsers = usersArgumentCaptor.getValue();

    assertEquals(captureUsers, captureUsers);
  }

  @Test
  void testCannotAddNewUsersEmptyUsernameOrPassword() {
    String username = "";
    String password = "";

    Users newUser = new Users(
            username,
            password
    );

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> testingUsersService.addNewUsers(newUser));
    assertEquals(thrown.getStatus(), HttpStatus.BAD_REQUEST);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Username dan / atau password kosong"));
  }

  @Test
  void testCannotAddNewUsersWithSameUsername() {
    String username = "john";
    String password = "123456abc";

    Users newUser = new Users(
            username,
            password
    );

    given(usersRepository.findUsersByUsername(newUser.getUsername())).willReturn(Optional.of(newUser));

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> testingUsersService.addNewUsers(newUser));
    assertEquals(thrown.getStatus(), HttpStatus.CONFLICT);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Username sudah terpakai"));
  }

  @Test
  void testGetUsers() {
    testingUsersService.getUsers();
    verify(usersRepository).findAll();
  }

  @Test
  void testCanLogin() {

    String username = "john";
    String password = "123456abc";

    Users newUser = new Users(
            username,
            password
    );

    when(usersRepository.findUsersByUsernamePassword(username, password)).thenReturn(Optional.of(newUser));

    final var testLoginUser = testingUsersService.login(newUser);

    assertEquals(testLoginUser.getUsername(), username);
    assertEquals(testLoginUser.getPassword(), password);
  }

  @Test
  void testLoginEmptyUsernamePassword() {
    String username = "";
    String password = "";

    Users newUser = new Users(
            username,
            password
    );

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> testingUsersService.login(newUser));
    assertEquals(thrown.getStatus(), HttpStatus.BAD_REQUEST);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Username dan / atau password kosong"));
  }

  @Test
  void testLoginWrongUsernamePassword() {
    String username = "john";
    String password = "password";

    Users login = new Users(
            username,
            password
    );

    when(usersRepository.findUsersByUsernamePassword(username, password)).thenReturn(Optional.empty());

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> testingUsersService.login(login));
    assertEquals(thrown.getStatus(), HttpStatus.UNAUTHORIZED);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("username / password tidak cocok"));
  }

  @Test
  void testUpdateUsersSuccess() {
    Users users = new Users(
            1L,
            "john",
            "123456abc"
    );

    Users updateData = new Users(
            "john update",
            "abc123456"
    );

    when(usersRepository.findById(users.getId())).thenReturn(Optional.of(users));

    final var updateUsers = testingUsersService.updateUsers(users.getId(), updateData.getUsername(), updateData.getPassword());

    assertEquals(updateUsers.getUsername(), updateData.getUsername());
    assertEquals(updateUsers.getPassword(), updateData.getPassword());
  }

  @Test
  void testUpdateUsersUserIdNotFound() {
    Users users = new Users(
            1L,
            "john",
            "123456abc"
    );

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> testingUsersService.updateUsers(users.getId(), users.getUsername(), users.getPassword()));
    assertEquals(thrown.getStatus(), HttpStatus.BAD_REQUEST);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("userId tidak ditemukan"));
  }

  @Test
  void testUpdateUsersUsernameExist() {

    Users otherUsers = new Users(
            1L,
            "john",
            "123456abc"
    );

    Users updateData = new Users(
            2L,
            "john doe",
            "abc1234546"
    );

    when(usersRepository.findById(updateData.getId())).thenReturn(Optional.of(updateData));
    when(usersRepository.findUsersByUsername("john")).thenReturn(Optional.of(otherUsers));

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> testingUsersService.updateUsers(updateData.getId(), "john", updateData.getPassword()));
    assertEquals(thrown.getStatus(), HttpStatus.CONFLICT);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Username sudah terpakai"));
  }

  @Test
  void testUpdateUsersPasswordNotValid() {

    Users updateData = new Users(
            1L,
            "john doe",
            "abc1234546"
    );

    when(usersRepository.findById(updateData.getId())).thenReturn(Optional.of(updateData));

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> testingUsersService.updateUsers(updateData.getId(), updateData.getUsername(), updateData.getPassword()));
    assertEquals(thrown.getStatus(), HttpStatus.BAD_REQUEST);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Password tidak boleh sama dengan password sebelumnya"));
  }
}
