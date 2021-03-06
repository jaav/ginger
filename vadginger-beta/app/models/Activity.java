package models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.db.jpa.Transactional;
import play.modules.scaffold.NoScaffolding;

@Entity
@Table(schema = "dbo", name = "Activity")
public class Activity extends Model {

	
	
	//@Id
	/*@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ActivityId", insertable=false, updatable=false)
	public Integer activityId;*/

	@NoScaffolding
	@Column(name = "Centrum", length = 10)
	public String centrum;

	@Column(name = "Duur")
	public Integer duur;

	@Column(name = "Evaluvated")
	@Required
	public boolean evaluvated;

	@Column(name = "InternalActivity")
	@Required
	public boolean internalActivity;

	@Column(name = "Reported")
	public Boolean reported;

	@Column(name = "TotalParticipants")
	public Integer totalParticipants;

	@OneToMany(mappedBy = "activityId")
	public Set<ItemsInActivity> itemsInActivity;
	
	@OneToMany(mappedBy = "activityId")
    public Set<MaterialsInActivity> materialsInActivities;
	
	@OneToMany(mappedBy = "activityId")
    public Set<ActivitySectors> activitySectorss;
	
	@OneToMany(mappedBy = "activityId")
    public Set<SectorActivityJunction> sectorActivityJunctions;
	
	@OneToMany(mappedBy = "activityId")
	public Set<ActivityEvaluvators> activityEvaluvatorsId;
	
	@OneToMany(mappedBy = "activityId")
	public Set<ActivityTargets> activityTargets;
	
	@OneToMany(mappedBy = "activityId")
	public Set<ActivityTypeJunction> activityTypeJunctions;
	
	@ManyToOne
	public Organisaties organizationId;
	
	@ManyToOne
	public Locations locationId;
	
	@ManyToOne
	public VadGingerUser userId;
	
	/*@ManyToOne
	public VadGingerUser userId;
	
	public void setUserId(VadGingerUser vadGingerUser) {
		this.centrum = vadGingerUser.userID.toUpperCase().substring(3);
	}*/
	
	/*@ManyToOne
	private Organisaties organizationId;*/
	
	
}
