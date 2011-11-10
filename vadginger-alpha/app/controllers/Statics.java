package controllers;

import play.mvc.Controller;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: 11/10/11
 * Time: 6:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class Statics extends Controller {
	public static void index() {
		render();
	}
	public static void usersStart() {
    renderArgs.put("accordionTab", "1");
		render();
	}
	public static void usersData() {
    renderArgs.put("accordionTab", "1");
		render();
	}
	public static void organisationsStart() {
    renderArgs.put("accordionTab", "3");
		render();
	}
	public static void activitiesStart() {
    renderArgs.put("accordionTab", "2");
		render();
	}
	public static void moreInfo() {
		render();
	}
	public static void tips() {
		render();
	}

}
