package controllers;

import java.util.List;

import org.apache.commons.lang.StringUtils;

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
    ModelPaginator entities = new ModelPaginator(Organisaties.class, "ouder is null and isActive=1 order by naam");
    renderArgs.put("title", "Organisaties");
    setAccordionTab(4);
		render(entities);
	}

	public static void centrumOrganisaties() {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		ModelPaginator entities = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder is null and isActive=1 order by naam");
    setAccordionTab(3);
    renderArgs.put("title", user.centrumId.naam+" organisaties");
		renderTemplate("Organisatiess/index.html", entities);
	}

	public static void centrumSubOrganisaties(Long orgId) {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();

    ModelPaginator entities = null;
    if(orgId==null)
      entities = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder is not null and isActive=1 order by naam");
    else{
      entities = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder = "+orgId + " and isActive=1 order by naam");
      renderArgs.put("orgId", orgId);
    }

    /*for (Object entity : entities) {
      Organisaties org = (Organisaties)entity;
      String namesquery = "from Organisaties where id = "+((Organisaties)entity).ouder.id;
      Organisaties ouder = Organisaties.find(namesquery).first();
      ((Organisaties)entity).ouderName = ouder.naam;
    }*/
    setAccordionTab(3);
    List<Organisaties> mainorgs = Organisaties.find("centrumId = " + user.centrumId.id + "and ouder is null and isActive=1 order by naam").fetch();
    //ModelPaginator mainorgs = new ModelPaginator(Organisaties.class, "centrumId = " + user.centrumId.id + "and ouder is null order by naam");
    renderArgs.put("title", user.centrumId.naam +" - suborganisaties afhankelijk ");
    renderArgs.put("mainorgs", mainorgs);
		renderTemplate("Organisatiess/index.html", entities);
	}



	public static void subOrgIndex() {
		ModelPaginator entities = new ModelPaginator(Organisaties.class, "ouder is not null and isActive=1 order by naam");
	    setAccordionTab(4);
    //List<Organisaties> suborgs = Organisaties.find("ouder is not null and isActive=1 order by naam").fetch();
    List<Organisaties> mainorgs = Organisaties.find("ouder is null and isActive=1 order by naam").fetch();
    //ModelPaginator mainorgs = new ModelPaginator(Organisaties.class, "ouder is null and isActive=1 order by naam");
    renderArgs.put("title", "Suborganisaties");
    renderArgs.put("mainorgs", mainorgs);
		render("Organisatiess/index.html",entities);
	}

	public static void create(Organisaties entity) {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
    entity.centrumId = user.centrumId;
    entity.userId = user;
    List<Organisaties> mainorgs = Organisaties.find("centrumId = " + user.centrumId.id + "and ouder is null order by naam").fetch();
    //ModelPaginator mainorgs = new ModelPaginator(Organisaties.class, );
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
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
    List<Organisaties> mainorgs = Organisaties.find("centrumId = " + user.centrumId.id + "and ouder is null order by naam").fetch();
    renderArgs.put("mainorgs", mainorgs);
    setAccordionTab(3);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    Organisaties entity = Organisaties.findById(id);
    entity.isActive = false;
    entity.save();
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
	
	public static void list(String id, String centrumId) {
		if(StringUtils.isBlank(id)) { renderText(""); return;} 
		//List<models.Locations> locs = models.Locations.find("ouder is " + id).fetch();
    String q = "ouder is " + id +" and isActive=1";
    if(StringUtils.isNotBlank(centrumId))
      q = q + " and centrumId = "+centrumId;
		List<models.Organisaties> orgs = models.Organisaties.find(q).fetch();
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
