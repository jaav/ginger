package models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import play.db.jpa.Model;

@Entity
@Table( name = "TargetType")
public class TargetType extends Model {

	@OneToMany(mappedBy = "targetTypeId")
	public Set<AttendantType> attendantTypes;

	@Column(name = "Beschrijving")
	@Lob
	public String beschrijving;
	
	@Column(name="IsActive", nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isActive;

	/*
	 * @PersistenceContext transient EntityManager entityManager;
	 * 
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 * 
	 * @Column(name = "TargetTypeId") private Integer targetTypeId;
	 */
	
	public String toString() {
		return this.beschrijving;
	}
}
