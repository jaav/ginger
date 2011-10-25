package controllers;

import java.util.List;
import models.MaterialsInActivity;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;

public class MaterialsInActivities extends Controller {
    public static void index() {
        List<MaterialsInActivity> entities = models.MaterialsInActivity.all().fetch();
        render(entities);
    }

    public static void create(MaterialsInActivity entity) {
        render(entity);
    }

    public static void show(java.lang.Long id) {
        MaterialsInActivity entity = MaterialsInActivity.findById(id);
        render(entity);
    }

    public static void edit(java.lang.Long id) {
        MaterialsInActivity entity = MaterialsInActivity.findById(id);
        render(entity);
    }

    public static void delete(java.lang.Long id) {
        MaterialsInActivity entity = MaterialsInActivity.findById(id);
        entity.delete();
        index();
    }
    
    public static void save(@Valid MaterialsInActivity entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@create", entity);
        }
        entity.save();
        flash.success(Messages.get("scaffold.created", "MaterialsInActivity"));
        index();
    }

    public static void update(@Valid MaterialsInActivity entity) {
        if (validation.hasErrors()) {
            flash.error(Messages.get("scaffold.validation"));
            render("@edit", entity);
        }
        
              entity = entity.merge();
        
        entity.save();
        flash.success(Messages.get("scaffold.updated", "MaterialsInActivity"));
        index();
    }
}
