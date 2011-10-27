package models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(schema = "dbo", name = "AttendantType")
public class AttendantType extends Model {

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 * 
	 * @Column(name = "AttendantTypId") private Integer attendantTypId;
	 */

	@OneToMany(mappedBy = "attendantTypeId")
	public Set<ActivityTargets> activityTargetss;

	@ManyToOne
	// @JoinColumn(name = "TargetTypeId", referencedColumnName = "TargetTypeId",
	// nullable = false)
	public TargetType targetTypeId;

	@Column(name = "Naam", length = 100)
	@Required
	public String naam;

	@Column(name = "Beschrijving")
	@Lob
	public String beschrijving;

	/*
	 * @PersistenceContext transient EntityManager entityManager;
	 */
}
