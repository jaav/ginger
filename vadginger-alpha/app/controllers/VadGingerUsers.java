package controllers;

import java.util.List;

import antlr.StringUtils;
import models.Centrums;
import models.RoleType;
import models.VadGingerUser;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import play.libs.Mail;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class VadGingerUsers extends GingerController {

	public static void index() {
		//List<VadGingerUser> entities = models.VadGingerUser.all().fetch();
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();

		if(user==null || user.userID==null) user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		if (user!=null && user.role.equals(RoleType.SUPER_ADMIN)) {
			ModelPaginator entities = new ModelPaginator(VadGingerUser.class, "role in (0,1) and isActive=1 order by userID");
			setAccordionTab(1);
			entities.setPageSize(50);
			render(entities);
		}
		else if (user!=null && user.role.equals(RoleType.ORG_ADMIN))
			centrumUsers();
		else
			Statics.index();
	}

	public static void centrumUsers() {
		VadGingerUser user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		if(user==null || user.userID==null){
			String userId = session.get("userId");
			//user = models.VadGingerUser.findById(session.get("userId"));
			user = models.VadGingerUser.findById(Long.parseLong(session.get("userId")));
			//user = models.VadGingerUser.find("id is " + session.get("userId")).first();
		}
		ModelPaginator entities = null;
		if (user!=null && user.centrumId!=null) {
			entities = new ModelPaginator(VadGingerUser.class, "centrumId = " + user.centrumId.id + " and isActive=1");
			setAccordionTab(3);
		  entities.setPageSize(50);
			renderTemplate("VadGingerUsers/index.html", entities);
		}
		else
			Application.index();
	}

	public static void create(VadGingerUser entity) {
    setAccordionTab(1);
    entity.password="";
		render(entity);
	}

	public static void show(java.lang.Long id) {
    VadGingerUser entity = VadGingerUser.findById(id);
    setAccordionTab(1);
		render(entity);
	}
	
	public static void changeEmailIdForm() {
		VadGingerUser entity = VadGingerUser.findById(Long.parseLong(session.get("userId")));
		setAccordionTab(1);
		render(entity);
	}
	
	public static void changeEmail() {
		VadGingerUser entity = VadGingerUser.findById(Long.parseLong(session.get("userId")));
		entity.emailAddress=request.params.get("entity.emailAddress");
		entity.save();
		Statics.usersOk();
	}
	
	public static void changePasswordForm() {
		boolean invalidPassword = false;
		setAccordionTab(1);
		render(invalidPassword);
	}
	
	public static void changePassword() {
		VadGingerUser entity = VadGingerUser.findById(Long.parseLong(session.get("userId")));
		if (entity.passwordHash.equals((request.params.get("oud_password")))) {
			entity.passwordHash=request.params.get("c_password");
			entity.isActive = true;
			entity.save();
		} else {
			boolean invalidPassword = true;
			render("VadGingerUsers/changePasswordForm.html", invalidPassword); 
		}
		Statics.usersOk();
	}

	public static void edit(java.lang.Long id) {
    VadGingerUser entity = VadGingerUser.findById(id);
    setAccordionTab(1);
		render(entity);
	}

	public static void delete(java.lang.Long id) {
    VadGingerUser entity = VadGingerUser.findById(id);
    entity.isActive = false;
    entity.save();
    //List<models.Organisaties> organizations = models.Activity
    //entity.delete();
		index();
	}
	
	public static void save(@Valid VadGingerUser entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
		//sysout
		Centrums cen = Centrums.find("id is " + request.params.get("centrum_id")).first();
		System.out.println(":::"+cen.id);
		entity.centrumId = cen;
		String pass = request.params.get("c_password");
		if(pass!=null&&pass.length()>=5) {
			entity.passwordHash = pass;
		} else {
			//flash.error(Messages.get("scaffold.validation"));
			render("@create", entity);
		}
		entity.isActive = true;
		entity.save();
		flash.success(Messages.get("scaffold.created", "VadGingerUser"));
		index();
	}

	public static void update(@Valid VadGingerUser entity) {
		if (validation.hasErrors()) {
			flash.error(Messages.get("scaffold.validation"));
			render("@edit", entity);
		}
		Centrums cen = Centrums.find("id is " + request.params.get("centrum_id")).first();
		System.out.println(":::"+cen.id);
		entity.centrumId = cen;
		String pass = request.params.get("c_password");
		entity.passwordHash = pass;
      	entity = entity.merge();
		
		entity.save();
		flash.success(Messages.get("scaffold.updated", "VadGingerUser"));
		index();
	}

	public static void list(String id) {
		StringBuffer htmlData = new StringBuffer("<option value=\"\">Selecteer een gebruiker</option>");
		if (id!=null&&!id.trim().equals("")) {
			List<VadGingerUser> users = VadGingerUser.find("centrumId is " + id +" and isActive=1").fetch();
			if (users.size()>0){
		      for (VadGingerUser user: users) {
		        htmlData.append(" <option value=\""+user.id+"\" >"+user.userID+"</option>\n");
		      }
      }
		}
		renderText(htmlData.toString());
	}

}
