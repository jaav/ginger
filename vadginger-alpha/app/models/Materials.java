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
@Table( name = "Materials")
public class Materials extends Model{

	/*@PersistenceContext
    transient EntityManager entityManager;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MaterialId")
    private Integer materialId;*/

	@OneToMany(mappedBy = "materialId")
    public Set<MaterialsInActivity> materialsInActivities;

	@Column(name = "Naam", length = 100)
    @Required
    public String naam;

	@Column(name = "Beschrijving")
    @Lob
    public Clob beschrijving;
}
