package controllers;

import java.util.List;
import models.SectorActivityJunction;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class SectorActivityJunctions extends Controller {
	public static void index() {
		List<SectorActivityJunction> entities = models.SectorActivityJunction.all().fetch();
		render(entities);
	}

	public static void create(SectorActivityJunction entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    SectorActivityJunction entity = SectorActivityJunction.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    SectorActivityJunction entity = SectorActivityJunction.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    SectorActivityJunction entity = SectorActivityJunction.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid SectorActivityJunction entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "SectorActivityJunction"));
		index();
	}

	public static void update(@Valid SectorActivityJunction entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "SectorActivityJunction"));
		index();
	}

}
