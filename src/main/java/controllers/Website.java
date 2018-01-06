package controllers;

import obj.BasicPhoneCall;
import spark.ModelAndView;
import spark.TemplateViewRoute;
import util.Config;
import util.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Website {
    //public static TemplateViewRoute init = (request, response) -> {new ModelAndView(model, "templates/index.vtl");

    public static TemplateViewRoute init = ((request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("twilioNumber", Config.TWILIO_NUMBER);
        model.put("phoneCalls", Helper.packageLog());

        return new ModelAndView(model, "templates/index.vtl");
    });
}
