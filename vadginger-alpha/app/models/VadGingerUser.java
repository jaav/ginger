package models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import controllers.Security;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import play.modules.scaffold.NoScaffolding;
import play.db.jpa.Model;
import play.mvc.Scope.Session;

@Entity
public class VadGingerUser extends Model {
  public String userID;
  public String firstName;
  public String lastName;
  public String emailAddress;
  @NoScaffolding
  public String passwordHash;
  public transient String password;
  public RoleType role;
  public int loginCount;
  
  @OneToMany(mappedBy = "userId")
  public Set<Activity> activityId;
  
  @OneToMany(mappedBy = "userId")
  public Set<Organisaties> organizationId;
  
  @ManyToOne
  public Centrums centrumId;
  
  @Column(name="IsActive", nullable=false)
  @Type(type = "org.hibernate.type.NumericBooleanType")
  public Boolean isActive;

  public VadGingerUser() {
  }

  public void setPassword(String password) {
    if (StringUtils.isBlank(password))
      return;
    this.passwordHash = password;
  }

  public boolean isCurrentUser(String currentUser) {
    String currentUserName = Session.current().get("username");
    if (StringUtils.isBlank(currentUserName))
      return false;
    return currentUserName.equalsIgnoreCase(currentUser);
  }

  @Override
  public String toString() {
    String name = null;
    if(firstName!=null) name = firstName;
    if(lastName!=null && name!=null) name = name+" "+lastName;
    if(lastName!=null && name==null) name = lastName;
    if(name!=null) return userID+" ("+name+")";
    else return userID;
  }
}

