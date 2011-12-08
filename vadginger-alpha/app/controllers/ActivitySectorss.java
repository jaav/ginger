package controllers;

import java.util.List;
import models.ActivitySectors;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class ActivitySectorss extends GingerController {
	public static void index() {
		List<ActivitySectors> entities = models.ActivitySectors.all().fetch();
		render(entities);
	}

	public static void create(ActivitySectors entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    ActivitySectors entity = ActivitySectors.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    ActivitySectors entity = ActivitySectors.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    ActivitySectors entity = ActivitySectors.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid ActivitySectors entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "ActivitySectors"));
		index();
	}

	public static void update(@Valid ActivitySectors entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "ActivitySectors"));
		index();
	}

}
