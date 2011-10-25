package controllers;

import java.util.List;
import models.Roles;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class Roless extends Controller {
    public static void index() {
        List<Roles> entities = models.Roles.all().fetch();
        render(entities);
    }

    public static void create(Roles entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        Roles entity = Roles.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        Roles entity = Roles.findById(id);
        render(entity);
    }

    public static void delete(java.lang.Long id) {
        Roles entity = Roles.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid Roles entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "Roles"));
        index();
    }

    public static void update(@Valid Roles entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "Roles"));
        index();
    }
}
