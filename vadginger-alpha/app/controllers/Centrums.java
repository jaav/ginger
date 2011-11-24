package controllers;

import models.Items;
import play.data.validation.Valid;
import play.i18n.Messages;
import play.mvc.With;

import java.util.List;

@With(Secure.class)

public class Centrums extends GingerController {
	public static void index() {
		List<Items> entities = Items.all().fetch();
    setAccordionTab(4);
		render(entities);
	}

	public static void create(Items entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(Long id) {
    Items entity = Items.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(Long id) {
    Items entity = Items.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void delete(Long id) {
    Items entity = Items.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid Items entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
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
