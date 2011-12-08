package controllers;

import java.util.List;
import models.ActivityTypeJunction;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class ActivityTypeJunctions extends GingerController {
	public static void index() {
		List<ActivityTypeJunction> entities = models.ActivityTypeJunction.all().fetch();
		render(entities);
	}

	public static void create(ActivityTypeJunction entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    ActivityTypeJunction entity = ActivityTypeJunction.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    ActivityTypeJunction entity = ActivityTypeJunction.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    ActivityTypeJunction entity = ActivityTypeJunction.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid ActivityTypeJunction entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "ActivityTypeJunction"));
		index();
	}

	public static void update(@Valid ActivityTypeJunction entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "ActivityTypeJunction"));
		index();
	}

}
