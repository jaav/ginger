package controllers;

import java.util.List;
import models.Materials;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class Materialss extends GingerController {
	public static void index() {
		List<Materials> entities = models.Materials.find("order by id").fetch();
    setAccordionTab(4);
		render(entities);
	}

	public static void create(Materials entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Materials entity = Materials.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Materials entity = Materials.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    Materials entity = Materials.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid Materials entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.isActive = true;
    entity.save();
		flash.success(Messages.get("scaffold.created", "Materials"));
		index();
	}

	public static void update(@Valid Materials entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Materials"));
		index();
	}

}
