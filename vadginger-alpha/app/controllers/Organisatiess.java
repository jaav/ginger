package controllers;

import java.util.List;
import models.Organisaties;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class Organisatiess extends GingerController {
	public static void index() {
		//List<Organisaties> entities = models.Organisaties.all().fetch();
    ModelPaginator entities = new ModelPaginator(Organisaties.class, "ouder is null");
    setAccordionTab(3);
		render(entities);
	}
	
	public static void subOrgIndex() {
		ModelPaginator entities = new ModelPaginator(Organisaties.class, "ouder is not null");
	    setAccordionTab(3);
		render("Organisatiess/index.html",entities);
	}
	
	public static void clusterIndex() {
		
	}

	public static void create(Organisaties entity) {
    setAccordionTab(3);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Organisaties entity = Organisaties.findById(id);
    setAccordionTab(3);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Organisaties entity = Organisaties.findById(id);
    setAccordionTab(3);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    Organisaties entity = Organisaties.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid Organisaties entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "Organisaties"));
		index();
	}

	public static void update(@Valid Organisaties entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Organisaties"));
		index();
	}
	
	public static void list(String id) {

		//List<models.Locations> locs = models.Locations.find("ouder is " + id).fetch();
		List<models.Organisaties> orgs = models.Organisaties.find("ouder is " + id).fetch();
    if(orgs.isEmpty()) renderText("");
    else{
      StringBuffer htmlData = new StringBuffer();
      htmlData.append("<div class=\"label\">Sub Organization</div>");
      htmlData.append("<div class=\"field\">");
      htmlData.append("<select name=\"entity.organizationId.id\" id=\"subOrganizationSelect\">\n");
      for (models.Organisaties org: orgs) {
        htmlData.append(" <option value=\""+org.id+"\" >"+org.naam+"</option>\n");

      }
      htmlData.append("</select></div></div>");

      renderText(htmlData.toString());
    }
	
		renderText(id);
	}

}
