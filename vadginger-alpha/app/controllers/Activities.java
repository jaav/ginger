package controllers;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.management.Query;
import javax.persistence.TemporalType;

import models.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import play.Logger;
import org.apache.commons.lang.StringUtils;

import play.data.validation.Valid;
import play.i18n.Messages;
import play.libs.Mail;
import play.modules.paginate.ModelPaginator;
import play.modules.paginate.Paginator;
import play.modules.paginate.ValuePaginator;
import play.mvc.With;

@With(Secure.class)
public class Activities extends GingerController {

	public static void index(String orderby, String orderhow) {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
    String orderquery = StringUtils.isNotBlank(orderby)&&StringUtils.isNotBlank(orderhow) ? " order by "+orderby+" "+orderhow : " order by id desc";
    if(params.getAll("filter")!=null) orderSearch(orderquery);
    Paginator entities = null;
    if("organizationId.naam".equals(orderby)){
      String query;
      List<models.Activity> result;
      if (user.role.compareTo(RoleType.ADMIN)>= 0)
        query = "isActive=1";
      else if (user.role.equals(RoleType.ORG_ADMIN))
        query = "centrumId is " + user.centrumId.id +" and isActive=1";
      else
        query = "userId is " + user.id + " and isActive=1";

      result = getSortedByOrganisation(query, orderhow);
      entities = new ValuePaginator(result);
    }
    else{
      entities = null;
      if (user.role.compareTo(RoleType.ADMIN)>= 0) {
        entities = new ModelPaginator(Activity.class, "isActive=1"+orderquery);
      }
      else if (user.role.equals(RoleType.ORG_ADMIN)) {
        entities = new ModelPaginator(Activity.class, "centrumId is " + user.centrumId.id +" and isActive=1"+orderquery);
      } else {
        entities = new ModelPaginator(Activity.class, "userId is " + user.id + " and isActive=1"+orderquery);
      }
    }
		entities.setPageSize(100);
		setAccordionTab(2);
    //renderArgs.put("allowExport", true);
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
      //BeanUtils.copyProperties(copy, entity);
      copy.beschrijving = entity.beschrijving;
      copy.activityDate = entity.activityDate;
      copy.centrumId = entity.centrumId;
      copy.evaluvated = entity.evaluvated;
      copy.internalActivity = entity.internalActivity;
      copy.isActive = true;
      copy.locationId = entity.locationId;
      copy.organizationId = entity.organizationId;
      copy.totalParticipants = entity.totalParticipants;
      copy.duur = entity.duur;
      copy.userId = user;
      Logger.debug("Activity was copied from %s"+entity);
      Logger.debug("Activity was copied into %s"+copy);
      /*copy.id = null;
      copy.beschrijving = "copy van "+copy.beschrijving;
      copy.isActive = true;
      copy.centrumId = user.centrumId;
      copy.activityEvaluvatorsId = null;
      copy.activityTargets = null;
      copy.activitySectorss = null;
      copy.sectorActivityJunctions = null;
      copy.itemsInActivity = null;
      copy.materialsInActivities = null;
      copy.activityTypeJunctions = null;*/
      copy = copy.save();

      List<ActivityEvaluvators> aes = ActivityEvaluvators.find("activityId_id is " + entity.id).fetch();
      for (ActivityEvaluvators ae : aes) {
        ActivityEvaluvators newAe = new ActivityEvaluvators();
        //BeanUtils.copyProperties(newAe, ae);
        newAe.evalTypeId = ae.evalTypeId;
        newAe.activityId = ae.activityId;
        newAe.activityId = copy;
        newAe.save();
      }                  

      List<ActivityTargets> ats = ActivityTargets.find("activityId_id is " + entity.id).fetch();
      for (ActivityTargets at : ats) {
        ActivityTargets newAt = new ActivityTargets();
        //BeanUtils.copyProperties(newAt, at);
        newAt.attendantTypeId = at.attendantTypeId;
        newAt.activityId = copy;
        newAt.save();
      }                    

      List<ActivityTypeJunction> atjs = ActivityTypeJunction.find("activityId_id is " + entity.id).fetch();
      for (ActivityTypeJunction atj : atjs) {
        ActivityTypeJunction newAtj = new ActivityTypeJunction();
        //BeanUtils.copyProperties(newAtj, atj);
        newAtj.activityTypeId = atj.activityTypeId;
        newAtj.activityId = copy;
        newAtj.save();
      }                    

      List<ItemsInActivity> iias = ItemsInActivity.find("activityId_id is " + entity.id).fetch();
      for (ItemsInActivity iia : iias) {
        ItemsInActivity newAtj = new ItemsInActivity();
        //BeanUtils.copyProperties(newAtj, iia);
        newAtj.itemId = iia.itemId;
        newAtj.activityId = copy;
        newAtj.save();
      }                     

      List<SectorActivityJunction> sajs = SectorActivityJunction.find("activityId_id is " + entity.id).fetch();
      for (SectorActivityJunction saj : sajs) {
        SectorActivityJunction newAtj = new SectorActivityJunction();
        //BeanUtils.copyProperties(newAtj, saj);
        newAtj.sectorId = saj.sectorId;
        newAtj.activityId = copy;
        newAtj.save();
      }                       

      List<MaterialsInActivity> mias = MaterialsInActivity.find("activityId_id is " + entity.id).fetch();
      for (MaterialsInActivity mia : mias) {
        MaterialsInActivity newAtj = new MaterialsInActivity();
        //BeanUtils.copyProperties(newAtj, mia);
        newAtj.materialId = mia.materialId;
        newAtj.activityId = copy;
        newAtj.save();
      }
      

      edit(copy.id);
    }
    catch (Exception e){
      e.printStackTrace();
    }
    /* catch (IllegalAccessException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (InvocationTargetException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }*/
    show(id);
	}

	public static void show(java.lang.Long id) {
		Activity entity = Activity.findById(id);
		setAccordionTab(2);
		List<models.ItemsInActivity> iia = models.ItemsInActivity.find("byActivityId", entity).fetch();
		List<models.MaterialsInActivity> mia = models.MaterialsInActivity.find("byActivityId", entity).fetch();
		List<models.ActivitySectors> asc = models.ActivitySectors.find("byActivityId", entity).fetch();
		List<models.SectorActivityJunction> sajs = models.SectorActivityJunction.find("byActivityId", entity).fetch();
		List<models.ActivityTypeJunction> atj = models.ActivityTypeJunction.find("byActivityId", entity).fetch();
		List<models.ActivityEvaluvators> aes = models.ActivityEvaluvators.find("byActivityId", entity).fetch();
		List<models.ActivityTargets> ats = models.ActivityTargets.find("byActivityId", entity).fetch();
		render(entity, iia, mia, asc, atj, aes, ats, sajs);
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
		entity.save();
		//entity.delete();
		index(null, null);
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
		index(null, null);
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
	
	private static String getDateString(Date d) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			return sf.format(d);
		} catch(Exception e) {return"";}
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
					if (evaluvatorId!=null&&!evaluvatorId.trim().equals("")) {
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
			String[] sub_act_types = request.params.getAll("sub_activity_type");
      if(sub_act_types!=null){
        for (int i = 0; i < sub_act_types.length; i++) {
          String sub_act_type = sub_act_types[i];
          if (sub_act_type!=null&&!sub_act_type.equals("")){
            models.ActivityType actTyp = models.ActivityType.find("id is " + sub_act_type).first();
            models.ActivityTypeJunction atj = new models.ActivityTypeJunction();
            atj.activityId = entity;
            atj.activityTypeId = actTyp;
            atj.save();
          }
        }
      }
      else{
        models.ActivityType actTyp = models.ActivityType.find("id is " + activityType).first();
        models.ActivityTypeJunction atj = new models.ActivityTypeJunction();
        atj.activityId = entity;
        atj.activityTypeId = actTyp;
        atj.save();
      }
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
        //if a sub-sector for the checked sector was selected
				if (sub_sec_id!=null&&!sub_sec_id.trim().equals("")) {
					String[] arr = request.params.getAll("sub_sector_"+sec.id);
					for (String ar: arr) {
						sub_sec = models.Sectors.find("id is " + ar).first();
						models.SectorActivityJunction sac = new models.SectorActivityJunction(); 
						sac.activityId = entity;
						sac.sectorId = sub_sec;
						sac.save();
					}
				}
        //if only the sector was checked and no sub-sector selected
        else {
          models.SectorActivityJunction sac = new models.SectorActivityJunction();
          sac.activityId = entity;
          sac.sectorId = sub_sec;
          sac.save();}
			}
			/*if (request.params.get("sec_atd_" + sec.id) != null) {
				models.ActivitySectors as = new models.ActivitySectors();
				as.activityId = entity;
				as.sectorId = sec;
				as.save();
			}*/
			
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
    List subor = params.get("sub_org_id", List.class);
		entity.isActive = true;
		getDate(entity);
		entity = entity.merge();
		/*if(request.params.get("entity.internalActivity")==null)
			entity.internalActivity = false;
		else
			entity.internalActivity = true;*/
		if (entity.evaluvated) {
			entity.evaluvated = true;
		}
		else {
			entity.evaluvated = false;
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
		index(null, null);
		flash.success(Messages.get("scaffold.updated", "Activity"));
		index(null, null);
	}
	
   private static void deletedAllRelationships(Activity entity) {
		for (models.MaterialsInActivity mia: entity.materialsInActivities)
			mia.delete();
		for (models.ItemsInActivity iia: entity.itemsInActivity)
			iia.delete();
		for (models.ActivityTypeJunction atj: entity.activityTypeJunctions)
			atj.delete();
		for (SectorActivityJunction as: entity.sectorActivityJunctions)
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
	   StringBuffer joinClause = new StringBuffer("select distinct act from Activity act ");
	   ArrayList<String> whereClause = new ArrayList<String>();
	   /*String query = "select mat.activityId from Activity act, MaterialsInActivity mat where mat.activityId.id = act.id and mat.materialId = 8";
	   query = "select distinct act " +
	   						"from Activity act " +
	   						"join act.materialsInActivities mat " +
	   						"join act.itemsInActivity iat " +
	   						"join mat.materialId mid " +
	   						"join iat.itemId iid " +
	   						"where mid.id=5 and iid=2";*/
	   
	   VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
	   if (user.role.equals(models.RoleType.MEMBER)) {
		   whereClause.add("act.userId=" + user.id);
	   } else if (user.role.equals(models.RoleType.ORG_ADMIN)) {
		   whereClause.add("act.centrumId=" + user.centrumId.id);
	   }
	  // List<models.Activity> entities = new ArrayList<Activity>();
    String intActivity = request.params.get("internal_activity");
    if (!StringUtils.isBlank(intActivity)&&intActivity.trim().toLowerCase().equals("yes"))
      whereClause.add("act.internalActivity=1");
    else if (!StringUtils.isBlank(intActivity)&&intActivity.trim().toLowerCase().equals("no"))
      whereClause.add("act.internalActivity=0");
    else if (!StringUtils.isBlank(intActivity)&&intActivity.trim().toLowerCase().equals("both"))
      whereClause.add("act.internalActivity in (0,1)");
	   getActivityByItemsUsed(whereClause, joinClause);
	   getActivityByMaterialUsed(whereClause, joinClause);
	   getActivityBySector(whereClause, joinClause);
	   getActivityByLocation(whereClause, joinClause);
	   getActivityByOrganization(whereClause, joinClause);
	   getActivityByTotalParticipants(whereClause);
	   getActivityByDuration(whereClause);
	   getActivityByDescription(whereClause);
	   getActivityByActvityType(whereClause, joinClause);
	   getActivityByActivityTargets(whereClause, joinClause);
	   getActivityByDate(whereClause);
	   getActivityByEvaluvation(whereClause,joinClause);
	   getActivityByUser(whereClause,joinClause);
	   //System.out.println("+++ where clause = "+ whereClause.toString());
	   StringBuffer where = new StringBuffer();
	   String[] whereArr = new String[whereClause.size()]; 
	   whereClause.toArray(whereArr);
	   for (int i = 0; i < whereArr.length; i++) {
		   where.append(whereArr[i]);
		   if(i!=whereArr.length-1)
			   where.append(" and ");
	   }
	   if (where.toString().trim().equals("")){
		   searchForm();
		   session.put("query", "");
     }
	   else {
       String quer = joinClause.toString() + " where " +where.toString()+ " order by act.id desc";
       System.out.println("=========================> query=" + quer);
       List<models.Activity> result = models.Activity.find(quer).fetch();
		   ValuePaginator entities = new ValuePaginator(result);
       if (request.params.get("sector_999")!=null) {
    	   for (Iterator<Activity> i = entities.iterator(); i.hasNext();) {
    		   Activity e = i.next();
    		   HashSet<Long> secIds = new HashSet<Long>();
    		   for (models.SectorActivityJunction saj: e.sectorActivityJunctions) {
    			   if (saj.sectorId.ouder!=null)
    				   secIds.add(saj.sectorId.ouder.id);
    			   else
    				   secIds.add(saj.sectorId.id);
    		   }
    		   if (secIds.size() < 2)
    			   i.remove();
    	   }
    	   
       }
		   entities.setPageSize(100);
       setAccordionTab(2);
       renderArgs.put("allowExport", true);
		   session.put("query", quer);
       renderArgs.put("count", entities.size());
       renderTemplate("Activities/index.html", entities);
     }
   }

  private static void orderSearch(String orderQuery){
		String query = session.get("query");
    List<models.Activity> result;
    if(orderQuery.indexOf("organizationId.naam")>0){
      result = getSortedByOrganisation(query, orderQuery.indexOf("desc")>0 ? "desc" : "asc");
    }
    else{
      query = query.substring(0, query.indexOf("order by"))+orderQuery.replace("order by ", "order by act.");
      result = Activity.find(query).fetch();
    }
    ValuePaginator entities = new ValuePaginator(result);
    setAccordionTab(2);
    entities.setPageSize(100);
    renderArgs.put("allowExport", true);
    renderArgs.put("count", entities.size());
    renderTemplate("Activities/index.html", entities);
  }

  private static List<Activity> getSortedByOrganisation(String query, String ascdesc){
    List<Activity> activities = Activity.find(query).fetch();
    Comparator<Activity> comp;
    if("asc".equals(ascdesc))
      comp = new ActivityComparableAsc();
    else
      comp = new ActivityComparableDesc();
    Collections.sort(activities, comp);
    return activities;
  }
  
  private static class ActivityComparableDesc implements Comparator<Activity>{
 
    @Override
    public int compare(Activity a1, Activity a2) {
      String name1 = getComparableOrganizationName(a1);
      String name2 = getComparableOrganizationName(a2);
      return (name1.compareTo(name2)>0 ? -1 : (name1.equals(name2) ? 0 : 1));
    }
  }

  private static class ActivityComparableAsc implements Comparator<Activity>{

    @Override
    public int compare(Activity a1, Activity a2) {
      String name1 = getComparableOrganizationName(a1);
      String name2 = getComparableOrganizationName(a2);
      return (name2.compareTo(name1)>0 ? -1 : (name2.equals(name1) ? 0 : 1));
    }
  }

  private static String getComparableOrganizationName(Activity activity){
      String name;
    if(activity.organizationId!=null){
      if(activity.organizationId.ouder!=null)
        name = activity.organizationId.ouder.naam+activity.organizationId.naam;
      else
        name = activity.organizationId.naam;
    }
    else name = "";
    return name;
  }

private static void getActivityByDuration(ArrayList<String> whereClause) {
	String durr = request.params.get("entity.duur");
	if(!StringUtils.isBlank(durr))
		whereClause.add("act.duur = " + durr);
	
}

private static void getActivityByTotalParticipants(ArrayList<String> whereClause) {
	String totalParticipants = request.params.get("entity.totalParticipants");
	if(!StringUtils.isBlank(totalParticipants))
		whereClause.add("act.totalParticipants = " + totalParticipants);
}

private static void getActivityByDate(ArrayList<String> whereClause) {
	Date fromDate = getDateFromString(getParam("from_date"));
	   Date toDate = getDateFromString(getParam("to_date"));
	   if (fromDate!=null) {
		   whereClause.add("act.activityDate >= '" + getDateString(fromDate) + "'");
	   }
	   if (toDate!=null) {
		   whereClause.add("act.activityDate <= '" + getDateString(toDate) + "'");
	   }
}

private static void getActivityByActivityTargets(ArrayList<String> whereClause, StringBuffer joinClause) {
	String activityTargetId = request.params.get("attendant_type");
		
  if (activityTargetId!=null) {
    List<models.AttendantType> atdTypes = models.AttendantType.find("byTargetTypeId", models.TargetType.find("id is " + activityTargetId).first()).fetch();
    String atdId = null;
    boolean hasTarget = false;
    joinClause.append(" join act.activityTargets actTarget join actTarget.attendantTypeId atdTypeId ");
    for (models.AttendantType atd: atdTypes) {
      atdId = request.params.get("atd_typ_" + atd.getId());
      if (atdId!=null) {
        whereClause.add("atdTypeId="+atd.getId());
        hasTarget = true;
      }
    }
    if(!hasTarget){
      StringBuffer targetWhere = new StringBuffer("atdTypeId in (");
      for (models.AttendantType atd: atdTypes) {
        targetWhere.append(atd.getId()).append(",");
      }
      whereClause.add(targetWhere.deleteCharAt(targetWhere.length()-1).append(")").toString());
    }
  }
}

private static void getActivityByActvityType(ArrayList<String> whereClause, StringBuffer joinClause) {
	
	String activityType = getParam("activity_type");
  if (activityType!=null&&!activityType.trim().equals("")) {
    String[] sub_act_types = request.params.getAll("sub_activity_type");
    String act_typ_ids = "";
    if (sub_act_types != null) {
      for (String actId : sub_act_types) {
      act_typ_ids += actId + ",";
      }
    }
    else{
      List<ActivityType> subtypes = ActivityType.find("ouder_id is " + activityType).fetch();
      for (ActivityType subtype : subtypes) {
      act_typ_ids += subtype.getId() + ",";
      }
    }
    act_typ_ids += activityType;
    //System.out.println(":: act Types = " + act_typ_ids);
    joinClause.append(" join act.activityTypeJunctions actTypeJunc join actTypeJunc.activityTypeId actTypeId ");
    whereClause.add(" actTypeId in ("+act_typ_ids+")");
   }
}

private static void getActivityByDescription(ArrayList<String> whereClause) {
	String desc = getParam("entity.beschrijving");
	   if (desc!=null&&!desc.trim().equals("")) {
		   whereClause.add("act.beschrijving like '%"+desc+"%' ");
		   /*List<models.Activity> acts = models.Activity.find("beschrijving like ?", "%"+desc+"%").fetch();
		   activities.addAll(acts);*/
	   }
}

private static void getActivityByLocation(ArrayList<String> whereClause, StringBuffer joincaluse) {
	String locId = getParam("entity.locationId.id");
  //join Locations loc where loc.ouder_id=1 and act.locationId_id=loc.id
   if (locId!=null){
     Long lid = Long.parseLong(locId);
     if(lid < 0){ //no specific sublocation
      locId = getParam("location");
       joincaluse.append(" join act.locationId loid ");
       whereClause.add("loid.ouder = "+locId);
     }
     else
      whereClause.add("act.locationId="+locId);
   }
  else{
    locId = getParam("location");
     if(StringUtils.isNotBlank(locId))
      whereClause.add("act.locationId="+locId);
   }
}

private static void getActivityByOrganization(ArrayList<String> whereClause, StringBuffer joinClause) {
	String orgId = getParam("sub_org_id");
	   if (orgId==null)
		   orgId = getParam("org_id");
	   if (orgId!=null&&!orgId.trim().equalsIgnoreCase("")) {
		   List<Organisaties> orgs = models.Organisaties.find("isActive=1 and ouder="+orgId).fetch();
		   String sub_org_ids = "";
		   for (models.Organisaties org: orgs) {
			   sub_org_ids += org.id + ",";
		   }
		   sub_org_ids += orgId;
		   //System.out.println(":: sub_org_ids = " + sub_org_ids);
		   whereClause.add("act.organizationId in ("+sub_org_ids+")");
		   //System.out.println("::: Org Id = " + orgId);
		   /*models.Organisaties org = models.Organisaties.find("id is " + orgId).first();
		   List<Activity> acts = Activity.find("byOrganizationId", org).fetch();
		   activities.addAll(acts);*/
	   }
}

private static void getActivityBySector(ArrayList<String> whereClause, StringBuffer joinClause) {
  if (request.params.get("sector_999") != null) {
    joinClause.append(" join act.sectorActivityJunctions ass ");
    whereClause.add("ass.size  > 1");
  }
  else {
    boolean hasSubSecs = false;
    List<String> ids = new ArrayList<String>();
    List<Long> emptyParents = new ArrayList<Long>();
    List<Sectors> sectors = models.Sectors.find("ouder is null").fetch();
    StringBuffer idswhere = new StringBuffer();
    int counter = 1;
    for (models.Sectors sector: sectors) {
      if(getParam("sector_"+sector.getId())!=null) emptyParents.add(sector.getId());
      String[] subsecs = request.params.getAll("sub_sector_"+sector.getId());
      if (subsecs!=null) {
        hasSubSecs = true;
        emptyParents.remove(sector.getId());
        for (int i = 0; i < subsecs.length; i++)
          ids.add(subsecs[i]);
      }
    }
    if(hasSubSecs){
      for (String id : ids) {
        joinClause.append(" join act.sectorActivityJunctions saj").append(counter).append(" ");
        if(counter==1) idswhere.append(" saj1.activityId=act.id and ");
        else  idswhere.append(" saj").append(counter-1).append(".activityId=saj").append(counter).append(".activityId and ");
        idswhere.append("saj").append(counter).append(".sectorId = ").append(id).append(" and ");
        counter++;
      }
      //joinCaluse.append("join act.itemsInActivity iat join iat.itemId iid ");
    }
    if(!emptyParents.isEmpty()){
      //join SectorActivityJunction saj join Sectors sec1 join SectorActivityJunction saj2 join Sectors sec2
      //where act.id=saj.activityId_id and act.id=saj2.activityId_id and saj.sectorId_id=sec1.id and saj2.sectorId_id=sec2.id and sec1.ouder_id=11 and sec2.ouder_id= 1
      for (Long parentSector : emptyParents) {
        joinClause.append(" join act.sectorActivityJunctions saj").append(counter).append(" join saj").append(counter).append(".sectorId sajsid").append(counter).append(" ");
        if(counter==1) idswhere.append(" saj1.activityId=act.id and ");
        else  idswhere.append(" saj").append(counter-1).append(".activityId=saj").append(counter).append(".activityId and ");
        idswhere.append("sajsid").append(counter).append(".ouder = ").append(parentSector).append(" and ");
        counter++;
      }
    }
    if(idswhere.length()>3) whereClause.add(idswhere.substring(0, idswhere.length()-4));
  }
}


/*private static void getActivityBySector(ArrayList<String> whereClause, StringBuffer joinClause) {
		if (request.params.get("sector_999") != null) {
			joinClause.append(" join act.sectorActivityJunctions ass ");
			whereClause.add("ass.size  > 1");
		} else {

      //@TODO correct if subsector is selected in search, all activities of parent sector are found!!!!
			String sub_org_idss = "";
			List<models.Sectors> secs = models.Sectors.find("ouder is null").fetch();
			for (models.Sectors sec : secs) {
				if (request.params.get("sector_" + sec.id) != null) {
					String[] ssids = request.params.getAll("sub_sector_"+ sec.id);
					if (ssids != null) {
						for (String ssid : ssids) {
							sub_org_idss += ssid + ",";
						}
					}
          else{
			      List<models.Sectors> subsecs = models.Sectors.find("ouder = "+sec.id).fetch();
            if(subsecs.isEmpty())
              sub_org_idss += sec.id + ",";
            else{
              for (Sectors subsec : subsecs) {
                sub_org_idss += subsec.id + ",";
              }
            }
          }
				}
			}
			if (sub_org_idss.length() > 0) {
				sub_org_idss = sub_org_idss.substring(0,
						sub_org_idss.length() - 1);
				joinClause
						.append(" join act.sectorActivityJunctions ass join ass.sectorId asid ");
				whereClause.add("asid in (" + sub_org_idss + ")");
			}
		}
}*/

private static void getActivityByItemsUsed(ArrayList<String> whereClause, StringBuffer joinCaluse) {
	boolean hasItems = false;
  List<Long> ids = new ArrayList<Long>();
	List<models.Items> items = models.Items.all().fetch();
  for (models.Items item: items) {
    if (getParam("item_1_"+item.getId())!=null) {
      hasItems = true;
      ids.add(item.getId());
    }
  }
  if(hasItems){
    StringBuffer idswhere = new StringBuffer();
    StringBuffer idsjoin = new StringBuffer();
    int counter = 1;
    for (Long id : ids) {
      joinCaluse.append(" join act.itemsInActivity iia").append(counter).append(" ");
      if(counter>1) idswhere.append(" iia").append(counter-1).append(".activityId=iia").append(counter).append(".activityId and ");
      idswhere.append("iia").append(counter).append(".itemId = ").append(id).append(" and ");
      counter++;
    }
    //joinCaluse.append("join act.itemsInActivity iat join iat.itemId iid ");
    whereClause.add(idswhere.substring(0, idswhere.length()-4));
  }
}


private static void getActivityByMaterialUsed(ArrayList<String> whereClause, StringBuffer joinCaluse) {
	boolean joinAdded = false;
	List<models.Materials> materials = models.Materials.all().fetch();
	   for (models.Materials material: materials) {
		   if (getParam("material_1_"+material.getId())!=null) {
			   if(!joinAdded) {
				   joinCaluse.append(" join act.materialsInActivities mia join mia.materialId mid ");
				   joinAdded = true;
			   }
			   whereClause.add("mid="+material.id);
		   }			   
	   }
}


private static void getActivityByEvaluvation(ArrayList<String> whereClause, StringBuffer joinCaluse) {
	String evaluvated = request.params.get("entity.evaluvated");
	//System.out.println(":: evaluvated " + evaluvated);
	if (!StringUtils.isBlank(evaluvated)&&evaluvated.trim().toLowerCase().equals("yes")) {
    /*Map<String, String[]> pars = request.params.all();
    List<String> hows = new ArrayList<String>();
    String who = null;
    for (String s : pars.keySet()) {
      if(s.startsWith("eval_type_")) hows.add(s.substring(10));
      if(s.equals("evaluvators")) who = pars.get(s)[0];
    }
    if(!hows.isEmpty() || who==null){
      StringBuffer evalJoin = new StringBuffer(" join act.activityEvaluvatorsId eia");
      StringBuffer evalWhere = new StringBuffer();
      if(!hows.isEmpty()){
        evalJoin.append(" join eia.evalTypeId etid ");
        for (String how : hows) {
          evalWhere.append("etid=").append(how).append(" or ");
        }
        whereClause.add(evalWhere.delete(evalWhere.length()-4, -1).toString());
        joinCaluse.append(evalJoin.toString());
      }
      if(who != null){
        joinCaluse.append(" join eia.evaluvatorsId esid ");
        whereClause.add("esid="+who);
      }
    }*/

    boolean joinAdded = false;
    List<models.Materials> materials = models.Materials.all().fetch();
       for (models.Materials material: materials) {
         if (getParam("material_1_"+material.getId())!=null) {
           if(!joinAdded) {
             joinCaluse.append(" join act.materialsInActivities mia join mia.materialId mid ");
             joinAdded = true;
           }
           whereClause.add("mid="+material.id);
         }
       }


		whereClause.add("act.evaluvated=1");
		getActivityByEvaluvators(whereClause, joinCaluse);
	}
	else if (!StringUtils.isBlank(evaluvated)&&evaluvated.trim().toLowerCase().equals("no"))
		whereClause.add("act.evaluvated=0");
}


private static void getActivityByUser(ArrayList<String> whereClause, StringBuffer joinCaluse) {
	String user = request.params.get("gebruiker");
	String centrum = request.params.get("centrum");
	//System.out.println(":: evaluvated " + evaluvated);
	if (StringUtils.isNotBlank(user)) {
		whereClause.add("act.userId="+user);
	}
	if (StringUtils.isNotBlank(centrum))
		whereClause.add("act.centrumId="+centrum);
}

private static void getActivityByEvaluvators(ArrayList<String> whereClause,
		StringBuffer joinCaluse) {
	boolean joinAdded = false;
	List<Long> evalTypeIds = new ArrayList<Long>();
	List<models.EvaluvationType> evalTypes = models.EvaluvationType.all().fetch();
	for (models.EvaluvationType evalType: evalTypes) {
    Long test = evalType.id;
		String evalTypeId = request.params.get("eval_type_"+evalType.id);
		if (!StringUtils.isBlank(evalTypeId)) {
			evalTypeIds.add(evalType.id);
		}
	}
	 if (evalTypeIds.size() > 0) {
		 joinCaluse.append(" join act.activityEvaluvatorsId actEval join actEval.evalTypeId actEvalTypeId ");
		 joinAdded = true;

     StringBuffer evalWhere = new StringBuffer("(");
     for (Long evalTypeId : evalTypeIds) {
       evalWhere.append("actEvalTypeId=").append(evalTypeId).append(" or ");
     }
     whereClause.add(evalWhere.delete(evalWhere.length()-4, 5000).append(")").toString());
	 }
	 String evaluvators = request.params.get("evaluvators");
	 if (!StringUtils.isBlank(evaluvators)) {
		 if (joinAdded) {
			 joinCaluse.append(" join actEval.evaluvatorsId actEvalId ");
			 whereClause.add("actEvalId="+evaluvators);
		 } else {
			 joinCaluse.append(" join act.activityEvaluvatorsId actEval join actEval.evaluvatorsId actEvalId ");
			 whereClause.add("actEvalId="+evaluvators);
		 }
	 }
	String isReported = request.params.get("entity.reported");
	if (!StringUtils.isBlank(isReported) && isReported.toLowerCase().equals("yes"))
		whereClause.add("act.reported=1");
	if (!StringUtils.isBlank(isReported) && isReported.toLowerCase().equals("no"))
		whereClause.add("act.reported=0");
}

private static String getParam(String paramName) {
	// TODO Auto-generated method stub
	return request.params.get(paramName);
}

public static void exportQuery() {
	response.setHeader("Content-Disposition", 
	"attachment;filename=csv_export.txt");
	String quer = session.get("query");
	List<models.Activity> entities = models.Activity.find(quer).fetch();
	renderArgs.put("entities", entities); 
	render("Activities/csv_file");
}


  /*SAMPLE FILTER QUERIES
  select distinct act.id from Activity act  join SectorActivityJunction ass join Sectors asid  where act.centrumId_id=42 and asid.id in (50) and act.Activity_Date >= '2010-01-01' and act.Activity_Date <= '2011-01-01' order by act.id desc;



   */


}
