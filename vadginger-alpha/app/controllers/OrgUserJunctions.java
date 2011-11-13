package controllers;

import java.util.Iterator;
import java.util.List;

import models.OrgUserJunction;
import models.VadGingerUser;
import play.data.validation.Valid;
import play.i18n.Messages;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;

public class OrgUserJunctions extends Controller {
	public static void index() {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		ModelPaginator entities = null;
		if (user.role.name().equalsIgnoreCase("admin")) {
		entities = new ModelPaginator(OrgUserJunction.class);
		
		}
		else if (user.role.name().equalsIgnoreCase("org_admin")) {
			List<OrgUserJunction> orgUserJuncs = OrgUserJunction.find("userId is " + user.id).fetch();
			StringBuffer query = new StringBuffer("orgId in (");
			Iterator<OrgUserJunction> i = orgUserJuncs.iterator();
			while(i.hasNext())
			{
				query.append("" + i.next().orgId.id);
				if (i.hasNext())
					query.append(",");
			}
			query.append(")");
			entities = new ModelPaginator(OrgUserJunction.class, query.toString());
		}
		entities.setPageSize(20);
		render(entities);
	}

	public static void create(OrgUserJunction entity) {
		render(entity);
	}

	public static void show(java.lang.Long id) {
    OrgUserJunction entity = OrgUserJunction.findById(id);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    OrgUserJunction entity = OrgUserJunction.findById(id);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    OrgUserJunction entity = OrgUserJunction.findById(id);
    entity.delete();
		index();
	}
	
	public static void save(@Valid OrgUserJunction entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
    entity.save();
		flash.success(Messages.get("scaffold.created", "OrgUserJunction"));
		index();
	}

	public static void update(@Valid OrgUserJunction entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		
      		entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "OrgUserJunction"));
		index();
	}

}
