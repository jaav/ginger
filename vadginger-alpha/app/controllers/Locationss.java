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
		ModelPaginator entities = new ModelPaginator(Locations.class, "ouder is null and isCluster is null");
		entities.setPageSize(20);
    setAccordionTab(4);
		render(entities);
	}
	
	public static void subLocindex() {
		//List<Locations> entities = models.Locations.all().fetch();
		ModelPaginator entities = new ModelPaginator(Locations.class, "ouder is not null and isCluster is null");
		entities.setPageSize(20);
		setAccordionTab(4);
		render("Locationss/index.html",entities);
	}
	
	public static void clustersIndex() {
		ModelPaginator entities = new ModelPaginator(Locations.class, "isCluster is 1");
		entities.setPageSize(20);
		setAccordionTab(4);
		render("Locationss/index.html",entities);
	}

	public static void create(Locations entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Locations entity = Locations.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Locations entity = Locations.findById(id);
    setAccordionTab(4);
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
		List<models.Locations> locs = models.Locations.find("ouder is " + id).fetch();
    if(locs.isEmpty()) renderText("");
    else{
      StringBuffer htmlData = new StringBuffer();
      htmlData.append("<div class=\"label\">Sub Location</div>");
      htmlData.append("<div class=\"field\">");
      htmlData.append("<select name=\"entity.locationId.id\" id=\"subLocationSelect\">\n");
      for (models.Locations loc: locs) {
        htmlData.append(" <option value=\""+loc.id+"\" >"+loc.naam+"</option>\n");

      }
      htmlData.append("</select></div></div>");

      renderText(htmlData.toString());
    }
	}

}
