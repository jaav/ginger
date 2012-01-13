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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.db.jpa.Transactional;

@Entity
@Table(name = "Sectors")
public class Sectors extends Model{

	@OneToMany(mappedBy = "sectorId")
    public Set<ActivitySectors> activitySectorss;

	@OneToMany(mappedBy = "sectorId")
    public Set<SectorActivityJunction> sectorActivityJunctions;

	/*@OneToMany(mappedBy = "Sectors")
    public Set<Sectors> sectorss;*/

	@ManyToOne
    //@JoinColumn(name = "Ouder", referencedColumnName = "SectorId", insertable = false, updatable = false)
	public Sectors ouder;

	@Column(name = "Naam", length = 200)
    @Required
    public String naam;

	@Column(name = "beschrijving")
    @Lob
    public Clob beschrijving;
	
	@Column(name="IsActive", nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isActive;
/*
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SectorId")
    private Integer sectorId;

	@PersistenceContext
    transient EntityManager entityManager;*/
	
	
	public String toString() {
		return this.naam;
	}

  public static String toString(Long id){
    if(id==null) return "" ;
    Sectors sec = Sectors.findById(id);
    if(sec!=null) return sec.naam;
    else return "";
  }
}
