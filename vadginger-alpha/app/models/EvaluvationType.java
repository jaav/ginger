package models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(schema = "dbo", name = "Evaluvation_Type")
public class EvaluvationType extends Model{

	@OneToMany(mappedBy = "evalTypeId")
	public Set<ActivityEvaluvators> activityEvaluvatorss;

	@Column(name = "EvalType", length = 50)
	public String evalType;

/*	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EvalTypeId")
	public Integer evalTypeId;

	@PersistenceContext
	transient EntityManager entityManager;*/
	
	public String toString() {
		return this.evalType;
	}
}
