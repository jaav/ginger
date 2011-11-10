package controllers;

import java.util.List;
import models.ActivityType;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class ActivityTypes extends Controller {
	public static void index() {
		List<ActivityType> entities = models.ActivityType.all().fetch();
    setAccordianTab();
		render(entities);
	}

	public static void create(ActivityType entity) {
    setAccordianTab();
		render(entity);
	}

	public static void show(java.lang.Long id) {
    ActivityType entity = ActivityType.findById(id);
    setAccordianTab();
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    ActivityType entity = ActivityType.findById(id);
    setAccordianTab();
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    ActivityType entity = ActivityType.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid ActivityType entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "ActivityType"));
		index();
	}

	public static void update(@Valid ActivityType entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "ActivityType"));
		index();
	}

private static void setAccordianTab(){
  renderArgs.put("accordionTab", "4");
}

}
