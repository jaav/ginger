package controllers;

import java.util.List;
import models.TargetType;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class TargetTypes extends GingerController {
	public static void index() {
		List<TargetType> entities = models.TargetType.all().fetch();
    setAccordionTab(4);
		render(entities);
	}

	public static void create(TargetType entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    TargetType entity = TargetType.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    TargetType entity = TargetType.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    TargetType entity = TargetType.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid TargetType entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.isActive = true;
    entity.save();
		flash.success(Messages.get("scaffold.created", "TargetType"));
		index();
	}

	public static void update(@Valid TargetType entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "TargetType"));
		index();
	}

}
