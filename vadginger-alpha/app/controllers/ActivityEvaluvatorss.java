package controllers;

import java.util.List;
import models.ActivityEvaluvators;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class ActivityEvaluvatorss extends GingerController {

	public static void index() {
		List<ActivityEvaluvators> entities = models.ActivityEvaluvators.all().fetch();
    setAccordionTab(4);
		render(entities);
	}

	public static void create(ActivityEvaluvators entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    ActivityEvaluvators entity = ActivityEvaluvators.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    ActivityEvaluvators entity = ActivityEvaluvators.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    ActivityEvaluvators entity = ActivityEvaluvators.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid ActivityEvaluvators entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "ActivityEvaluvators"));
		index();
	}

	public static void update(@Valid ActivityEvaluvators entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "ActivityEvaluvators"));
		index();
	}

}
