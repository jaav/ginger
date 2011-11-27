package controllers;

import java.util.List;
import models.Locations;
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
		ModelPaginator entities = new ModelPaginator(Locations.class, "ouder is null and isCluster=1");
		entities.setPageSize(20);
		setAccordionTab(4);
		render("Locationss/cluster_index.html",entities);
	}

	public static void create(Locations entity) {
    setAccordionTab(4);
    render(entity);
	}
	
	public static void createCluster(Locations entity) {
	  setAccordionTab(4);
	  render("Locationss/cluster_create.html",entity);
		
	}
	

	public static void show(java.lang.Long id) {
    Locations entity = Locations.findById(id);
    setAccordionTab(4);
    if(isCluster(entity))
    	render("Locationss/cluster_show.html",entity);
	render(entity);
	}

	public static void edit(java.lang.Long id) {
    Locations entity = Locations.findById(id);
    setAccordionTab(4);
    if(isCluster(entity)) {
    	List<models.Locations> locs = models.Locations.find("ouder="+entity.id).fetch();
    	render("Locationss/cluster_edit.html",entity, locs);
    }
    render(entity);
	}

	public static void delete(java.lang.Long id) {
    Locations entity = Locations.findById(id);
    entity.delete();
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
	
	public static void saveCluster(@Valid Locations entity) {
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
	}
	
	public static void updateCluster(@Valid Locations entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
      	entity = entity.merge();
		entity.save();
		removeCurrentLocations(entity);
		createClusterLocations(entity);
		clustersIndex();
	}

	private static void removeCurrentLocations(Locations entity) {
		List<models.Locations> clusterLocs = models.Locations.find("ouder="+entity.id).fetch();
		for (models.Locations loc: clusterLocs){
			loc.delete();
		}
	}

	public static void update(@Valid Locations entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
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
				query += " centrumId=" + user.centrumId.id;
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
