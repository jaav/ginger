package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "CityClusterJunction")
public class CityClusterJunction extends Model{

	@ManyToOne
	public Locations clusterId;

	@ManyToOne
	public Locations cityId;
}
