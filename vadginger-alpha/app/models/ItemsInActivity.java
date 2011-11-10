package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import play.db.jpa.Model;
import play.db.jpa.Transactional;

@Entity
@Table(name = "ItemsInActivity")
//@IdClass(ItemsInActivityId.class)
public class ItemsInActivity extends Model {

	/*
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ItemInActId", insertable=false, updatable=false)
	public Integer itemInActId;*/

	//@Id
	@ManyToOne
	//@JoinColumn(name = "ActivityId", nullable = false, referencedColumnName="ActivityId")
	public Activity activityId;

	//@Id
	@ManyToOne
	//@JoinColumn(name = "ItemId", nullable = false, referencedColumnName="ItemId")
	public Items itemId;

	/*
	 * @PersistenceContext transient EntityManager entityManager;
	 */
}
