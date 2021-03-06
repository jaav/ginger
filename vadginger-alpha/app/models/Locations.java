package models;

import java.sql.Clob;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table( name = "Locations")
public class Locations extends Model{

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LocationId")
	private Integer locationId;

	@PersistenceContext
	transient EntityManager entityManager;*/

	@OneToMany(mappedBy = "locationId")
	public Set<Activity> activities;

	/*@OneToMany(mappedBy = "ouder")
	private Set<Locations> locationss;*/

	@ManyToOne
	//@JoinColumn(name = "Ouder", referencedColumnName = "LocationId", insertable = false, updatable = false)
	public Locations ouder;
	
	@ManyToOne
	public Centrums centrumId;

	@Column(name = "Naam", length = 100)
	@Required
	public String naam;

	@Column(name = "Beschrijving")
	@Lob
	public Clob beschrijving;

	@Column(name = "IsCluster", nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isCluster;
	
	@Column(name="IsActive", nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isActive;

  @Transient
  public String cities;
	
	public String toString() {
		return naam;
	}
}
