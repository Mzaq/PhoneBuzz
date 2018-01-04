package controllers;

import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

import java.util.HashMap;

public class Website {
    public static TemplateViewRoute init = (request, response) -> new ModelAndView(new HashMap(), "templates/index.vtl");
}
