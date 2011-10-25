package controllers;

import java.util.List;
import models.Materials;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class Materialss extends Controller {
    public static void index() {
        List<Materials> entities = models.Materials.all().fetch();
        render(entities);
    }

    public static void create(Materials entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        Materials entity = Materials.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        Materials entity = Materials.findById(id);
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
