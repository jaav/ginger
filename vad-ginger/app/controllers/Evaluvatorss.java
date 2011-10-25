package controllers;

import java.util.List;
import models.Evaluvators;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class Evaluvatorss extends Controller {
    public static void index() {
        List<Evaluvators> entities = models.Evaluvators.all().fetch();
        render(entities);
    }

    public static void create(Evaluvators entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        Evaluvators entity = Evaluvators.findById(id);
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
