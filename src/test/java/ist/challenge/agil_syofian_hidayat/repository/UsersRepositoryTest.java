package ist.challenge.agil_syofian_hidayat.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ist.challenge.agil_syofian_hidayat.model.Users;

@SpringBootTest
public class UsersRepositoryTest {

  @Autowired
  public UsersRepository testingUsersRepository;

  @Test
  public final void testFindUsersByUsername() {

    String username = "john";
    String password = "123456abc";

    Users users = new Users(
      username,
      password  
    );

    testingUsersRepository.save(users);

    Optional<Users> exist = testingUsersRepository.findUsersByUsername(username);

    assertTrue(exist.isPresent());
  }

  @Test
  public final void testFindUsersByUsernamePassword() {
    String username = "john2";
    String password = "123456abc";

    Users user = new Users(
      username,
      password  
    );

    testingUsersRepository.save(user);

    Optional<Users> exist = testingUsersRepository.findUsersByUsernamePassword(username, password);

    assertTrue(exist.isPresent());
  }
}
