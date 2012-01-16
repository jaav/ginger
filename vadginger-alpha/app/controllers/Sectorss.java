package controllers;

import java.util.List;

import models.Activity;
import models.Sectors;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class Sectorss extends GingerController {
	public static void index() {
		//List<Sectors> entities = models.Sectors.all().fetch();
		ModelPaginator entities = new ModelPaginator(Sectors.class, "ouder is null");
		entities.setPageSize(20);
		setAccordionTab(4);
		render(entities);
	}
	
	public static void subIndex() {
		//List<Sectors> entities = models.Sectors.all().fetch();
		ModelPaginator entities = new ModelPaginator(Sectors.class, "ouder is not null");
		entities.setPageSize(20);
		setAccordionTab(4);
		render("Sectorss/index.html",entities);
	}

	public static void create(Sectors entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Sectors entity = Sectors.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Sectors entity = Sectors.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    Sectors entity = Sectors.findById(id);
    entity.isActive = false;
    entity.save();
    //entity.delete();
		index();
	}
	
	public static void save(@Valid Sectors entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.isActive = true;
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
		query+= " and isActive=1 order by naam";
		List<models.Sectors> secs = models.Sectors.find(query).fetch();
    if(secs.isEmpty()) renderText("<div></div>");
		StringBuffer htmlData = new StringBuffer();
		htmlData.append("<div class=\"label\">Detailsector</div>");
		htmlData.append("<div class=\"field\">");
		htmlData.append("<select name=\"sub_sector_"+id+"\" id=\"subSectorSelect\" multiple=\"multiple\" size=\"4\">\n");
    Sectors andere = null;
		for (models.Sectors sec: secs) {
      if("Andere".equals(sec.naam)) andere = sec;
      else
			  htmlData.append(" <option value=\""+sec.id+"\" >"+sec.naam+"</option>\n");
		}
    if(andere!=null) htmlData.append(" <option value=\""+andere.id+"\" >Andere</option>\n");
		htmlData.append("</select></div>");
		renderText(htmlData.toString());
	}

}
