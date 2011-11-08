package controllers;

import java.util.List;
import models.Sectors;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class Sectorss extends Controller {
	public static void index() {
		List<Sectors> entities = models.Sectors.all().fetch();
		render(entities);
	}

	public static void create(Sectors entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Sectors entity = Sectors.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Sectors entity = Sectors.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    Sectors entity = Sectors.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid Sectors entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "Sectors"));
		index();
	}

	public static void update(@Valid Sectors entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Sectors"));
		index();
	}
	
	public static void list(String id) {
		String query = "ouder is ";
		if (id == null || id.trim().equalsIgnoreCase(""))
			query = "ouder is null";
		else
			query += id;
		List<models.Sectors> secs = models.Sectors.find(query).fetch();
		StringBuffer htmlData = new StringBuffer();
		htmlData.append("<div class=\"label\">Sub Sectors</div>");
		htmlData.append("<div class=\"field\">");
		htmlData.append("<select name=\"sub_sector_"+id+"\" id=\"subSectorSelect\">\n");
		for (models.Sectors sec: secs) {
			htmlData.append(" <option value=\""+sec.id+"\" >"+sec.naam+"</option>\n");
		}
		htmlData.append("</select></div></div>");
		renderText(htmlData.toString());
	}

}
