package models;

import java.sql.Clob;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.db.jpa.Transactional;

@Entity
@Table(schema = "dbo",name = "GingerUsers")
public class GingerUsers extends Model{

	/*@PersistenceContext
    transient EntityManager entityManager;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserId", length = 50)
    private String userId;*/

	@OneToMany(mappedBy = "userId")
    public Set<Activity> activitiyId;

	@OneToMany(mappedBy = "userId")
    public Set<Organisaties> organisatieses;

	@OneToMany(mappedBy = "userId")
    public Set<UserRoles> userRoleses;

	@Column(name = "Password", length = 50)
    @Required
    public String password;

	@Column(name = "Email")
    @Required
    @Lob
    public String email;

	@Column(name = "FirstName")
    @Lob
    public String firstName;

	@Column(name = "LastName")
    @Lob
    public String lastName;
}
