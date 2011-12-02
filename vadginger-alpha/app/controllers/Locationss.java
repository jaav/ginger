package controllers;

import java.sql.Clob;
import java.util.List;

import models.CityClusterJunction;
import models.Locations;
import models.VadGingerUser;

import org.apache.commons.lang.StringUtils;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class Locationss extends GingerController {
	public static void index() {
		//List<Locations> entities = models.Locations.all().fetch();
		ModelPaginator entities = new ModelPaginator(Locations.class, "ouder is null and isCluster=0");
		entities.setPageSize(20);
    setAccordionTab(4);
		render(entities);
	}
	
	public static void subLocindex() {
		//List<Locations> entities = models.Locations.all().fetch();
		ModelPaginator entities = new ModelPaginator(Locations.class, "ouder is not null and isCluster=0");
		entities.setPageSize(20);
		setAccordionTab(4);
		render("Locationss/index.html",entities);
	}
	
	public static void clustersIndex() {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
    //String query = "select loc.naam from Locations loc, CityClusterJunction ccj where ccj.cityId = loc and loc.ouder is null and loc.isCluster=1";//ccj.clusterId_id = 2908;";
		String query = "ouder is null and isCluster=1";
		if (user.role.compareTo(models.RoleType.ADMIN)<0) {
			query+= " and centrumId=" + user.centrumId.id;
		}

    List<Locations> entities = Locations.find(query).fetch();
    for (Locations entity : entities) {
      StringBuilder sb = new StringBuilder();
      String namesquery = "select loc from CityClusterJunction ccj, Locations loc where ccj.clusterId = "+entity.id+" and ccj.cityId=loc";
      List<Locations> cities = Locations.find(namesquery).fetch();
      for (Locations city : cities) {
        sb.append(city.naam).append(", ");
      }
      entity.cities = sb.toString();
    }
		/*ModelPaginator entities = new ModelPaginator(Locations.class, query);
		entities.setPageSize(20);*/
    setAccordionTab(3);
		render("Locationss/cluster_index.html",entities);
	}

	public static void create(Locations entity) {
    setAccordionTab(4);
    render(entity);
	}
	
	public static void createCluster(Locations entity) {
    setAccordionTab(3);
		String query = "ouder_id = 1 and isActive = 1";
    renderArgs.put("locations", models.Locations.find(query).fetch());
	  render("Locationss/cluster_create.html", entity);
		
	}
	

	public static void show(java.lang.Long id) {
    Locations entity = Locations.findById(id);
    setAccordionTab(3);
    if(isCluster(entity))
    	render("Locationss/cluster_show.html",entity);
	render(entity);
	}

	public static void edit(java.lang.Long id) {
    Locations entity = Locations.findById(id);
    setAccordionTab(3);
    if(isCluster(entity)) {
    	List<models.CityClusterJunction> locs = models.CityClusterJunction.find("clusterId="+entity.id).fetch();
    	render("Locationss/cluster_edit.html",entity, locs);
    }
    render(entity);
	}

	public static void delete(java.lang.Long id) {
    Locations entity = Locations.findById(id);
    entity.isActive = false;
    entity.save();
    //entity.delete();
	index();
	}
	
	public static void save(@Valid Locations entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "Locations"));
		index();
	}
	
	public static void newSaveCluster(@Valid Locations entity) {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		if (user.role.compareTo(models.RoleType.ORG_ADMIN)<=0)
			entity.centrumId = user.centrumId;
		entity.isCluster = true;
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("Locationss/cluster_create.html", entity);
		}
		entity.isActive=true;
    entity = entity.save();
    saveClusterLocations(entity);
    clustersIndex();

  }

	private static void saveClusterLocations(Locations entity) {
		for(int i = 0; i<10; i++){
		  if(StringUtils.isNotBlank(params.get("cluster_locations_"+i))){
		    CityClusterJunction ccj = new CityClusterJunction();
		    ccj.cityId = Locations.findById(Long.parseLong(params.get("cluster_locations_"+i)));
		    ccj.clusterId = entity;
		    ccj.save();
		  }
		}
	}

	/*public static void saveCluster(@Valid Locations entity) {
		entity.isCluster = true;
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("Locationss/cluster_create.html", entity);
		}
		entity.isActive=true;
		entity.save();
		createClusterLocations(entity);
		clustersIndex();
	}

	private static void createClusterLocations(Locations entity) {
		utils.ClusterUtils cu = new utils.ClusterUtils();
		models.Locations clusterLoc = null;
		for (int i = 1 ;i <=10;i++) {
			String key = request.params.get("cluster"+i);
			if (key!=null&&!key.trim().equals("")) {
				clusterLoc = new models.Locations();
				clusterLoc.isActive = true; clusterLoc.isCluster = false;
				clusterLoc.ouder = entity; clusterLoc.naam = cu.postCodeList.get(Integer.parseInt(key));
				clusterLoc.save();
			}
		}
	}*/
	
	public static void updateCluster(@Valid Locations entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
      	entity = entity.merge();
    entity.isActive = true;
		entity.save();
		removeCurrentLocations(entity);
		saveClusterLocations(entity);
		clustersIndex();
	}

	private static void removeCurrentLocations(Locations entity) {
		List<models.CityClusterJunction> clusterLocs = models.CityClusterJunction.find("clusterId="+entity.id).fetch();
		for (models.CityClusterJunction loc: clusterLocs){
			loc.delete();
		}
	}

	public static void update(@Valid Locations entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();

    entity.isActive = true;
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Locations"));
		index();
	}
	
	public static void list(String id) {
		//List<models.Locations> locs = models.Locations.find("ouder is " + id).fetch();
		models.Locations org1 = models.Locations.find("id is " + id).first();
		String query = "ouder is " + id +" and isActive=1";
		if (org1.naam.toLowerCase().indexOf("regionaal")>-1) {
			models.VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
			query = "isActive=1 and isCluster=1";
			if (user.role.equals(models.RoleType.ORG_ADMIN)||user.role.equals(models.RoleType.MEMBER))
				query += "and centrumId=" + user.centrumId.id;
		}
		List<models.Locations> locs = models.Locations.find(query).fetch();
    if(locs.isEmpty()) renderText("");
    else{
      StringBuffer htmlData = new StringBuffer();
      htmlData.append("<div class=\"label\">Sub-locatie</div>");
      htmlData.append("<div class=\"field\">");
      htmlData.append("<select name=\"entity.locationId.id\" id=\"subLocationSelect\">\n");
      for (models.Locations loc: locs) {
        htmlData.append(" <option value=\""+loc.id+"\" >"+loc.naam+"</option>\n");

      }
      htmlData.append("</select></div></div>");

      renderText(htmlData.toString());
    }
	}
	
	private static boolean isCluster(Locations loc) {
		return loc.isCluster;
	}

}
