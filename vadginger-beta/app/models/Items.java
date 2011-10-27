package models;

import java.sql.Clob;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.db.jpa.Transactional;

@Entity
@Table(schema = "dbo", name = "Items")
public class Items extends Model {

	/*
	 * @PersistenceContext transient EntityManager entityManager;
	 */

	/*@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ItemId", insertable=false, updatable=false)
	public Integer itemId;*/

	@Column(name = "Naam", length = 100)
	@Required
	public String naam;

	@Column(name = "Beschrijving")
	@Lob
	public String beschrijving;

	@OneToMany(mappedBy = "itemId")
	public Set<ItemsInActivity> itemsInActivities;
	
	public Items(String naam) {
		this.naam = naam;
	}
	
	
	public Items(String naam,String beschrijving) {
		this.naam = naam;
		this.beschrijving = beschrijving;
	}
}
