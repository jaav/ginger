package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import models.*;
import play.Logger;
import play.mvc.Controller;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.data.validation.Valid;


import play.mvc.With;

@With(Secure.class)

public class AttendantTypes extends GingerController {
	public static void index() {
		List<AttendantType> entities = models.AttendantType.all().fetch();
    setAccordionTab(4);
		render(entities);
	}

	public static void create(AttendantType entity) {
    setAccordionTab(4);
		render(entity);
	}

	public static void show(java.lang.Long id) {
    AttendantType entity = AttendantType.findById(id);
    setAccordionTab(4);
		render(entity);
	}

	public static void edit(java.lang.Long id) {
    AttendantType entity = AttendantType.findById(id);
    setAccordionTab(4);
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
		getAttendantTypes(atdTypes, htmlData, interLabel, null);
		getTotalParticipants(htmlData, null);
		getSectors(htmlData, secLabel, null);
		} else  {
			getTotalParticipants(htmlData, null);
			getAttendantTypes(atdTypes, htmlData, finalLabel, null);
		}
		renderText(htmlData.toString());
		
	}

	public static void filledlist(Long activity_id) {
    Activity activity = models.Activity.findById(activity_id);
    Set<ActivityTargets> activityTargets = activity.activityTargets;
    if(activityTargets.isEmpty()) renderText("No attendants");
    else{
      List<Long> attendantTypes = new ArrayList<Long>();
      models.TargetType tgtType = null;
      Iterator<ActivityTargets> itt = activityTargets.iterator();
      while (itt.hasNext()) {
        ActivityTargets next = itt.next();
        attendantTypes.add(next.attendantTypeId.id);
        tgtType = next.attendantTypeId.targetTypeId;
      }
      List<Long> sectors = new ArrayList<Long>();
      List<SectorActivityJunction> sacs = SectorActivityJunction.find("byActivityId", activity).fetch();
      for (SectorActivityJunction sac : sacs) {
        sectors.add(sac.sectorId.id);
      }
      //models.TargetType tgtType = models.TargetType.find("id is " + id).first();
      List<models.AttendantType> atdTypes = models.AttendantType.find("byTargetTypeId", tgtType).fetch();
      StringBuffer htmlData = new StringBuffer("<BR>");
      String interLabel = "Wie waren aanwezig op de activiteit?";
      String finalLabel = "Welke personen van de uiteindelijke doelgroep zijn aanwezig?";
      String secLabel = "Tot welke sector(en) behoort de uiteindelijke doelgroep?";
      if (tgtType.id == 1) {
      getAttendantTypes(atdTypes, htmlData, interLabel, attendantTypes);
      getTotalParticipants(htmlData, activity.totalParticipants);
      getSectors(htmlData, secLabel, sectors);
      } else  {
        getTotalParticipants(htmlData, activity.totalParticipants);
        getAttendantTypes(atdTypes, htmlData, finalLabel, attendantTypes);
      }
      renderText(htmlData.toString());
    }
	}
	
	public static void nList(String id) {
		models.TargetType tgtType = models.TargetType.find("id is " + id).first();
		List<models.AttendantType> atdTypes = models.AttendantType.find("byTargetTypeId", tgtType).fetch();
		String interLabel ="Aanwezigen";
		String secLabel = "Sector doelgroep";
		StringBuffer htmlData = new StringBuffer("<BR>");
		if (id.trim().equals("1")) {
			getAttendantTypes(atdTypes, htmlData, interLabel, null);
			getTotalParticipants(htmlData, null);
			getSectors(htmlData, secLabel, null);
		} else
			getTotalParticipants(htmlData, null);
			getAttendantTypes(atdTypes, htmlData, "Aanwezigen", null);
		renderText(htmlData.toString());
	}

	private static void getAttendantTypes(List<models.AttendantType> atdTypes,
			StringBuffer htmlData, String label, List<Long> attendants) {
		htmlData.append("<div class=\"label\">"+label+"</div>");
		htmlData.append("<div class=\"field\">");
		for(models.AttendantType atd: atdTypes) {
      String checked = "";
      if(attendants!=null && attendants.contains(atd.id)) checked = "checked";
			htmlData.append("<input id=\""+atd.getId()+"\" name=\"atd_typ_"+ atd.getId()+"\" type=\"checkbox\" "+checked+">");
			htmlData.append("<label class =\"choice\" for=\"atd_typ_"+ atd.getId()+"\">"+ atd.naam+"</label>");
			htmlData.append("<span class=\"field\" id=\"atd_typ_"+ atd.getId()+"\"></span><br>");
					
		}
		htmlData.append("</div><BR>");
	}

	private static void getSectors(StringBuffer htmlData, String label, List<Long> sectors) {
		htmlData.append("<div class=\"label\">"+label+"</div>");
		htmlData.append("<div class=\"field\">");
		List<models.Sectors> secs = models.Sectors.find("ouder is null").fetch();
		for (models.Sectors sec: secs) {
      String checked = "";
      if(sectors!=null && sectors.contains(sec.getId())) checked = "checked";
			htmlData.append("<input id=\""+sec.getId()+"\" name=\"sec_atd_"+ sec.getId()+"\" type=\"checkbox\" "+checked+">");
			htmlData.append("<label class =\"choice\" for=\"sec_atd_"+ sec.getId()+"\">"+ sec.naam+"</label>");
			htmlData.append("<span class=\"field\" id=\"sec_atd_"+ sec.getId()+"\"></span><br>");
		}
		htmlData.append("</div>");
	}

	private static void getTotalParticipants(StringBuffer htmlData, Integer amount) {
    String s_amount = "";
    if(amount!=null) s_amount += amount;
		htmlData.append("<div class=\"row\">");
		htmlData.append("<div class=\"label\">Totaal aantal aanwezigen</div>");
		htmlData.append("<div class=\"field\">");
		htmlData.append("<input type=\"text\" name=\"entity.totalParticipants\" value=\""+s_amount+"\"/>");
		htmlData.append("</div>");
		//htmlData.append("<span class=\"error\">${errors.forKey('entity.totalParticipants')}</span>");
		htmlData.append("</div><BR><BR>");
	}

}
