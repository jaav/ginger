package controllers;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.TemporalType;

import models.*;
import org.apache.commons.beanutils.BeanUtils;
import play.data.validation.Valid;
import play.i18n.Messages;
import play.modules.paginate.ModelPaginator;
import play.mvc.With;

@With(Secure.class)
public class Activities extends GingerController {
	public static void index() {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		ModelPaginator entities = null;
		if (user.role.compareTo(RoleType.ADMIN)>= 0) {
		entities = new ModelPaginator(Activity.class);
		
		}
		else if (user.role.equals(RoleType.ORG_ADMIN)) {/*
			List<OrgUserJunction> orgUserJuncs = OrgUserJunction.find("userId is " + user.id).fetch();
			StringBuffer query = new StringBuffer("organizationId in (");
			Iterator<OrgUserJunction> i = orgUserJuncs.iterator();
			while(i.hasNext())
			{
				query.append("" + i.next().orgId.id);
				if (i.hasNext())
					query.append(",");
			}
			query.append(")");*/
			entities = new ModelPaginator(Activity.class, "centrumId is " + user.centrumId.id);
		} else {
			entities = new ModelPaginator(Activity.class, "userId is " + user.id);
		}
		entities.setPageSize(20);
		render(entities);
	}

	public static void create(Activity entity) {
		/*
		 * List<models.Locations> locs =
		 * models.Locations.find("ouder is 1").fetch(); for (models.Locations
		 * loc: locs) { System.out.println(loc.naam); }
		 */
		List<models.Items> items = models.Items.find("isActive=1").fetch();
		List<models.Materials> materials = models.Materials.find("isActive=1 order by id").fetch();
    setAccordionTab(2);
		render(entity, items, materials);
	}

	public static void copy(Long id) {
		models.VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		Activity entity = Activity.findById(id);
    Activity copy = new Activity();
    try {
      BeanUtils.copyProperties(copy, entity);
      copy.id = null;
      copy.beschrijving = "copy van "+copy.beschrijving;
      copy.isActive = true;
      copy.centrumId = user.centrumId;
      copy.activityEvaluvatorsId = null;
      copy.activityTargets = null;
      copy.activitySectorss = null;
      copy.sectorActivityJunctions = null;
      copy.itemsInActivity = null;
      copy.materialsInActivities = null;
      copy.activityTypeJunctions = null;
      copy = copy.save();

      List<ActivityEvaluvators> aes = ActivityEvaluvators.find("activityId_id is " + entity.id).fetch();
      for (ActivityEvaluvators ae : aes) {
        ActivityEvaluvators newAe = new ActivityEvaluvators();
        BeanUtils.copyProperties(newAe, ae);
        newAe.activityId = copy;
        newAe.save();
      }                  

      List<ActivityTargets> ats = ActivityTargets.find("activityId_id is " + entity.id).fetch();
      for (ActivityTargets at : ats) {
        ActivityTargets newAt = new ActivityTargets();
        BeanUtils.copyProperties(newAt, at);
        newAt.activityId = copy;
        newAt.save();
      }                    

      List<ActivityTypeJunction> atjs = ActivityTypeJunction.find("activityId_id is " + entity.id).fetch();
      for (ActivityTypeJunction atj : atjs) {
        ActivityTypeJunction newAtj = new ActivityTypeJunction();
        BeanUtils.copyProperties(newAtj, atj);
        newAtj.activityId = copy;
        newAtj.save();
      }                    

      List<ItemsInActivity> iias = ItemsInActivity.find("activityId_id is " + entity.id).fetch();
      for (ItemsInActivity iia : iias) {
        ItemsInActivity newAtj = new ItemsInActivity();
        BeanUtils.copyProperties(newAtj, iia);
        newAtj.activityId = copy;
        newAtj.save();
      }                     

      List<SectorActivityJunction> sajs = SectorActivityJunction.find("activityId_id is " + entity.id).fetch();
      for (SectorActivityJunction saj : sajs) {
        SectorActivityJunction newAtj = new SectorActivityJunction();
        BeanUtils.copyProperties(newAtj, saj);
        newAtj.activityId = copy;
        newAtj.save();
      }                       

      List<MaterialsInActivity> mias = MaterialsInActivity.find("activityId_id is " + entity.id).fetch();
      for (MaterialsInActivity mia : mias) {
        MaterialsInActivity newAtj = new MaterialsInActivity();
        BeanUtils.copyProperties(newAtj, mia);
        newAtj.activityId = copy;
        newAtj.save();
      }
      

      edit(copy.id);
    } catch (IllegalAccessException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (InvocationTargetException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    show(id);
	}

	public static void show(java.lang.Long id) {
		Activity entity = Activity.findById(id);
		setAccordionTab(2);
		List<models.ItemsInActivity> iia = models.ItemsInActivity.find("byActivityId", entity).fetch();
		List<models.MaterialsInActivity> mia = models.MaterialsInActivity.find("byActivityId", entity).fetch();
		List<models.ActivitySectors> asc = models.ActivitySectors.find("byActivityId", entity).fetch();
		List<models.ActivityTypeJunction> atj = models.ActivityTypeJunction.find("byActivityId", entity).fetch();
		List<models.ActivityEvaluvators> aes = models.ActivityEvaluvators.find("byActivityId", entity).fetch();
		List<models.ActivityTargets> ats = models.ActivityTargets.find("byActivityId", entity).fetch();
		render(entity, iia, mia, asc, atj, aes, ats);
	}

	public static void edit(java.lang.Long id) {
		List<models.Items> items = models.Items.find("isActive=1").fetch();
		List<models.Materials> materials = models.Materials.find("isActive=1").fetch();
		Activity entity = Activity.findById(id);
		setAccordionTab(2);
    	render(entity, items, materials);
	}

	public static void delete(java.lang.Long id) {
		Activity entity = Activity.findById(id);
		entity.isActive = false;
		//entity.delete();
		index();
	}

	public static void save(@Valid Activity entity) {
		models.VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		//entity.centrum = user.userID.toUpperCase().substring(0, 3);
		entity.userId = user;
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
		storeLocation(entity);
		storeOrganization(entity);
		entity.isActive = true;
		entity.centrumId = user.centrumId;
		getDate(entity);
		entity.save();
		storeEvaluvationsAndEvaluvators(entity);
		storeActivityTargets(entity);
		storeActivityType(entity);
		storeItems(entity);
		storeSectors(entity);
		storeMaterials(entity);
		flash.success(Messages.get("scaffold.created", "Activity"));
		index();
	}

	private static void storeLocation(Activity entity) {
		if (entity.locationId==null) {
			String locationId = request.params.get("location");
			if (locationId!=null&&!locationId.trim().equals("")) {
				models.Locations loc = models.Locations.find("id is " + locationId).first();
				entity.locationId = loc;
			}
		}
		
	}

	private static void storeOrganization(Activity entity) {
		String orgId =request.params.get("organization").trim(); 
		if (!orgId.trim().equals("")) {
			
			String sub_org_id = request.params.get("sub_org_id");
			if (sub_org_id!=null&&!sub_org_id.trim().equals("")) {
				orgId = sub_org_id;
				
			}
			entity.organizationId = Organisaties.find("id is " + orgId).first(); 
		}
	}

	private static void getDate(Activity entity) {
		String actDate = request.params.get("activity_date");
		Date d = getDateFromString(actDate);
		entity.activityDate = d;
	}

	private static Date getDateFromString(String actDate) {
		Date d = null;
		try {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 d = sdf.parse(actDate);
		} catch (Exception e){}
		return d;
	}

	private static void storeEvaluvationsAndEvaluvators(Activity entity) {
		if (entity.evaluvated) {
			List<models.EvaluvationType> evalTypes = models.EvaluvationType.all().fetch();
			for (models.EvaluvationType evalType: evalTypes) {
				String evID = request.params.get("eval_type_" + evalType.getId());
				if(evID!=null&&!evID.trim().equals("")) {
					models.ActivityEvaluvators ae = new models.ActivityEvaluvators();
					ae.activityId = entity;
					ae.evalTypeId = evalType;
					String evaluvatorId = request.params.get("evaluvators");
					if (!evaluvatorId.trim().equals("")&&evaluvatorId!=null) {
						ae.evaluvatorsId  = models.Evaluvators.find("id is " + evaluvatorId).first();
					}
					ae.save();
				}
			}
		}
	}

	private static void storeActivityTargets(Activity entity) {
		String activityTargetId = request.params.get("attendant_type");
		if (activityTargetId!=null&&!activityTargetId.trim().equals("")) {
			List<models.AttendantType> atdTypes = models.AttendantType.find("byTargetTypeId", models.TargetType.find("id is " + activityTargetId).first()).fetch();
			String atdId = null;
			for (models.AttendantType atd: atdTypes) {
				atdId = request.params.get("atd_typ_" + atd.getId());
				if (atdId!=null&&!atdId.trim().equals("")) {
					models.ActivityTargets at = new models.ActivityTargets();
					at.activityId = entity;
					at.attendantTypeId = atd;
					at.save();
				}
			}
		}
	}

	private static void storeActivityType(Activity entity) {
		String activityType = request.params.get("activity_type");
		if (activityType != null && !activityType.trim().equals("")) {
			String sub_act_type = request.params.get("sub_activity_type");
			if (sub_act_type!=null&&!sub_act_type.equals(""))
				activityType = sub_act_type;
			models.ActivityType actTyp = models.ActivityType.find("id is " + activityType).first();
			models.ActivityTypeJunction atj = new models.ActivityTypeJunction();
			atj.activityId = entity;
			atj.activityTypeId = actTyp;
			atj.save();
		}
	}

	private static void storeMaterials(Activity entity) {
		List<models.Materials> materials = models.Materials.all().fetch();
		for (models.Materials material : materials) {
			if(request.params.get("material_1_" + material.id)!=null) {
				models.MaterialsInActivity mic = new models.MaterialsInActivity();
				mic.activityId = entity;
				mic.materialId = material;
				mic.save();
			}
		}
	}

	private static void storeSectors(Activity entity) {
		List<models.Sectors> secs = models.Sectors.find("ouder is null").fetch();
		for (models.Sectors sec: secs) {
			if (request.params.get("sector_" + sec.id)!=null) {
				String sub_sec_id = request.params.get("sub_sector_" + sec.id);
				models.Sectors sub_sec = sec;
				//System.out.println("++++> is empty " + sub_sec_id);
				if (sub_sec_id!=null&&!sub_sec_id.trim().equals("")) {
					//System.out.println("++++> shuoldn;t be here " + sub_sec_id);
					sub_sec = models.Sectors.find("id is " + sub_sec_id).first();
				}
				models.SectorActivityJunction sac = new models.SectorActivityJunction(); 
				sac.activityId = entity;
				sac.sectorId = sub_sec;
				sac.save();
			}
			if (request.params.get("sec_atd_" + sec.id) != null) {
				models.ActivitySectors as = new models.ActivitySectors();
				as.activityId = entity;
				as.sectorId = sec;
				as.save();
			}
			
		}
	}

	private static void storeItems(Activity entity) {
		List<models.Items> items = models.Items.all().fetch();
		for (models.Items item : items) {
			if (request.params.get("item_1_" + item.id) != null) {
				models.ItemsInActivity mic = new models.ItemsInActivity();
				mic.activityId = entity;
				mic.itemId = item;
				mic.save();
			}

		}
	}

	public static void update(@Valid Activity entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}


		//entity.save();
		storeOrganization(entity);
		entity.isActive = true;
		getDate(entity);
		entity = entity.merge();
		if(request.params.get("entity.internalActivity")==null)
			entity.internalActivity = false;
		else
			entity.internalActivity = true;
		if (request.params.get("entity.evaluvated")==null) {
			entity.evaluvated = false;
			entity.reported = false;
		}
		else {
			entity.evaluvated = true;
			//entity.reported = true;
				if (request.params.get("entity.reported")==null)
					entity.reported = false;
			}
		entity.save();
		deletedAllRelationships(entity);
		storeEvaluvationsAndEvaluvators(entity);
		storeActivityTargets(entity);
		storeActivityType(entity);
		storeItems(entity);
		storeSectors(entity);
		storeMaterials(entity);
		flash.success(Messages.get("scaffold.created", "Activity"));
		index();
		flash.success(Messages.get("scaffold.updated", "Activity"));
		index();
	}
	
   private static void deletedAllRelationships(Activity entity) {
		for (models.MaterialsInActivity mia: entity.materialsInActivities)
			mia.delete();
		for (models.ItemsInActivity iia: entity.itemsInActivity)
			iia.delete();
		for (models.ActivityTypeJunction atj: entity.activityTypeJunctions)
			atj.delete();
		for (models.ActivitySectors as: entity.activitySectorss)
			as.delete();
		for(models.ActivityEvaluvators ae: entity.activityEvaluvatorsId)
			ae.delete();
		for (models.ActivityTargets at: entity.activityTargets)
			at.delete();
	
	}

public static void searchForm() {
	   //System.out.println("++++++++++++> HERE");
     setAccordionTab(2);
	   render();
   }
   
   public static void search() {
	   VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
	   List<models.Activity> entities = new ArrayList<Activity>();
	   boolean internalActivity = (getParam("internal_activity")!=null);
	   boolean nonInternalActivity = (getParam("non_internal_activity")!=null);
	   getActivityByDate(entities);
	   getActivityByItemsUsed(entities);
	   getActivityBySector(entities);
	   getActivityByLocation(entities);
	   getActivityByOrganization(entities);
	   getActivityByDescription(entities);
	   getActivityByActvityType(entities);
	   getActivityByActivityTargets(entities);
	   Iterator<Activity> actIter = entities.iterator();
	   if (!(internalActivity && nonInternalActivity))  {
		      while(actIter.hasNext()) {
		    	  Activity e = actIter.next();
				   if (e.internalActivity&&internalActivity) {
					  actIter.remove();
			} else if(!e.internalActivity&&nonInternalActivity)
				actIter.remove();
	   }
	   }
	   if (user.role.equals(RoleType.MEMBER)) {
		   actIter = entities.iterator();
		   while(actIter.hasNext()) {
			   Activity e = actIter.next();
					if (e.userId.id!=user.id) {
						  actIter.remove();
				} 
		   }   
	   }
     setAccordionTab(2);
	   renderTemplate("Activities/index.html", entities);
   }

private static void getActivityByDate(List<models.Activity> entities) {
	Date fromDate = getDateFromString(getParam("from_date"));
	   Date toDate = getDateFromString(getParam("to_date"));
	   if (fromDate!=null&&toDate!=null) {
	   List<models.Activity> ae = models.Activity.em().createQuery("SELECT e " +
               "FROM Activity e " +
               "WHERE e.activityDate BETWEEN :start AND :end")
  .setParameter("start", fromDate, TemporalType.DATE)
  .setParameter("end", toDate, TemporalType.DATE)
  .getResultList();
	   entities.addAll(ae); }
}

private static void getActivityByActivityTargets(
		List<models.Activity> activities) {
	String activityTargetId = request.params.get("attendant_type");
		
		if (activityTargetId!=null) {
			List<models.AttendantType> atdTypes = models.AttendantType.find("byTargetTypeId", models.TargetType.find("id is " + activityTargetId).first()).fetch();
			String atdId = null;
			for (models.AttendantType atd: atdTypes) {
				atdId = request.params.get("atd_typ_" + atd.getId());
				if (atdId!=null) {
					List<models.ActivityTargets> ats = models.ActivityTargets.find("byAttendantTypeId", atd).fetch();
					for(models.ActivityTargets at: ats) {
						activities.add(at.activityId);
					}
				}
			}
		}
}

private static void getActivityByActvityType(List<models.Activity> activities) {
	String activityType = getParam("activity_type");
	   if (activityType!=null&&!activityType.trim().equals("")) {
		   models.ActivityType at = models.ActivityType.find("id is "+ activityType).first();
		   List<models.ActivityTypeJunction> atjs = models.ActivityTypeJunction.find("byActivityTypeId", at).fetch();
		   for (models.ActivityTypeJunction atj: atjs) {
			   activities.add(atj.activityId);
		   }
	   }
}

private static void getActivityByDescription(List<models.Activity> activities) {
	String desc = getParam("entity.beschrijving");
	   if (desc!=null) {
		   List<models.Activity> acts = models.Activity.find("beschrijving like ?", "%"+desc+"%").fetch();
		   activities.addAll(acts);
	   }
}

private static void getActivityByLocation(List<models.Activity> activities) {
	String locId = getParam("entity.locationId.id");
	   if (locId==null)
		   locId = getParam("location");
	   if (locId!=null&&!locId.trim().equalsIgnoreCase("")) {
		   models.Locations locs = models.Locations.find("id is " + locId).first();
		   List<Activity> acts = Activity.find("byLocationId", locs).fetch();
		   activities.addAll(acts);
	   }
}

private static void getActivityByOrganization(List<models.Activity> activities) {
	String orgId = getParam("entity.organizationId.id");
	   if (orgId==null)
		   orgId = getParam("org_id");
	   if (orgId!=null&&!orgId.trim().equalsIgnoreCase("")) {
		   models.Organisaties org = models.Organisaties.find("id is " + orgId).first();
		   List<Activity> acts = Activity.find("byOrganizationId", org).fetch();
		   activities.addAll(acts);
	   }
}

private static void getActivityBySector(List<models.Activity> activities) {
	List<models.Sectors> secs = models.Sectors.find("ouder is null").fetch();
		for (models.Sectors sec: secs) {
			if (request.params.get("sector_" + sec.id)!=null) {
				List<models.ActivitySectors> sacs;
				String sub_sec_id = request.params.get("sub_sector_" + sec.id);
				models.Sectors sub_sec = sec;
				if (sub_sec_id!=null&&!sub_sec_id.trim().equals("")) {
					sub_sec = models.Sectors.find("id is " + sub_sec_id).first();
					 sacs = models.ActivitySectors.find("bySectorId", sub_sec).fetch();
				} else {
					sacs = models.ActivitySectors.find("bySectorId", sec).fetch(); 
				}
				for(models.ActivitySectors sac:sacs) {
					activities.add(sac.activityId);
				}
			}
			if (request.params.get("sec_atd_" + sec.id) != null) {
				//models.SectorActivityJunction as = new models.SectorActivityJunction();
				List<models.SectorActivityJunction> acs = models.SectorActivityJunction.find("bySectorId", sec).fetch();
				for(models.SectorActivityJunction ac: acs)
					activities.add(ac.activityId);
			}
			
		}
}

private static void getActivityByItemsUsed(List<models.Activity> activities) {
	List<models.Items> items = models.Items.all().fetch();
	   for (models.Items item: items) {
		   if (getParam("item_1_"+item.getId())!=null) {
			   List<models.ItemsInActivity> iia = models.ItemsInActivity.find("byItemId", item).fetch();
			   for (models.ItemsInActivity ia: iia)
				   activities.add(ia.activityId);
		   }
			   
	   }
}

private static String getParam(String paramName) {
	// TODO Auto-generated method stub
	return request.params.get(paramName);
}


}
