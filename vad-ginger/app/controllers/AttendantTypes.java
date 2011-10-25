package controllers;

import java.util.List;
import models.AttendantType;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class AttendantTypes extends Controller {
    public static void index() {
        List<AttendantType> entities = models.AttendantType.all().fetch();
        render(entities);
    }

    public static void create(AttendantType entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        AttendantType entity = AttendantType.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        AttendantType entity = AttendantType.findById(id);
        render(entity);
    }

    public static void delete(java.lang.Long id) {
        AttendantType entity = AttendantType.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid AttendantType entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "AttendantType"));
        index();
    }

    public static void update(@Valid AttendantType entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "AttendantType"));
        index();
    }
}
