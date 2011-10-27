package models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.db.jpa.Transactional;

@Entity
@Table(schema = "dbo",name = "ActivitySectors")

public class ActivitySectors extends Model{

	/*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ActivitySectorId")
    private Integer activitySectorId;*/

	@ManyToOne
    //@JoinColumn(name = "ActivityId", referencedColumnName = "ActivityId", nullable = false)
    public Activity activityId;

	@ManyToOne
    //@JoinColumn(name = "SectorId", referencedColumnName = "SectorId", nullable = false)
    public Sectors sectorId;

	
}
