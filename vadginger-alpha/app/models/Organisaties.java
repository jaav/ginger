package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table( name = "Organisaties")
public class Organisaties extends Model {

	/*
	 * @PersistenceContext transient EntityManager entityManager;
	 */

	@OneToMany(mappedBy = "organizationId", cascade = CascadeType.ALL)
	public Set<Activity> activityId;

	/*
	 * @ManyToOne // @JoinColumn(name = "UserId", referencedColumnName =
	 * "UserId", nullable = // false) public GingerUsers userId;
	 */

	@ManyToOne
	public VadGingerUser userId;
	
	@ManyToOne
	public Centrums centrumId;
	
	@ManyToOne
	public Organisaties ouder;

	@Column(name = "Naam", length = 50)
	@Required
	public String naam;

	@Column(name = "OrganisatieNetwerk", length = 3)
	public String organisatieNetwerk;

	@Column(name = "Adres", length = 50)
	public String adres;

	@Column(name = "Postcode", length = 10)
	public String postcode;

	@Column(name = "Gemeente", length = 50)
	public String gemeente;

	@Column(name = "Land", length = 20)
	public String land;

	/*
	 * @Column(name = "ChangeDate")
	 * 
	 * @Required //@Temporal(TemporalType.TIMESTAMP) //@Past public Date
	 * changeDate;
	 */

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 * 
	 * @Column(name = "OrgID") private Integer orgId;
	 */
	
	public String toString() {
    if(ouder!=null)
      return ouder.naam + " (" + naam + ")";
		return naam;
	}
}
