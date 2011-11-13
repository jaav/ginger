package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
@Entity
@Table( name = "OrgUserJunction")
public class OrgUserJunction extends Model {
	
	@ManyToOne
	public Organisaties orgId;
	
	@ManyToOne
	public VadGingerUser userId;

}
