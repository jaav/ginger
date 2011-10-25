package controllers;

import java.util.List;
import models.GingerUsers;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class GingerUserss extends Controller {
    public static void index() {
        List<GingerUsers> entities = models.GingerUsers.all().fetch();
        render(entities);
    }

    public static void create(GingerUsers entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        GingerUsers entity = GingerUsers.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        GingerUsers entity = GingerUsers.findById(id);
        render(entity);
    }

    public static void delete(java.lang.Long id) {
        GingerUsers entity = GingerUsers.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid GingerUsers entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "GingerUsers"));
        index();
    }

    public static void update(@Valid GingerUsers entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "GingerUsers"));
        index();
    }
}
