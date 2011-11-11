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

public class Organisatiess extends Controller {
	public static void index() {
		//List<Organisaties> entities = models.Organisaties.all().fetch();
		ModelPaginator entities = new ModelPaginator(Organisaties.class);
		entities.setPageSize(20);
		render(entities);
	}

	public static void create(Organisaties entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Organisaties entity = Organisaties.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Organisaties entity = Organisaties.findById(id);
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
		StringBuffer htmlData = new StringBuffer();
		htmlData.append("<div class=\"label\">Sub Organization</div>");
		htmlData.append("<div class=\"field\">");
		htmlData.append("<select name=\"entity.organizationId.id\" id=\"subOrganizationSelect\">\n");
		for (models.Organisaties org: orgs) {
			htmlData.append(" <option value=\""+org.id+"\" >"+org.naam+"</option>\n");
			
		}
		htmlData.append("</select></div></div>");
		
		renderText(htmlData.toString());
	
		renderText(id);
	}

}
