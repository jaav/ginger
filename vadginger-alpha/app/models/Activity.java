package models;

import java.sql.Clob;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.Type;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.db.jpa.Transactional;
import play.modules.scaffold.NoScaffolding;

@Entity
@Table( name = "Activity")
public class Activity extends Model {

	
	
	//@Id
	/*@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ActivityId", insertable=false, updatable=false)
	public Integer activityId;*/

	@Column(name = "Duur")
	public Integer duur;
	
	@Column(name = "beschrijving")
    @Lob
    public String beschrijving;

	@Column(name = "Evaluvated")
	@Required
	public boolean evaluvated;

	@Column(name = "InternalActivity")
	@Required
	public boolean internalActivity;

	@Column(name = "Reported")
	public Boolean reported;

	@Column(name = "DefForm")
	public Boolean defForm;

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
	public Centrums centrumId;
	
	@ManyToOne
	public Locations locationId;
	
	@ManyToOne
	public VadGingerUser userId;
	
	@Column(name = "Activity_date")
	@Temporal(TemporalType.TIMESTAMP) 
	public Date	activityDate;
	
	@Column(name="IsActive", nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isActive;


	@Column(name = "Pers")
	public String pers;

	@Column(name = "Indicatoren")
	public String indicatoren;

	
	/*@ManyToOne
	public VadGingerUser userId;
	
	public void setUserId(VadGingerUser vadGingerUser) {
		this.centrum = vadGingerUser.userID.toUpperCase().substring(3);
	}*/
	
	/*@ManyToOne
	private Organisaties organizationId;*/

  @Override
  public String toString() {
    if(beschrijving!=null)
      return beschrijving + " - " + organizationId;
    else
      return "Geen beschrijving - "+organizationId;
  }
}
