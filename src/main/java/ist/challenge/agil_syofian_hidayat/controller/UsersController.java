package ist.challenge.agil_syofian_hidayat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ist.challenge.agil_syofian_hidayat.model.Users;
import ist.challenge.agil_syofian_hidayat.service.UsersService;

@RestController
@RequestMapping(path = "api/v1/user")
public class UsersController {

  private final UsersService usersService;
  
  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  @GetMapping
  public ResponseEntity <List<Users>> getUsers(){
    List<Users> users = usersService.getUsers();
    return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
  }

  @PostMapping(path = "registrasi")
  public ResponseEntity<Users> registerUsers(@RequestBody Users users){
    Users userDto = usersService.addNewUsers(users);
    return new ResponseEntity<Users>(userDto, HttpStatus.CREATED);
  }

  @PostMapping(path = "login")
  public ResponseEntity<Users> loginUsers(@RequestBody Users users){
    Users userDto = usersService.login(users);
    return new ResponseEntity<Users>(userDto, HttpStatus.OK);
  }

  @PutMapping(path = "{userId}")
  public ResponseEntity<Users> updateUsers(
      @PathVariable("userId") Long userId,
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String password){
    Users userDto = usersService.updateUsers(userId, username, password);
    return new ResponseEntity<Users>(userDto, HttpStatus.CREATED);
  }

}
