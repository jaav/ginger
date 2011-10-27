package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(schema = "dbo", name = "ActivityTargets")
public class ActivityTargets extends Model {

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 * 
	 * @Column(name = "ActivityTargetId") private Integer activityTargetId;
	 * 
	 * @PersistenceContext transient EntityManager entityManager;
	 */

	@ManyToOne
	// @JoinColumn(name = "ActivityId", referencedColumnName = "ActivityId",
	// nullable = false)
	public Activity activityId;

	@ManyToOne
	// @JoinColumn(name = "AttendantTypeId", referencedColumnName =
	// "AttendantTypId", nullable = false)
	public AttendantType attendantTypeId;
}
