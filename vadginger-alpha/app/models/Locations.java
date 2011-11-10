package models;

import java.sql.Clob;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

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

	@Column(name = "Naam", length = 100)
	@Required
	public String naam;

	@Column(name = "Beschrijving")
	@Lob
	public Clob beschrijving;

	@Column(name = "IsCluster")
	public Boolean isCluster;
	
	public String toString() {
		return naam;
	}
}
