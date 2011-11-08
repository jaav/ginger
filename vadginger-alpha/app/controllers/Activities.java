package controllers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import models.Activity;
import play.data.validation.Valid;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Activities extends Controller {
	public static void index() {
		List<Activity> entities = models.Activity.all().fetch();
		render(entities);
	}

	public static void create(Activity entity) {
		/*
		 * List<models.Locations> locs =
		 * models.Locations.find("ouder is 1").fetch(); for (models.Locations
		 * loc: locs) { System.out.println(loc.naam); }
		 */
		List<models.Items> items = models.Items.all().fetch();
		List<models.Materials> materials = models.Materials.all().fetch();
		render(entity, items, materials);
	}

	public static void show(java.lang.Long id) {
		Activity entity = Activity.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
		Activity entity = Activity.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
		Activity entity = Activity.findById(id);
		entity.delete();
		index();
	}

	public static void save(@Valid Activity entity) {
		models.VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		entity.centrum = user.userID.toUpperCase().substring(0, 3);
		entity.userId = user;
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
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

	private static void storeEvaluvationsAndEvaluvators(Activity entity) {
		if (entity.evaluvated) {
			List<models.EvaluvationType> evalTypes = models.EvaluvationType.all().fetch();
			for (models.EvaluvationType evalType: evalTypes) {
				if(request.params.get("eval_type_" + evalType.getId())!=null) {
					models.ActivityEvaluvators ae = new models.ActivityEvaluvators();
					ae.activityId = entity;
					ae.evalTypeId = evalType;
					String evaluvatorId = request.params.get("evaluvators");
					if (evaluvatorId!=null) {
						ae.evaluvatorsId  = models.Evaluvators.find("id is " + evaluvatorId).first();
					}
					ae.save();
				}
			}
		}
	}

	private static void storeActivityTargets(Activity entity) {
		String activityTargetId = request.params.get("attendant_type");
		if (activityTargetId!=null) {
			List<models.AttendantType> atdTypes = models.AttendantType.find("byTargetTypeId", models.TargetType.find("id is " + activityTargetId).first()).fetch();
			String atdId = null;
			for (models.AttendantType atd: atdTypes) {
				atdId = request.params.get("atd_typ_" + atd.getId());
				if (atdId!=null) {
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
		if (activityType != null) {
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
				if (sub_sec_id!=null) {
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

		entity = entity.merge();

		entity.save();
		flash.success(Messages.get("scaffold.updated", "Activity"));
		index();
	}
	
   public static void searchForm() {
	   //System.out.println("++++++++++++> HERE");
	   render();
   }
   
   public static void search() {
	   List<models.Activity> entities = new ArrayList<Activity>();
	   boolean internalActivity = (getParam("internal_activity")!=null);
	   boolean nonInternalActivity = (getParam("non_internal_activity")!=null);
	   getActivityByItemsUsed(entities);
	   getActivityBySector(entities);
	   getActivityByLocation(entities);
	   getActivityByOrganization(entities);
	   getActivityByDescription(entities);
	   getActivityByActvityType(entities);
	   getActivityByActivityTargets(entities);
	   renderTemplate("Activities/index.html", entities);
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
		   List<models.Locations> locs = models.Locations.find("id is " + locId).fetch();
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
				List<models.SectorActivityJunction> sacs;
				String sub_sec_id = request.params.get("sub_sector_" + sec.id);
				models.Sectors sub_sec = sec;
				if (sub_sec_id!=null) {
					sub_sec = models.Sectors.find("id is " + sub_sec_id).first();
					 sacs = models.SectorActivityJunction.find("bySectorId", sub_sec).fetch();
				} else {
					sacs = models.SectorActivityJunction.find("bySectorId", sec).fetch(); 
				}
				for(models.SectorActivityJunction sac:sacs) {
					activities.add(sac.activityId);
				}
			}
			if (request.params.get("sec_atd_" + sec.id) != null) {
				models.ActivitySectors as = new models.ActivitySectors();
				List<models.ActivitySectors> acs = models.ActivitySectors.find("bySectorId", sec).fetch();
				for(models.ActivitySectors ac: acs)
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
