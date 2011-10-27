package controllers;

import java.util.List;
import models.VadGingerUser;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class VadGingerUsers extends Controller {
	public static void index() {
		List<VadGingerUser> entities = models.VadGingerUser.all().fetch();
		render(entities);
	}

	public static void create(VadGingerUser entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    VadGingerUser entity = VadGingerUser.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    VadGingerUser entity = VadGingerUser.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    VadGingerUser entity = VadGingerUser.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid VadGingerUser entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "VadGingerUser"));
		index();
	}

	public static void update(@Valid VadGingerUser entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "VadGingerUser"));
		index();
	}

}
