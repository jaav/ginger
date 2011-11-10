package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "ActivityTypeJunction")
public class ActivityTypeJunction extends Model {

	/*
	 * @PersistenceContext transient EntityManager entityManager;
	 * 
	 * @EmbeddedId private ActivityTypeJunctionPK id;
	 */

	@ManyToOne
	// @JoinColumn(name = "ActivityId", referencedColumnName = "ActivityId",
	// nullable = false, insertable = false, updatable = false)
	public Activity activityId;

	@ManyToOne
	// @JoinColumn(name = "ActivityTypeId", referencedColumnName =
	// "ActivityTypeId", nullable = false, insertable = false, updatable =
	// false)
	public ActivityType activityTypeId;
}
