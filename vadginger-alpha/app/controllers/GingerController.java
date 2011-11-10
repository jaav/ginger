package controllers;

import play.mvc.Controller;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: 11/10/11
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GingerController extends Controller{

  protected static void setAccordionTab(int tab){
    renderArgs.put("accordionTab", tab);
  }
}
