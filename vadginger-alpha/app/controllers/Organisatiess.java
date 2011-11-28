package controllers;

import java.util.List;
import models.Organisaties;
import models.VadGingerUser;
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
<<<<<<< HEAD
    ModelPaginator entities = new ModelPaginator(Organisaties.class, "ouder is null");
=======
    ModelPaginator entities = new ModelPaginator(Organisaties.class, "ouder is null and isActive=1");
    setAccordionTab(4);
>>>>>>> 3428ad691385418721e21922aeac403ab0386b3e
    renderArgs.put("title", "Organisaties");
    setAccordionTab(3);
		render(entities);
	}

	public static void centrumOrganisaties() {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		ModelPaginator entities = null;
		if (user.role.compareTo(models.RoleType.ADMIN)>=0)
			index();
		else {
		/*if (user.centrumId!=null)
			
		else*/
			entities = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder is null and isActive=1");
    setAccordionTab(3);
    renderArgs.put("title", user.centrumId.naam+" organisaties");
		renderTemplate("Organisatiess/index.html", entities);

		}
		//List<Organisaties> entities = models.Organisaties.all().fetch();
    //ModelPaginator entities = new ModelPaginator(Organisaties.class, "ouder is null");
	}

	public static void centrumSubOrganisaties(Long orgId) {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		if (user.role.compareTo(models.RoleType.ADMIN)>=0)
			subOrgIndex();
		else {
		ModelPaginator entities = null;
    if(orgId==null)
      entities = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder is not null");
    else{
      entities = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder = "+orgId);
      renderArgs.put("orgId", orgId);
    }
    setAccordionTab(3);
    ModelPaginator mainorgs = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder is null");
    renderArgs.put("title", user.centrumId.naam +" - suborganisaties afhankelijk ");
    renderArgs.put("mainorgs", mainorgs);
		renderTemplate("Organisatiess/index.html", entities);
		}
	}



	public static void subOrgIndex() {
		ModelPaginator entities = new ModelPaginator(Organisaties.class, "ouder is not null and isActive=1");
	    setAccordionTab(4);
    ModelPaginator mainorgs = new ModelPaginator(Organisaties.class, "ouder is null and isActive=1");
    renderArgs.put("title", "Suborganisaties");
    renderArgs.put("mainorgs", mainorgs);
		render("Organisatiess/index.html",entities);
	}
	
	public static void clusterIndex() {
		
	}

	public static void create(Organisaties entity) {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
    entity.centrumId = user.centrumId;
    entity.userId = user;
    ModelPaginator mainorgs = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder is null");
    renderArgs.put("mainorgs", mainorgs);
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
    entity.isActive = false;
    entity.save();
    List<models.Activity> activities = models.Activity.find("organizationId=" + entity.id +" and isActive=1").fetch();
    for (models.Activity activity: activities) {
    	activity.isActive = false;
    	activity.save();
    }
    //entity.delete();
		index();
	}
	
	public static void save(@Valid Organisaties entity) {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.isActive = true;
    entity.centrumId = user.centrumId;
    entity.save();
		flash.success(Messages.get("scaffold.created", "Organisaties"));
		centrumOrganisaties();
	}

	public static void update(@Valid Organisaties entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Organisaties"));
		centrumOrganisaties();
	}
	
	public static void list(String id) {

		//List<models.Locations> locs = models.Locations.find("ouder is " + id).fetch();
		List<models.Organisaties> orgs = models.Organisaties.find("ouder is " + id +" and isActive=1").fetch();
    if(orgs.isEmpty()) renderText("");
    else{
      StringBuffer htmlData = new StringBuffer();
      htmlData.append("<div class=\"label\">Sub-organisatie</div>");
      htmlData.append("<div class=\"field\">");
      htmlData.append("<select name=\"sub_org_id\" id=\"subOrganizationSelect\" multiple size=\"4\">\n");
      for (models.Organisaties org: orgs) {
        htmlData.append(" <option value=\""+org.id+"\" >"+org.naam+"</option>\n");

      }
      htmlData.append("</select></div></div>");

      renderText(htmlData.toString());
    }
	
		renderText(id);
	}

}
