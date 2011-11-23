package models;

import java.sql.Clob;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

import org.hibernate.annotations.Type;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="Centrum")
public class Centrums extends Model {
	
	@Column(name = "Naam", length = 10)
	@Required
	public String naam;
	
	@Column(name = "Beschrijving")
	@Lob
	public String beschrijving;
	
	@OneToMany(mappedBy="centrumId",cascade = CascadeType.ALL)
	public Set<models.Activity> activityId;
	
	@OneToMany(mappedBy="centrumId",cascade = CascadeType.ALL)
	public Set<models.Organisaties> organizationId;
	
	@OneToMany(mappedBy="centrumId",cascade = CascadeType.ALL)
	public Set<models.VadGingerUser> userId;
	
	@Column(name="IsActive", nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isActive;

}
