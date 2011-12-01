package controllers;

import java.util.List;
import models.Items;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class Itemss extends GingerController {
	public static void index() {
		List<Items> entities = models.Items.all().fetch();
    setAccordionTab(4);
		render(entities);
	}

	public static void create(Items entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    Items entity = Items.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    Items entity = Items.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    Items entity = Items.findById(id);
    entity.isActive = false;
    entity.save();
    //entity.delete();
	index();
	}
	
	public static void save(@Valid Items entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.isActive = true;
    entity.save();
		flash.success(Messages.get("scaffold.created", "Items"));
		index();
	}

	public static void update(@Valid Items entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "Items"));
		index();
	}

}
