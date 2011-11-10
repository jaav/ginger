package controllers;

import java.util.List;
import models.Evaluvators;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class Evaluvatorss extends GingerController {
	public static void index() {
		List<Evaluvators> entities = models.Evaluvators.all().fetch();
    setAccordionTab(4);
		render(entities);
	}

	public static void create(Evaluvators entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Evaluvators entity = Evaluvators.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Evaluvators entity = Evaluvators.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    Evaluvators entity = Evaluvators.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid Evaluvators entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "Evaluvators"));
		index();
	}

	public static void update(@Valid Evaluvators entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Evaluvators"));
		index();
	}

}
