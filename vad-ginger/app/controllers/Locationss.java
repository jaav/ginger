package controllers;

import java.util.List;
import models.Locations;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class Locationss extends Controller {
    public static void index() {
        List<Locations> entities = models.Locations.all().fetch();
        render(entities);
    }

    public static void create(Locations entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        Locations entity = Locations.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        Locations entity = Locations.findById(id);
        render(entity);
    }

    public static void delete(java.lang.Long id) {
        Locations entity = Locations.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid Locations entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "Locations"));
        index();
    }

    public static void update(@Valid Locations entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "Locations"));
        index();
    }
}
