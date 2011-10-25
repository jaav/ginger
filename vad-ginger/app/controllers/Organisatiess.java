package controllers;

import java.util.List;
import models.Organisaties;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class Organisatiess extends Controller {
    public static void index() {
        List<Organisaties> entities = models.Organisaties.all().fetch();
        render(entities);
    }

    public static void create(Organisaties entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        Organisaties entity = Organisaties.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        Organisaties entity = Organisaties.findById(id);
        render(entity);
    }

    public static void delete(java.lang.Long id) {
        Organisaties entity = Organisaties.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid Organisaties entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "Organisaties"));
        index();
    }

    public static void update(@Valid Organisaties entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "Organisaties"));
        index();
    }
}
