package controllers;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import play.libs.Mail;
import play.mvc.Controller;

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

	public static void sendPass() {
    SimpleEmail email = new SimpleEmail();
    try {
      email.setFrom("johan.rosiers@vad.be");
      email.addTo("jef@virtualsushi.be");
      email.setSubject("subject");
      email.setMsg("Message");
    } catch (EmailException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    Mail.send(email);
		render();
	}

}
