package controllers;

import models.VadGingerUser;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import play.libs.Mail;
import play.mvc.Controller;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: 11/10/11
 * Time: 6:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class Statics extends GingerController {
	public static void index() {
    setAccordionTab(0);
		render();
	}
	public static void usersStart() {
    setAccordionTab(1);
		render();
	}
	public static void usersOk() {
    setAccordionTab(1);
		render();
	}
	public static void usersData() {
    setAccordionTab(1);
		render();
	}
	public static void organisationsStart() {
    setAccordionTab(3);
		render();
	}
	public static void activitiesStart() {
    setAccordionTab(2);
		render();
	}
	public static void moreInfo() {
    setAccordionTab(0);
		render();
	}
	public static void tips() {
    setAccordionTab(0);
		render();
	}
	public static void passforgot() {
    setAccordionTab(0);
		render();
	}

	public static void forgotPass() {
		setAccordionTab(1);
		render();
	}

	public static void forgotAccount() {
		setAccordionTab(1);
		render();
	}

	public static void sendPass(String gebruikersnaam) {
    VadGingerUser user = VadGingerUser.find("userID = '"+gebruikersnaam+"'").first();
    String status = "OK";
    if(user!=null){
      HtmlEmail email = new HtmlEmail();
      try {
        email.addTo(user.emailAddress);
        email.addCc("johan.rosiers@vad.be", "Johan Rosiers");
        email.setFrom("johan.rosiers@vad.be", "Johan Rosiers");
        email.setSubject("VADGinger wachtwoord vergeten");
        email.setMsg(getHtmlMailContent(user));
        Mail.send(email);
      } catch (EmailException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
    else status = "NOK";

		render(status);
	}

  private static String getMailContent(VadGingerUser user){
    return "Beste VADGinGer.be gebruiker,\n" +
      "\n" +
      "\n" +
      "Gelieve hieronder uw VADGinGer.be aanmeldgegevens te vinden:\n" +
      "\n" +
      "Uw gebruikersnaam: "+user.userID+"\n" +
      "Uw wachtwoord: "+user.passwordHash+" \n" +
      "\n" +
      "U kan aanmelden op http://www.vadginger.be\n" +
      "\n" +
      "Indien u dit wenst kan u zelf uw wachtwoord wijzigen in iets dat u makkelijker kan onthouden.Gebruik hiervoor volgende link: http://www.vadginger.be/vadgingerusers/changeemailidform\n" +
      "Het huidige wachtwoord kan u kopi\u00EBren en plakken. \n" +
      "\n" +
      "mvg,\n" +
      "\n" +
      "Johan Rosiers";
  }

  private static String getHtmlMailContent(VadGingerUser user){
    return "Beste VADGinger gebruiker,<br /><br /><br />" +
      "Gelieve hieronder uw VADGinger aanmeldgegevens te vinden:<br /><br />"+
      "Uw gebruikersnaam: "+user.userID+"<br />" +
      "Uw wachtwoord: "+user.passwordHash+" <br />" +
      "<br />" +
      "U kan aanmelden op http://www.vadginger.be<br />" +
      "<br />" +
      "Indien u dit wenst kan u zelf uw wachtwoord wijzigen in iets dat u makkelijker kan onthouden.Gebruik hiervoor volgende link: <a href='http://www.vadginger.be/vadgingerusers/changeemailidform'>http://www.vadginger.be/vadgingerusers/changeemailidform</a><br />" +
      "Het huidige wachtwoord kan u kopi&euml;ren en plakken. <br />" +
      "<br />" +
      "mvg,<br />" +
      "<br />" +
      "Johan Rosiers";
  }

  }
