package ist.challenge.agil_syofian_hidayat.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ist.challenge.agil_syofian_hidayat.model.Users;
import ist.challenge.agil_syofian_hidayat.repository.UsersRepository;

@Service
public class UsersService {

  @Autowired
  private final UsersRepository usersRepository;

  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public List<Users> getUsers(){
    return usersRepository.findAll();
  }

  public Users addNewUsers(Users users) {

    if (users.getUsername() == null || users.getUsername().length() == 0 || users.getPassword() == null || users.getPassword().length() == 0) {
     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username dan / atau password koson");
    }

    Optional<Users> usersOpt = usersRepository.findUsersByUsername(users.getUsername());
    if (usersOpt.isPresent()){
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username sudah terpakai");
    }
    return usersRepository.save(users);
  }

  public Users login(Users users) {
    if (users.getUsername() == null || users.getPassword() == null || users.getUsername().length() == 0 || users.getPassword().length() == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username dan / atau password kosong");
    }

    Optional<Users> usersOpt = usersRepository.findUsersByUsernamePassword(users.getUsername(), users.getPassword());
    if (usersOpt.isEmpty()){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username / password tidak cocok");
    }

    return usersOpt.get();
  }

  @Transactional
  public Users updateUsers(Long userId, String username, String password){
    Users userFromDB = usersRepository.findById(userId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId tidak ditemukan"));
    
    if (username != null && username.length() > 0 && !Objects.equals(userFromDB.getUsername(), username)) {
      Optional<Users> usersOpt = usersRepository.findUsersByUsername(username);
      if (usersOpt.isPresent()){
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Username sudah terpakai");
      }
      userFromDB.setUsername(username);
    }

    if (password != null && password.length() > 0) {
      if (password.equals(userFromDB.getPassword())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password tidak boleh sama dengan password sebelumnya");
      }
      userFromDB.setPassword(password);
    }

    return userFromDB;
  }
}
