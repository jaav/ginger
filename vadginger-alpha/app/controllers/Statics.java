package controllers;

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

}
