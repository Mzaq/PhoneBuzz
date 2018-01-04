package controllers;

import spark.ModelAndView;
import spark.TemplateViewRoute;
import util.Config;

import java.util.HashMap;
import java.util.Map;

public class Website {
    //public static TemplateViewRoute init = (request, response) -> {new ModelAndView(model, "templates/index.vtl");

    public static TemplateViewRoute init = ((request, response) -> {
        Map<String, String> model = new HashMap<>();
        model.put("twilioNumber", Config.TWILIO_NUMBER);
        return new ModelAndView(model, "templates/index.vtl");
    });
}
