package ist.challenge.agil_syofian_hidayat.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import ist.challenge.agil_syofian_hidayat.model.Users;
import ist.challenge.agil_syofian_hidayat.service.UsersService;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTest {

  @InjectMocks
  private UsersController usersController;

  @Mock
  private UsersService usersService;

  @Test
  void testGetUsers() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    List<Users> listUser = List.of(
      new Users(1L,"john","123456abc"),
      new Users(2L,"myqes","abc123456")
    );

    when(usersService.getUsers()).thenReturn(listUser);

    ResponseEntity<List<Users>> responseEntity = usersController.getUsers();

    verify(usersService).getUsers();

    assertEquals(responseEntity.getBody().toString(), listUser.toString());
    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void testLoginUsersSuccess() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    Users user = new Users(1L,"john","123456abc");

    when(usersService.login(user)).thenReturn(user);

    ResponseEntity<Users> responseEntity = usersController.loginUsers(user);

    verify(usersService).login(user);

    assertEquals(responseEntity.getBody().toString(), user.toString());
    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void testLoginUsersBadRequest() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    Users user = new Users(1L,"","");

    when(usersService.login(user)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username dan / atau password kosong"));

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> usersController.loginUsers(user));
    assertEquals(thrown.getStatus(), HttpStatus.BAD_REQUEST);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Username dan / atau password kosong"));
  }

  @Test
  void testLoginUsersUnauthorized() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    Users user = new Users(1L,"john","abc123456");

    when(usersService.login(user)).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username / password tidak cocok"));

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> usersController.loginUsers(user));
    assertEquals(thrown.getStatus(), HttpStatus.UNAUTHORIZED);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Username / password tidak cocok"));
  }

  @Test
  void testRegisterUsers() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    Users user = new Users("john","abc123456");

    when(usersService.addNewUsers(user)).thenReturn(user);
    
    ResponseEntity<Users> responseEntity = usersController.registerUsers(user);

    verify(usersService).addNewUsers(user);

    assertEquals(responseEntity.getBody().getUsername(), user.getUsername());
    assertEquals(responseEntity.getBody().getPassword(), user.getPassword());
    assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
  }

  @Test
  void testRegisterUsersUsernameExist() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    Users user = new Users("john","abc123456");

    when(usersService.addNewUsers(user)).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Username sudah terpakai"));

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> usersController.registerUsers(user));
    assertEquals(thrown.getStatus(), HttpStatus.CONFLICT);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Username sudah terpakai"));
  }

  @Test
  void testUpdateUsers() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    Users user = new Users(1L, "john doe","abc123456");

    when(usersService.updateUsers(user.getId(), user.getUsername(), user.getPassword())).thenReturn(user);

    ResponseEntity<Users> responseEntity = usersController.updateUsers(user.getId(), user.getUsername(), user.getPassword());
    assertEquals(responseEntity.getBody().getUsername(), user.getUsername());
    assertEquals(responseEntity.getBody().getPassword(), user.getPassword());
    assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
  }

  @Test
  void testUpdateUsersUsernameExist() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    Users user = new Users(1L, "john doe","abc123456");

    when(usersService.updateUsers(user.getId(), user.getUsername(), user.getPassword())).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Username sudah terpakai"));

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> usersController.updateUsers(user.getId(), user.getUsername(), user.getPassword()));
    assertEquals(thrown.getStatus(), HttpStatus.CONFLICT);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Username sudah terpakai"));
  }

  @Test
  void testUpdateUsersPasswordNotChange() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    Users user = new Users(1L, "john doe","abc123456");

    when(usersService.updateUsers(user.getId(), user.getUsername(), user.getPassword())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password tidak boleh sama dengan password sebelumnya"));

    ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> usersController.updateUsers(user.getId(), user.getUsername(), user.getPassword()));
    assertEquals(thrown.getStatus(), HttpStatus.BAD_REQUEST);
    assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Password tidak boleh sama dengan password sebelumnya"));
  }
}
