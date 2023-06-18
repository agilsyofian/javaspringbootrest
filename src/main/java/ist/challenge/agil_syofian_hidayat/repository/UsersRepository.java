package ist.challenge.agil_syofian_hidayat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ist.challenge.agil_syofian_hidayat.model.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
  
  @Query("SELECT u FROM Users u WHERE u.username = ?1") Optional<Users> findUsersByUsername(String username);
  @Query("SELECT u FROM Users u WHERE u.username = ?1 AND u.password = ?2") Optional<Users> findUsersByUsernamePassword(String username, String password);

}
