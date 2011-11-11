package controllers;

import java.util.List;

import antlr.StringUtils;
import models.VadGingerUser;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class VadGingerUsers extends GingerController {

	public static void index() {
		//List<VadGingerUser> entities = models.VadGingerUser.all().fetch();
		ModelPaginator entities = new ModelPaginator(VadGingerUser.class);
    setAccordionTab(1);
		render(entities);
	}

	public static void create(VadGingerUser entity) {
    setAccordionTab(1);
    entity.password="";
		render(entity);
	}

	public static void show(java.lang.Long id) {
    VadGingerUser entity = VadGingerUser.findById(id);
    setAccordionTab(1);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    VadGingerUser entity = VadGingerUser.findById(id);
    setAccordionTab(1);
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
		String pass = request.params.get("password");
		if(pass!=null&&pass.length()>=5) {
			entity.passwordHash = play.libs.Codec.encodeBASE64(Security.md5(pass));
		} else {
			//flash.error(Messages.get("scaffold.validation"));
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
