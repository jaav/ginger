package controllers;

import java.util.List;
import models.EvaluvationType;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class EvaluvationTypes extends Controller {
	public static void index() {
		List<EvaluvationType> entities = models.EvaluvationType.all().fetch();
		render(entities);
	}

	public static void create(EvaluvationType entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    EvaluvationType entity = EvaluvationType.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    EvaluvationType entity = EvaluvationType.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    EvaluvationType entity = EvaluvationType.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid EvaluvationType entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "EvaluvationType"));
		index();
	}

	public static void update(@Valid EvaluvationType entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "EvaluvationType"));
		index();
	}

}
