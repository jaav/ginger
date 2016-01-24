package models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table( name = "ActivityType")
public class ActivityType extends Model {

	/*
	 * @OneToMany(mappedBy = "ouder") private Set<ActivityType> activityTypes;
	 */

	@OneToMany(mappedBy = "activityTypeId")
	public Set<ActivityTypeJunction> activityTypeJunctions;

	@ManyToOne
	// @JoinColumn(name = "Ouder", referencedColumnName = "ActivityTypeId",
	// insertable = false, updatable = false)
	public ActivityType ouder;

	@Column(name = "Naam", length = 100)
	@Required
	public String naam;

	@Column(name = "Beschrijving")
	@Lob
	public String beschrijving;

	@Column(name = "UsedBy", length = 10)
	public String usedBy;
	
	@Column(name="IsActive", nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isActive;
	
	public String toString() {
		return this.naam;
	}

	/*
	 * @PersistenceContext transient EntityManager entityManager;
	 * 
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 * 
	 * @Column(name = "ActivityTypeId") private Integer activityTypeId;
	 */
}
