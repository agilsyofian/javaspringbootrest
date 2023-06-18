package ist.challenge.agil_syofian_hidayat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class Users {
  @Id
  @SequenceGenerator(
          name = "users_sequence",
          sequenceName = "users_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "users_sequence"
  )

  private Long id;
  private String username;
  private String password;

  public Users() {
  }

  public Users(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public Users(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
  }

}
