package models;

import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.db.jpa.Transactional;

@Entity
@Table(schema = "dbo",name = "UserRoles")
public class UserRoles extends Model{

	/*@EmbeddedId
    private UserRolesPK id;

	public UserRolesPK getId() {
        return this.id;
    }

	public void setId(UserRolesPK id) {
        this.id = id;
    }*/

	/*@PersistenceContext
    transient EntityManager entityManager;*/

	@ManyToOne
    //@JoinColumn(name = "RoleId", referencedColumnName = "RoleId", nullable = false, insertable = false, updatable = false)
    public Roles roleId;

	@ManyToOne
    //@JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false, insertable = false, updatable = false)
    public GingerUsers userId;
}
