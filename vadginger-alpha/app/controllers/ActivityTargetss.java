package controllers;

import java.util.List;
import models.ActivityTargets;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class ActivityTargetss extends GingerController {
	public static void index() {
		List<ActivityTargets> entities = models.ActivityTargets.all().fetch();
    setAccordionTab(2);
		render(entities);
	}

	public static void create(ActivityTargets entity) {
    setAccordionTab(2);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    ActivityTargets entity = ActivityTargets.findById(id);
    setAccordionTab(2);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    ActivityTargets entity = ActivityTargets.findById(id);
    setAccordionTab(2);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    ActivityTargets entity = ActivityTargets.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid ActivityTargets entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "ActivityTargets"));
		index();
	}

	public static void update(@Valid ActivityTargets entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "ActivityTargets"));
		index();
	}

}
