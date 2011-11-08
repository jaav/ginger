package models;

import javax.persistence.Entity;

import controllers.Security;

import org.apache.commons.lang.StringUtils;

import play.modules.scaffold.NoScaffolding;
import play.db.jpa.Model;
import play.mvc.Scope.Session;

@Entity
public class User extends Model {
  public String userID;
  public String firstName;
  public String lastName;
  public String emailAddress;
  @NoScaffolding
  public String passwordHash;
  public transient String password;
  public RoleType role;
  public int loginCount;

  public User() {
  }

  public void setPassword(String password) {
    if (StringUtils.isBlank(password))
      return;
    this.passwordHash = Security.md5(password);
  }

  public boolean isCurrentUser(String currentUser) {
    String currentUserName = Session.current().get("username");
    if (StringUtils.isBlank(currentUserName))
      return false;
    return currentUserName.equalsIgnoreCase(currentUser);
  }
}

