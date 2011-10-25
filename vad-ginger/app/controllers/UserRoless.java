package controllers;

import java.util.List;
import models.UserRoles;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class UserRoless extends Controller {
    public static void index() {
        List<UserRoles> entities = models.UserRoles.all().fetch();
        render(entities);
    }

    public static void create(UserRoles entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        UserRoles entity = UserRoles.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        UserRoles entity = UserRoles.findById(id);
        render(entity);
    }

    public static void delete(java.lang.Long id) {
        UserRoles entity = UserRoles.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid UserRoles entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "UserRoles"));
        index();
    }

    public static void update(@Valid UserRoles entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "UserRoles"));
        index();
    }
}
