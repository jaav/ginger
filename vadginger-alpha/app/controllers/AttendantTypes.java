package controllers;

import java.util.List;
import models.AttendantType;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

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
	
	public static void list(String id) {
		models.TargetType tgtType = models.TargetType.find("id is " + id).first();
		List<models.AttendantType> atdTypes = models.AttendantType.find("byTargetTypeId", tgtType).fetch();
		StringBuffer htmlData = new StringBuffer("<BR>");
		String interLabel = "Wie waren aanwezig op de activiteit?";
		String finalLabel = "Welke personen van de uiteindelijke doelgroep zijn aanwezig?";
		String secLabel = "Tot welke sector(en) behoort de uiteindelijke doelgroep?";
		if (id.trim().equals("1")) {
		getAttendantTypes(atdTypes, htmlData, interLabel);
		getTotalParticipants(htmlData);
		getSectors(htmlData, secLabel);
		} else  {
			getTotalParticipants(htmlData);
			getAttendantTypes(atdTypes, htmlData, finalLabel);
		}
		renderText(htmlData.toString());
		
	}
	
	public static void nList(String id) {
		models.TargetType tgtType = models.TargetType.find("id is " + id).first();
		List<models.AttendantType> atdTypes = models.AttendantType.find("byTargetTypeId", tgtType).fetch();
		String interLabel ="Aanwezigen";
		String secLabel = "Sector doelgroep";
		StringBuffer htmlData = new StringBuffer("<BR>");
		if (id.trim().equals("1")) {
			getAttendantTypes(atdTypes, htmlData, interLabel);
			getSectors(htmlData, secLabel);
		} else
			getAttendantTypes(atdTypes, htmlData, "	Uiteindelijke doelgroep");
		renderText(htmlData.toString());
	}

	private static void getAttendantTypes(List<models.AttendantType> atdTypes,
			StringBuffer htmlData, String label) {
		htmlData.append("<div class=\"label\">"+label+"</div>");
		htmlData.append("<div class=\"field\">");
		for(models.AttendantType atd: atdTypes) {
			htmlData.append("<input id=\""+atd.getId()+"\" name=\"atd_typ_"+ atd.getId()+"\" type=\"checkbox\">");
			htmlData.append("<label class =\"choice\" for=\"atd_typ_"+ atd.getId()+"\">"+ atd.naam+"</label>");
			htmlData.append("<span class=\"field\" id=\"atd_typ_"+ atd.getId()+"\"></span><br>");
					
		}
		htmlData.append("</div><BR><BR>");
	}

	private static void getSectors(StringBuffer htmlData, String label) {
		htmlData.append("<div class=\"label\">"+label+"</div>");
		htmlData.append("<div class=\"field\">");
		List<models.Sectors> secs = models.Sectors.find("ouder is null").fetch();
		for (models.Sectors sec: secs) {
			htmlData.append("<input id=\""+sec.getId()+"\" name=\"sec_atd_"+ sec.getId()+"\" type=\"checkbox\">");
			htmlData.append("<label class =\"choice\" for=\"sec_atd_"+ sec.getId()+"\">"+ sec.naam+"</label>");
			htmlData.append("<span class=\"field\" id=\"sec_atd_"+ sec.getId()+"\"></span><br>");
		}
		htmlData.append("</div>");
	}

	private static void getTotalParticipants(StringBuffer htmlData) {
		htmlData.append("<div class=\"row\">");
		htmlData.append("<div class=\"label\">Totaal aantal aanwezigen</div>");
		htmlData.append("<div class=\"field\">");
		htmlData.append("<input type=\"text\" name=\"entity.totalParticipants\" value=\"\"/>");
		htmlData.append("</div>");
		//htmlData.append("<span class=\"error\">${errors.forKey('entity.totalParticipants')}</span>");
		htmlData.append("</div><BR><BR>");
	}

}