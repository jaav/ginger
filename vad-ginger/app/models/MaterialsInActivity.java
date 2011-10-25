package models;

import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.db.jpa.Transactional;

@Entity
@Table(schema = "dbo",name = "MaterialsInActivity")
public class MaterialsInActivity extends Model{

	
	@ManyToOne
    //@JoinColumn(name = "ActivityId", referencedColumnName = "ActivityId", nullable = false, insertable = false, updatable = false)
    public Activity activityId;

	@ManyToOne
    //@JoinColumn(name = "MaterialId", referencedColumnName = "MaterialId", nullable = false, insertable = false, updatable = false)
    public Materials materialId;

	/*@EmbeddedId
    private MaterialsInActivityPK id;

	public MaterialsInActivityPK getId() {
        return this.id;
    }

	public void setId(MaterialsInActivityPK id) {
        this.id = id;
    }*/
}
