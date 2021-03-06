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

}
