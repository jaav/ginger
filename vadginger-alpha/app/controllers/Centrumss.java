package controllers;

import models.*;
import models.Centrums;
import play.data.validation.Valid;
import play.i18n.Messages;
import play.modules.paginate.ModelPaginator;
import play.mvc.With;

import java.util.List;

@With(Secure.class)

public class Centrumss extends GingerController {
	public static void index() {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		List<Centrums> entities = Centrums.find("order by naam").fetch();
    setAccordionTab(4);
		render(entities);
	}

	public static void create(Centrums entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void showMyCentrum() {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
    show(user.centrumId.id);
	}

	public static void show(Long id) {
    Centrums entity = Centrums.findById(id);
    setAccordionTab(3);
		render(entity);
	}

	public static void edit(Long id) {
    Centrums entity = Centrums.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void delete(Long id) {
    Centrums entity = Centrums.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid Centrums entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.isActive = true;
    entity.save();
		flash.success(Messages.get("scaffold.created", "Centrums"));
		index();
	}

	public static void update(@Valid Centrums entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Centrums"));
		index();
	}

}
