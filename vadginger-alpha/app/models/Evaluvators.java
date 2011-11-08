package models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(schema = "dbo",name = "Evaluvators")
public class Evaluvators extends Model{

	@OneToMany(mappedBy = "evaluvatorsId")
    public Set<ActivityEvaluvators> activityEvaluvatorId;

	@Column(name = "Naam", length = 50)
    @Required
    public String naam;

	/*
	 * @PersistenceContext transient EntityManager entityManager;
	 * 
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 * 
	 * @Column(name = "EvaluvatorsId") private Integer evaluvatorsId;
	 */
	
	public String toString() {
		return this.naam;
	}
}
