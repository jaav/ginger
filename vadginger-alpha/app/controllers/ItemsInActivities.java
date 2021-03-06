package controllers;

import java.util.List;
import models.ItemsInActivity;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class ItemsInActivities extends GingerController {
	public static void index() {
		List<ItemsInActivity> entities = models.ItemsInActivity.all().fetch();
		render(entities);
	}

	public static void create(ItemsInActivity entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    ItemsInActivity entity = ItemsInActivity.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    ItemsInActivity entity = ItemsInActivity.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    ItemsInActivity entity = ItemsInActivity.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid ItemsInActivity entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "ItemsInActivity"));
		index();
	}

	public static void update(@Valid ItemsInActivity entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "ItemsInActivity"));
		index();
	}

}
