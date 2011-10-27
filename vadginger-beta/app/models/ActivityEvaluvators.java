package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(schema = "dbo", name = "ActivityEvaluvators")
public class ActivityEvaluvators extends Model{

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 * 
	 * @Column(name = "ActivityEvalId") private Integer activityEvalId;
	 * 
	 * @PersistenceContext transient EntityManager entityManager;
	 */

	@ManyToOne
	//@JoinColumn(name = "ActivityId", referencedColumnName = "ActivityId")
	public Activity activityId;

	@ManyToOne
	//@JoinColumn(name = "EvalTypeId", referencedColumnName = "EvalTypeId")
	public EvaluvationType evalTypeId;

	@ManyToOne
	// @JoinColumn(name = "EvalId", referencedColumnName = "EvaluvatorsId")
	public Evaluvators evaluvatorsId;
}
